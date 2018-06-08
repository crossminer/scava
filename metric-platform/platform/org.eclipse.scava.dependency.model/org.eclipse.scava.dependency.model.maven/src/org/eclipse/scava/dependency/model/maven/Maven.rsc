module org::eclipse::scava::dependency::model::maven::Maven

import IO;
import lang::java::m3::Core;

import org::eclipse::scava::metricprovider::ProjectDelta;
import org::eclipse::scava::metricprovider::MetricProvider;

import org::eclipse::scava::dependency::model::maven::model::MavenModelBuilder;

//TODO: Consider changing this plugin to a Java extractor
@memo
MavenModel getMavenModelFromWorkingCopy(loc workingCopy) =
	createMavenModelFromWorkingCopy(workingCopy);