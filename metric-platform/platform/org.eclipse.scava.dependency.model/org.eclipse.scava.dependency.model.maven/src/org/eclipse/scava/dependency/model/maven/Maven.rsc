@license{
	Copyright (c) 2018 Centrum Wiskunde & Informatica
	
	This program and the accompanying materials are made
	available under the terms of the Eclipse Public License 2.0
	which is available at https://www.eclipse.org/legal/epl-2.0/
	
	SPDX-License-Identifier: EPL-2.0
}
module org::eclipse::scava::dependency::model::maven::Maven

import IO;
import lang::java::m3::Core;

import org::eclipse::scava::metricprovider::ProjectDelta;
import org::eclipse::scava::metricprovider::MetricProvider;

import org::eclipse::scava::dependency::model::maven::model::MavenModel;
import org::eclipse::scava::dependency::model::maven::model::MavenModelBuilder;

//TODO: Consider changing this plugin to a Java extractor
@memo
MavenModel getMavenModelFromWorkingCopy(loc workingCopy) =
	createMavenModelFromWorkingCopy(workingCopy);