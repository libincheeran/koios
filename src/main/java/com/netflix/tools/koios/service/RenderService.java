package com.netflix.tools.koios.service;

import com.netflix.tools.koios.dto.Item;
import com.netflix.tools.koios.dto.PullRequest;

import java.util.List;

public interface RenderService {

    void renderRepoByStars(List<Item> items);

    void renderRepoByForks(List<Item> items);

    void renderRepoByPrs(List<PullRequest> pullRequests);

    void renderRepoByContributionPercent(List<Item> items);
}
