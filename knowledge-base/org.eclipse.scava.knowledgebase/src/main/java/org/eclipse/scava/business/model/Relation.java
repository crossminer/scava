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

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * @author Juri Di Rocco
 *
 */
public class Relation {
	@Id
	private String id;
	@DBRef(lazy = true)
	private Artifact fromArtifact;
	@DBRef(lazy = true)
	private Artifact toArtifact;
	private double value;
	private RelationType type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Relation() {

	}

	public Relation(Artifact from, Artifact to, double value, RelationType relType){
		this.fromArtifact = from;
		this.toArtifact = to;
		this.value = value;
		this.type = relType;
	}
	public Artifact getToProject() {
		return toArtifact;
	}

	public void setToProject(Artifact toProject) {
		this.toArtifact = toProject;
	}

	public Artifact getFromProject() {
		return fromArtifact;
	}
	public void setFromProject(Artifact fromProject) {
		this.fromArtifact = fromProject;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public RelationType getType() {
		return type;
	}

	public void setType(RelationType type) {
		this.type = type;
	}
}
