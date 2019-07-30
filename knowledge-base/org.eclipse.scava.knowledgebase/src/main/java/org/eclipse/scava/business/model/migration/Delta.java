package org.eclipse.scava.business.model.migration;

import org.springframework.data.annotation.Id;

public class Delta {
	@Id
	private String id;
	private String coordinate1;
	private String coordinate2;
	private String deltaPath;
	
	public Delta() {
		super();
	}
	
	public Delta(String coordinateLibv1, String coordinateLibv2, String deltaPath) {
		super();
		this.coordinate1 = coordinateLibv1;
		this.coordinate2 = coordinateLibv2;
		this.deltaPath = deltaPath;
	}
	public String getCoordinate1() {
		return coordinate1;
	}
	public void setCoordinate1(String libv1) {
		this.coordinate1 = libv1;
	}
	
	public String getCoordinateLibv2() {
		return coordinate2;
	}
	public void setCoordinate2(String libv2) {
		this.coordinate2 = libv2;
	}
	
	public String getDeltaPath() {
		return deltaPath;
	}
	public void setDeltaPath(String deltas) {
		this.deltaPath = deltas;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
