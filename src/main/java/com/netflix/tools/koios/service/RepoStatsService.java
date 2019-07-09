package com.netflix.tools.koios.service;

public interface RepoStatsService {


    void displayRepoByStars(int topN, String repoName);

    void displayRepoByForks(int topN, String repoName);

    void displayRepoByPrs(int topN, String repoName);

    void displayRepoByContribution(int topN, String repoName);
}
