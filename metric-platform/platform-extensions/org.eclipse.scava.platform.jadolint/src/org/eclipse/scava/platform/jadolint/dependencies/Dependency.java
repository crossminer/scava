package org.eclipse.scava.platform.jadolint.dependencies;

public class Dependency {

    private String packageName;
    private String packageVersion;

    public Dependency(String packageName, String packageVersion) {
        this.packageName = packageName;
        this.packageVersion = packageVersion;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageVersion() {
        return packageVersion;
    }

    public void setPackageVersion(String packageVersion) {
        this.packageVersion = packageVersion;
    }
}
