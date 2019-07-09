package com.netflix.tools.koios.service;

import com.netflix.tools.koios.dto.Item;
import com.netflix.tools.koios.dto.PullRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsoleRenderService implements RenderService {

    @Override
    public void renderRepoByStars(List<Item> items) {
        printHeader("Repos by Stars");
        printItemsWithStars(items);
        printFooter();
    }

    @Override
    public void renderRepoByForks(List<Item> items) {
        printHeader("Repos by forks");
        printItemsWithForks(items);
        printFooter();
    }

    @Override
    public void renderRepoByPrs(List<PullRequest> pullRequests) {
        printHeader("Repos by PRs");
        printPullRequests(pullRequests);
        printFooter();
    }

    @Override
    public void renderRepoByContributionPercent(List<Item> items) {
        printHeader("Repos by Contribution Percent");
        printReposByContributionPercent(items);
        printFooter();
    }

    private void printReposByContributionPercent(List<Item> items) {
        items.forEach(item -> System.out.printf(item.getName() + " -> %.1f %%\n", item.getContributionPercent() * 100));
    }

    private void printItemsWithStars(List<Item> items) {
        items.forEach(item -> System.out.println(item.getName() + " -> " + item.getStargazersCount()));
    }

    private void printItemsWithForks(List<Item> items) {
        items.forEach(item -> System.out.println(item.getName() + " -> " + item.getForksCount()));
    }

    private void printPullRequests(List<PullRequest> pullRequests) {
        pullRequests.forEach(pullRequest -> System.out.println(pullRequest.getRepo() + " -> " + pullRequest.getPrCount()));
    }

    private void printHeader(String message) {
        System.out.println("-------------------------");
        System.out.println(message);
        System.out.println("-------------------------");
    }

    private void printFooter() {
        System.out.println("");
        System.out.println("");
    }
}
