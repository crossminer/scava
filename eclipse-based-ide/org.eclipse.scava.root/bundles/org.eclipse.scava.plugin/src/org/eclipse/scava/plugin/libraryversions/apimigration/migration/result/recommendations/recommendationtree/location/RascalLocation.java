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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RascalLocation implements ILocation, IJaccardComparable {

	private final String scheme;
	private final List<PathPart> parts;
	private final Set<PathPart> jaccardInput;
	private final String location;

	public RascalLocation(String location) {
		int indexOfSeparator;
		if (location != null && !location.isEmpty() && (indexOfSeparator = location.indexOf(":///")) != -1) {
			location = fixLocation(location);

			scheme = location.substring(1, indexOfSeparator);

			String path = location.substring(indexOfSeparator + 4, location.length() - 1);
			String[] pathParts = path.split("/");
			parts = Arrays.stream(pathParts).map(PathPart::new).collect(Collectors.toList());
		} else {
			scheme = "";
			parts = new ArrayList<>();
		}

		jaccardInput = new HashSet<>(parts);
		this.location = parts.stream().map(PathPart::toLocationString).collect(Collectors.joining("."));
	}

	private String fixLocation(String location) {
		return location.replaceAll("%5B", "\\[").replaceAll("%5D", "\\]").replaceAll(",", ";");
	}

	public boolean isEmpty() {
		return scheme.equals("") && parts.isEmpty();
	}

	public String getScheme() {
		return scheme;
	}

	public String getSchemeType() {
		return scheme.contains("+") ? scheme.split("\\+")[1] : scheme;
	}

	@Override
	public String toString() {
		return "RascalLocation [scheme=" + scheme + ", parts=" + parts + "]";
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
		return parts;
	}
}
