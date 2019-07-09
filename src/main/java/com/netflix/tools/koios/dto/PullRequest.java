package com.netflix.tools.koios.dto;

public class PullRequest {

    private String repo;
    private int prCount;

    public PullRequest(String repo, int prCount) {
        this.repo = repo;
        this.prCount = prCount;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public int getPrCount() {
        return prCount;
    }

    public void setPrCount(int prCount) {
        this.prCount = prCount;
    }
}
