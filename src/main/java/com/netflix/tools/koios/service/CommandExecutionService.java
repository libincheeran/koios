package com.netflix.tools.koios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "CommandExecutor", header = "GitHub Repo Stats")
public class CommandExecutionService implements Runnable {

    @Autowired
    private RepoStatsService repoStatsService;

    @CommandLine.Option(names = {"-n", "--top-n"}, required = true, description = "The top N number of records.",
            defaultValue = "10")
    private int topN;

    @CommandLine.Option(names = {"-o", "--org-name"}, required = true, description = "Organization name in GitHub")
    private String orgName;

    @Override
    public void run() {

        repoStatsService.displayRepoByStars(topN, orgName);
        repoStatsService.displayRepoByForks(topN, orgName);
        repoStatsService.displayRepoByPrs(topN, orgName);
        repoStatsService.displayRepoByContribution(topN, orgName);
    }
}
