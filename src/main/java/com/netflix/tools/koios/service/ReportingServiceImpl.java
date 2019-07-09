package com.netflix.tools.koios.service;

import com.netflix.tools.koios.client.GitHubClient;
import com.netflix.tools.koios.dto.Item;
import com.netflix.tools.koios.dto.PullRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReportingServiceImpl implements ReportingService {

    @Autowired
    private GitHubClient gitHubClient;

    @Autowired
    private RenderService renderService;

    @Override
    public void displayRepoByStars(int topN, String repoName) {
        List<Item> topReposByStars = gitHubClient.getTopReposByStars(topN, repoName);
        renderService.renderRepoByStars(topReposByStars);
    }

    @Override
    public void displayRepoByForks(int topN, String repoName) {
        List<Item> topReposByForks = gitHubClient.getTopReposByForks(topN, repoName);
        renderService.renderRepoByForks(topReposByForks);
    }

    @Override
    public void displayRepoByPrs(int topN, String repoName) {
        List<PullRequest> pullRequests = gitHubClient.getTopReposByPrs(topN, repoName);
        renderService.renderRepoByPrs(pullRequests);
    }

    @Override
    public void displayRepoByContribution(int topN, String repoName) {

        List<Item> topReposByContributionPercent = gitHubClient.getTopReposByContributionPercent(topN, repoName);
        renderService.renderRepoByContributionPercent(topReposByContributionPercent);
    }
}
