package org.eclipse.scava.business.model.migration;

import java.util.List;

import org.maracas.data.Detection;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

public class DetectionResult {
//	private String clientSource;
	private Detection detection;
	private List<DetectionMetaData> detectionsMD = Lists.newArrayList();
	
//	public String getClientSource() {
//		return clientSource;
//	}
//	public void setClientSource(String name) {
//		this.clientSource = name;
//	}
	public Detection getDetection() {
		return detection;
	}
	public void setDetection(Detection detection) {
		this.detection = detection;
	}
	public List<DetectionMetaData> getDetectionsMD() {
		return detectionsMD;
	}
	public void setDetectionsMD(List<DetectionMetaData> detectionsMD) {
		this.detectionsMD = detectionsMD;
	}
	//	public MultiValueMap<Detection, Detection> getResult() {
//		return result;
//	}
//	public void MultiValueMap(MultiValueMap<Detection, Detection> result) {
//		this.result = result;
//	}
	

}
