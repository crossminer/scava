package org.eclipse.scava.platform.jadolint.dependencies;

public class FromDependency extends Dependency {
    private String packageName, packageVersion;

    public FromDependency(String packageName, String packageVersion) {
        super(packageName, packageVersion);
    }
}
