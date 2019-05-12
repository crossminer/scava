package org.eclipse.scava.platform.jadolint.dependencies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.platform.jadolint.model.From;
import org.eclipse.scava.platform.jadolint.model.Run;
import org.eclipse.scava.platform.jadolint.model.RunBlock;
import org.eclipse.scava.platform.jadolint.util.JadolintUtils;

public class DependencyDetector {

    private List<Dependency> dependencies = new ArrayList<Dependency>();

    public void detectDependency(String line) {
        String instruction = JadolintUtils.getInstruction(line);

        switch(instruction) {
        case "FROM":
            checkForFromDependency(line);
            break;
        case "RUN":
            checkForRunDependency(line);
            break;
        }
    }

    private void checkForFromDependency(String line) {
        From from = new From(line);
        
        String packageName = from.getImage();
        
        if(from.getTag() != null)
            dependencies.add(new FromDependency(packageName, from.getTag()));
        else
            dependencies.add(new FromDependency(packageName, null));
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
            } else{
                dependencies.add(new RunDependency(packageInfo, null));
            }
        }
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }
}
