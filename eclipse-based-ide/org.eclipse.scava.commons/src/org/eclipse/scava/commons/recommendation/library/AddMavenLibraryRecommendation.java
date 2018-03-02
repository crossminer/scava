/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*    Zsolt J�nos Szamosv�lgyi
*    Endre Tam�s V�radi
*    Gerg� Balogh
**********************************************************************/
package org.eclipse.scava.commons.recommendation.library;

import java.util.Date;

import org.eclipse.scava.commons.buildsystem.BuildSystemType;
import org.eclipse.scava.commons.library.Library;
import org.eclipse.scava.commons.recommendation.RecommendationKind;

/**
 * Provides a representation of a maven library adder recommendation.
 *
 */
public class AddMavenLibraryRecommendation extends AddLibraryRecommendation {
	private final String groupId;
	private final String artifactId;
	private final String version;
	
	public AddMavenLibraryRecommendation(String id, Date requestTimeStamp, Library target, String groupId,
			String artifactId, String version) {
		super(id, requestTimeStamp, RecommendationKind.ADD_MAVEN_LIBRARY, BuildSystemType.MAVEN, target);
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.version = version;
	}
	
	public String getGroupId() {
		return groupId;
	}
	
	public String getArtifactId() {
		return artifactId;
	}
	
	public String getVersion() {
		return version;
	}
	
}
