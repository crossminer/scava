/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.location;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PathPartLocation implements ILocation, IJaccardComparable {

	private final Set<PathPart> jaccardInput;
	private final List<PathPart> pathParts;
	private final String location;

	public PathPartLocation(List<PathPart> pathParts) {
		super();

		this.pathParts = pathParts;
		jaccardInput = new HashSet<>(pathParts);
		location = pathParts.stream().map(PathPart::toLocationString).collect(Collectors.joining("."));
	}

	@Override
	public Set<PathPart> getJaccardInput() {
		return jaccardInput;
	}

	@Override
	public String getLocation() {
		return location;
	}

	@Override
	public List<PathPart> getLocationParts() {
		return pathParts;
	}

}
