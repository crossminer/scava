/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.business.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * @author Juri Di Rocco
 *
 */
public class Cluster {
	@DBRef
	private Clusterization clusterization;
	private List<Tag> tags;
	private Artifact mostRepresentative;
	@DBRef
	private List<Artifact> artifacts;
	
	public Artifact getMostRepresentative() {
		return mostRepresentative;
	}
	public void setMostRepresentative(Artifact mostRepresentative) {
		this.mostRepresentative = mostRepresentative;
	}
	public List<Artifact> getArtifacts() {
		return artifacts;
	}
	public void setArtifacts(List<Artifact> artifacts) {
		this.artifacts = artifacts;
	}
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	public Clusterization getClusterization() {
		return clusterization;
	}
	public void setClusterization(Clusterization clusterization) {
		this.clusterization = clusterization;
	}
}
