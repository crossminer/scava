package org.eclipse.scava.business.dto;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.business.model.MethodDeclaration;

public class APIMigrationRequest {
	private List<MethodDeclaration> methodDeclarations = new ArrayList<>();; 

	private String coordinateLibV1;
	
	private String coordinateLibV2;
	
	public List<MethodDeclaration> getMethodDeclarations() {
		return methodDeclarations;
	}
	public void setMethodDeclarations(List<MethodDeclaration> methodDeclarations) {
		this.methodDeclarations = methodDeclarations;
	}
	public String getCoordinateLibV1() {
		return coordinateLibV1;
	}
	public void setCoordinateLibV1(String coordinateLibV1) {
		this.coordinateLibV1 = coordinateLibV1;
	}
	public String getCoordinateLibV2() {
		return coordinateLibV2;
	}
	public void setCoordinateLibV2(String coordinateLibV2) {
		this.coordinateLibV2 = coordinateLibV2;
	}
	
}
