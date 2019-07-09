package com.netflix.tools.koios.service;

import com.netflix.tools.koios.dto.Item;
import com.netflix.tools.koios.dto.PullRequest;

import java.util.List;

public interface RenderService {

    /**
     * Renders the repo stats with stars as a dimension to an appropriate output source
     *
     * @param items repo details
     */
    void renderRepoByStars(List<Item> items);

    /**
     * Renders the repo stats with forks as a dimension to an appropriate output source
     *
     * @param items repo details
     */
    void renderRepoByForks(List<Item> items);

    /**
     * Renders the repo stats with PRs as a dimension to an appropriate output source
     *
     * @param pullRequests PR details
     */
    void renderRepoByPrs(List<PullRequest> pullRequests);

    /**
     * Renders the repo stats with Contribution percentage as a dimension to an
     * appropriate output source
     *
     * @param items repo details
     */
    void renderRepoByContributionPercent(List<Item> items);
}
