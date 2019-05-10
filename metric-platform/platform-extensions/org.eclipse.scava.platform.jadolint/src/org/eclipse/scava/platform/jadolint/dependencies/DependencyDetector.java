package org.eclipse.scava.platform.jadolint.dependencies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.platform.jadolint.model.Run;
import org.eclipse.scava.platform.jadolint.model.RunBlock;

public class DependencyDetector {

    private List<Dependency> dependencies = new ArrayList<Dependency>();

    public DependencyDetector() {

    }

    public void detectDependency(String line) {
        String[] splitLine = line.split(" ", 2);

        String instruction = splitLine[0];

        switch(instruction) {
        case "FROM":
            checkForFromDependency(splitLine[1]);
            break;
        case "RUN":
            checkForRunDependency(line);
            break;
        }
    }

    private void checkForFromDependency(String lineWithoutInstruction) {
        String packageName;
        String packageVersion = null;
        if (lineWithoutInstruction.contains(":")) {
            packageName = lineWithoutInstruction.split(":", 2)[0];
            packageVersion = lineWithoutInstruction.split(":", 2)[1];
        } else {
            packageName = lineWithoutInstruction;
        }

        dependencies.add(new FromDependency(packageName, packageVersion));
    }

    private void checkForRunDependency(String line) {
        List<RunBlock> runBlocksInstall = new Run(line).getAptGetInstallBlocks();

        for (RunBlock runblock : runBlocksInstall) {
            String[] splitLine = runblock.getParams().split(" ");
            String packageInfo = splitLine[splitLine.length - 1];

            if (packageInfo.contains("=")) {
                String name = packageInfo.split("=", 2)[0];
                String version = packageInfo.split("=", 2)[1];
                dependencies.add(new RunDependency(name, version));
            }
        }
    }

}
