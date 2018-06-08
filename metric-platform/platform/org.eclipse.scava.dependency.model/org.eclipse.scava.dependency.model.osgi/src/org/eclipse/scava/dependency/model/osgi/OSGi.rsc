module org::eclipse::scava::dependency::model::osgi::OSGi

import IO;

import org::eclipse::scava::metricprovider::ProjectDelta;
import org::eclipse::scava::metricprovider::MetricProvider;

import org::eclipse::scava::dependency::model::osgi::model::OSGiModelBuilder;


//TODO: Consider changing this plugin to a Java extractor
@memo
OSGiModel getOSGiModelFromWorkingCopy(loc workingCopy) =
	createOSGiModelFromWorkingCopy(workingCopy);
