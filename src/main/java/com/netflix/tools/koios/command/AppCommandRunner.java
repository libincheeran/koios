package com.netflix.tools.koios.command;

import com.netflix.tools.koios.service.CommandExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
public class AppCommandRunner implements CommandLineRunner {

    @Autowired
    private CommandExecutionService commandExecutionService;

    @Override
    public void run(String... args) throws Exception {
        CommandLine.run(commandExecutionService, System.err, args);
    }
}
