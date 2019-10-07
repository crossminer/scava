package org.eclipse.scava.business.dto;

import org.eclipse.scava.business.model.Artifact;

public class ArtifactSimDto {
	private double simValue;
	private Artifact artifac;
	public double getSimValue() {
		return simValue;
	}

	public void setSimValue(double simValue) {
		this.simValue = simValue;
	}

	public Artifact getArtifac() {
		return artifac;
	}

	public void setArtifac(Artifact artifac) {
		this.artifac = artifac;
	}
	
	public ArtifactSimDto(Artifact art, double value) {
		simValue = value;
		artifac = art;
	}
}
