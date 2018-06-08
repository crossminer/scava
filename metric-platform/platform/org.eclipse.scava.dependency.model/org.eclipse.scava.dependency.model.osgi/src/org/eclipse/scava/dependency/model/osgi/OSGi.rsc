module org::eclipse::scava::dependency::model::osgi::OSGi

import IO;
import lang::java::m3::Core;

import org::eclipse::scava::metricprovider::ProjectDelta;
import org::eclipse::scava::metricprovider::MetricProvider;

import org::eclipse::scava::dependency::model::osgi::model::OSGiModelBuilder;

//TODO: Consider changing this plugin to a Java extractor
@memo
OSGiModel getOSGiModelFromWorkingCopy(loc workingCopy, M3 m3) =
	createOSGiModelFromWorkingCopy(workingCopy, m3);
