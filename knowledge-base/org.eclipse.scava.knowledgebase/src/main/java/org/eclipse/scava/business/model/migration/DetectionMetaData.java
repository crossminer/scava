package org.eclipse.scava.business.model.migration;

import java.util.List;

import org.maracas.data.Detection;
import org.springframework.data.annotation.Id;

public class DetectionMetaData {
	@Id
	private String id;
	private String coordinate1;
	private String coordinate2;
	private String clientM3;
	private String clientSource;
	private List<Detection> detections;
	public DetectionMetaData() {
		super();
	}
	
	public DetectionMetaData(String coordinate1, String coordinate2, String clientM3, List<Detection> detections) {
		super();
		this.coordinate1 = coordinate1;
		this.coordinate2 = coordinate2;
		this.clientM3 = clientM3;
		this.detections = detections;
	}
	public String getCoordinate1() {
		return coordinate1;
	}
	public void setCoordinate1(String libv1) {
		this.coordinate1 = libv1;
	}
	
	public String getCoordinate2() {
		return coordinate2;
	}
	public void setCoordinate2(String libv2) {
		this.coordinate2 = libv2;
	}
	
	public String getClientM3() {
		return clientM3;
	}
	public void setClientM3(String clientM3) {
		this.clientM3 = clientM3;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public List<Detection> getDetections() {
		return detections;
	}

	public void setDetections(List<Detection> detections) {
		this.detections = detections;
	}

	public String getClientSource() {
		return clientSource;
	}

	public void setClientSource(String clientSource) {
		this.clientSource = clientSource;
	}

}
