package com.netflix.tools.koios.client;

import com.google.gson.Gson;
import com.netflix.tools.koios.config.AppProperties;
import com.netflix.tools.koios.dto.GithubResponse;
import com.netflix.tools.koios.dto.Item;
import okhttp3.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class GitHubClientTest {

    @Mock
    private AppProperties appProperties;

    @Mock
    private OkHttpClient client;

    @Mock
    private Request.Builder requestBuilder;

    @Mock
    private Call call;

    @Mock
    private Response response;

    @Mock
    private ResponseBody responseBody;

    private GitHubClient gitHubClient;

    @BeforeEach
    public void init() throws IOException {
        gitHubClient = new GitHubClient(appProperties, new Gson(), client, requestBuilder, ForkJoinPool.commonPool());
        when((requestBuilder.url(anyString()))).thenReturn(requestBuilder);
        when(requestBuilder.get()).thenReturn(requestBuilder);
        when(client.newCall(any())).thenReturn(call);
        when(call.execute()).thenReturn(response);
        when((response.body())).thenReturn(responseBody);
        when(responseBody.string()).thenReturn(getGithubResponse());
    }

    @Test
    public void testGetTopReposByStars() {
        List<Item> items = gitHubClient.getTopReposByStars(10, "test");
        assertEquals(1, items.size());
        Item item = items.get(0);
        assertEquals("Tetris", item.getName());
        assertSame(1, item.getStargazersCount());
    }

    @Test
    public void testGetTopReposByForks() {
        List<Item> items = gitHubClient.getTopReposByForks(10, "test");
        assertEquals(1, items.size());
        Item item = items.get(0);
        assertEquals("Tetris", item.getName());
        assertSame(5, item.getForksCount());
    }

    private GithubResponse getGitHubResponse() {
        Item item = new Item();
        item.setName("testRepo");
        item.setStargazersCount(10);
        item.setForksCount(15);

        GithubResponse githubResponse = new GithubResponse();
        githubResponse.setItems(Arrays.asList(item));

        return githubResponse;
    }


    private String getGithubResponse() {
        return "{\n" +
                "  \"total_count\": 40,\n" +
                "  \"incomplete_results\": false,\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"id\": 3081286,\n" +
                "      \"node_id\": \"MDEwOlJlcG9zaXRvcnkzMDgxMjg2\",\n" +
                "      \"name\": \"Tetris\",\n" +
                "      \"full_name\": \"dtrupenn/Tetris\",\n" +
                "      \"owner\": {\n" +
                "        \"login\": \"dtrupenn\",\n" +
                "        \"id\": 872147,\n" +
                "        \"node_id\": \"MDQ6VXNlcjg3MjE0Nw==\",\n" +
                "        \"avatar_url\": \"https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png\",\n" +
                "        \"gravatar_id\": \"\",\n" +
                "        \"url\": \"https://api.github.com/users/dtrupenn\",\n" +
                "        \"received_events_url\": \"https://api.github.com/users/dtrupenn/received_events\",\n" +
                "        \"type\": \"User\"\n" +
                "      },\n" +
                "      \"private\": false,\n" +
                "      \"html_url\": \"https://github.com/dtrupenn/Tetris\",\n" +
                "      \"description\": \"A C implementation of Tetris using Pennsim through LC4\",\n" +
                "      \"fork\": false,\n" +
                "      \"url\": \"https://api.github.com/repos/dtrupenn/Tetris\",\n" +
                "      \"created_at\": \"2012-01-01T00:31:50Z\",\n" +
                "      \"updated_at\": \"2013-01-05T17:58:47Z\",\n" +
                "      \"pushed_at\": \"2012-01-01T00:37:02Z\",\n" +
                "      \"homepage\": \"\",\n" +
                "      \"size\": 524,\n" +
                "      \"stargazers_count\": 1,\n" +
                "      \"watchers_count\": 1,\n" +
                "      \"language\": \"Assembly\",\n" +
                "      \"forks_count\": 5,\n" +
                "      \"open_issues_count\": 0,\n" +
                "      \"master_branch\": \"master\",\n" +
                "      \"default_branch\": \"master\",\n" +
                "      \"score\": 10.309712\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }
}