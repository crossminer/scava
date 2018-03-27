module org::eclipse::scava::dependency::model::maven::util::MavenVersionOptions


//Versions without ranges and braces, such as: "1.0.bla"
public str SOFT_VERSION = "softVersion";

// One version enclosed in square brackets, such as: "[1.0]"
public str HARD_VERSION = "hardVersion";

// Range of versions enclosed in curly or square braces, such as: "[1.1,)", "(1.0,3.4]", "(,1.1]"
public str RANGE_VERSION = "rangeVersion";

// List of comma delimited version ranges, such as: "(,1.0],[3.0,4.5)"
public str MULTIPLE_RANGE_VERSIONS = "multipleRangeVersions";

// Badly specified version.
public str ERROR_VERSION = "errorVersion";