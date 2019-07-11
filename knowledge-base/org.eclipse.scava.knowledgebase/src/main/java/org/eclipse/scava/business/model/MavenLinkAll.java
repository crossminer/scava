package org.eclipse.scava.business.model;

import org.springframework.data.annotation.Id;

public class MavenLinkAll {
	@Id
	private String id;
	private String coordinate;
	private String coordinateDep;

	public MavenLinkAll() {
		super();
	}
	public MavenLinkAll(String coordinate, String coordinateDep) {
		this.coordinate = coordinate;
		this.coordinateDep = coordinateDep;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}
	public String getCoordinateDep() {
		return coordinateDep;
	}
	public void setCoordinateDep(String coordinateDep) {
		this.coordinateDep = coordinateDep;
	}
}
