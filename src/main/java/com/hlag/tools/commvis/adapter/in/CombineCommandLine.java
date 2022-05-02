package com.hlag.tools.commvis.adapter.in;

import com.hlag.tools.commvis.application.port.in.ScannerCommand;
import com.hlag.tools.commvis.application.port.in.ScannerUseCase;
import lombok.RequiredArgsConstructor;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@RequiredArgsConstructor
@CommandLine.Command(name = "combine", aliases = {"c"}, description = "Combines models from different project and creates the graph.")
public class CombineCommandLine implements Callable<Integer> {
    @CommandLine.Parameters(index = "0", description = "Top level directory containing the model.json files. Subdirectories are scanned.")
    private String topLevelDirectory;

    @CommandLine.Option(names = {"-n", "--name"}, defaultValue = "big-picture", description = "Name of the resulting graph model.")
    private String projectName;

    @Override
    public Integer call() {
        return 0;
    }
}
