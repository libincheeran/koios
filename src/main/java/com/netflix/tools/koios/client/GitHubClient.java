package com.netflix.tools.koios.client;

import com.google.common.math.IntMath;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netflix.tools.koios.config.AppProperties;
import com.netflix.tools.koios.dto.GithubResponse;
import com.netflix.tools.koios.dto.Item;
import com.netflix.tools.koios.dto.PrItem;
import com.netflix.tools.koios.dto.PullRequest;
import io.vavr.control.Try;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import one.util.streamex.StreamEx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Type;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.netflix.tools.koios.util.KoiosConstants.BEAN_NAME_GITHUB;

@Component
public class GitHubClient {

    private static final Logger logger = LoggerFactory.getLogger(GitHubClient.class);
    private static final int MAX_PER_PAGE = 100;

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private Gson gson;

    @Autowired
    private OkHttpClient client;

    @Autowired
    @Qualifier(BEAN_NAME_GITHUB)
    private Request.Builder requestBuilder;

    @Autowired
    private ForkJoinPool pool;

    public GitHubClient(AppProperties appProperties, Gson gson, OkHttpClient client, Request.Builder requestBuilder, ForkJoinPool pool) {
        this.appProperties = appProperties;
        this.gson = gson;
        this.client = client;
        this.requestBuilder = requestBuilder;
        this.pool = pool;
    }

    public List<Item> getTopReposByStars(int topN, String orgName) {

        int range = getRange(topN);

        List<Item> items = IntStream.rangeClosed(1, range)
                .parallel()
                .mapToObj(page -> callGithubForSearch(resolveSearchUrl(orgName, "star", page, topN)))
                .flatMap(stream -> stream.stream())
                .collect(Collectors.toList());

        return items;
    }

    public List<Item> getTopReposByForks(int topN, String orgName) {

        int range = getRange(topN);

        List<Item> items = IntStream.rangeClosed(1, range)
                .parallel()
                .mapToObj(page -> callGithubForSearch(resolveSearchUrl(orgName, "forks", page, topN)))
                .flatMap(stream -> stream.stream())
                .collect(Collectors.toList());

        return items;
    }

    public List<PullRequest> getTopReposByPrs(int topN, String orgName) {

        List<PullRequest> sortedPullReqCount = StreamEx.of(getAllRepos(orgName))
                .parallel(pool)
                .map(item -> new PullRequest(item.getName(), getPrCount(item.getName(), orgName)))
                .sorted((pr1, pr2) -> Integer.compare(pr2.getPrCount(), pr1.getPrCount()))
                .limit(topN)
                .collect(Collectors.toList());

        return sortedPullReqCount;
    }

    public List<Item> getTopReposByContributionPercent(int topN, String orgName) {

        List<Item> items = StreamEx.of(getAllRepos(orgName))
                .parallel(pool)
                .map(item -> {
                    int prCount = getPrCount(item.getName(), orgName);
                    item.setContributionPercent(prCount > 0 ? (float) prCount / item.getForksCount() : 0);
                    return item;
                })
                .sorted((item1, item2) -> Float.compare(item2.getContributionPercent(), item1.getContributionPercent()))
                .limit(topN)
                .collect(Collectors.toList());

        return items;
    }

    private List<Item> getAllRepos(String orgName) {

        int pageCount = 0;
        List<Item> totalRepoList = new ArrayList<>();
        while (true) {
            String url = resolveSearchUrl(orgName, "", ++pageCount, MAX_PER_PAGE);
            List<Item> items = StreamEx.of(callGithubForSearch(url))
                    .parallel(pool)
                    .collect(Collectors.toList());
            totalRepoList.addAll(items);
            if (CollectionUtils.isEmpty(items)) {
                break;
            }
        }
        return totalRepoList;
    }

    private int getPrCount(String repoName, String orgName) {
        int pageCount = 0;
        int prCount = 0;
        while (true) {
            String urlpr = resolvePrUrl(orgName, repoName, ++pageCount, MAX_PER_PAGE);
            List<PrItem> prItems = callGithubForPR(urlpr);
            int sum = prItems != null ? prItems.size() : 0;
            prCount += sum;
            if (CollectionUtils.isEmpty(prItems)) {
                break;
            }

        }
        return prCount;
    }

    private String resolveSearchUrl(String orgName, String sortKey, int page, int perPage) {

        String url = appProperties.getGithubBaseUrl()
                + "/search/repositories?q=org:%s&sort=%s&order=desc&page=%s&per_page=%s";
        return String.format(url, orgName, sortKey, page, perPage);
    }

    private String resolvePrUrl(String orgName, String repo, int page, int perPage) {
        String url = appProperties.getGithubBaseUrl() + "/repos/%s/%s/pulls?page=%s&per_page=%s";
        return String.format(url, orgName, repo, page, perPage);
    }

    private int getRange(int topN) {
        return IntMath.divide(topN, MAX_PER_PAGE, RoundingMode.CEILING);
    }

    private List<Item> callGithubForSearch(String url) {

        Call call = getCall(url);
        return Try.of(() -> {
            Response response = call.execute();
            return gson.fromJson(response.body().string(), GithubResponse.class).getItems();
        })
                .onFailure(ex -> logger.error("error", ex))
                .getOrElse(() -> Collections.emptyList());
    }

    private List<PrItem> callGithubForPR(String url) {

        Call call = getCall(url);

        try {
            Response response = call.execute();
            Type typeOfT = TypeToken.getParameterized(List.class, PrItem.class).getType();
            return gson.fromJson(response.body().string(), typeOfT);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }

        return Collections.emptyList();
    }

    private Call getCall(String url) {
        Request request = requestBuilder
                .url(url)
                .get()
                .build();

        return client.newCall(request);
    }

}
