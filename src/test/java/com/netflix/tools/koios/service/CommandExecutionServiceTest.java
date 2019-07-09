package com.netflix.tools.koios.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CommandExecutionServiceTest {

    @Mock
    private ReportingService reportingService;

    @InjectMocks
    private CommandExecutionService commandExecutionService;

    @Test
    void run() {

        commandExecutionService.run();

        verify(reportingService, atMost(1)).displayRepoByStars(anyInt(), anyString());
        verify(reportingService, atMost(1)).displayRepoByForks(anyInt(), anyString());
        verify(reportingService, atMost(1)).displayRepoByPrs(anyInt(), anyString());
        verify(reportingService, atMost(1)).displayRepoByContribution(anyInt(), anyString());
    }
}