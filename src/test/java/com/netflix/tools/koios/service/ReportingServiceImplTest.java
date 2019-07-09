package com.netflix.tools.koios.service;

import com.netflix.tools.koios.client.GitHubClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ReportingServiceImplTest {

    @Mock
    private GitHubClient gitHubClient;

    @Mock
    private RenderService renderService;

    @InjectMocks
    private ReportingServiceImpl reportingService;

    @Test
    void displayRepoByStars() {

        reportingService.displayRepoByStars(10, "abc");
        verify(gitHubClient).getTopReposByStars(10, "abc");
        verify(renderService).renderRepoByStars(anyList());
    }

    @Test
    void displayRepoByForks() {
        reportingService.displayRepoByForks(10, "abc");
        verify(gitHubClient).getTopReposByForks(10, "abc");
        verify(renderService).renderRepoByForks(anyList());
    }

    @Test
    void displayRepoByPrs() {
        reportingService.displayRepoByPrs(10, "abc");
        verify(gitHubClient).getTopReposByPrs(10, "abc");
        verify(renderService).renderRepoByPrs(anyList());
    }

    @Test
    void displayRepoByContribution() {
        reportingService.displayRepoByContribution(10, "abc");
        verify(gitHubClient).getTopReposByContributionPercent(10, "abc");
        verify(renderService).renderRepoByContributionPercent(anyList());
    }
}