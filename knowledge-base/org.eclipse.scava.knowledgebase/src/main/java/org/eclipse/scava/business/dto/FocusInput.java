package org.eclipse.scava.business.dto;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.business.model.MethodDeclaration;

public class FocusInput {
	private List<MethodDeclaration> methodDeclarations = new ArrayList<>();; 
	private String activeDeclaration;
	public String getActiveDeclaration() {
		return activeDeclaration;
	}
	public void setActiveDeclaration(String activeDeclaration) {
		this.activeDeclaration = activeDeclaration;
	}
	public List<MethodDeclaration> getMethodDeclarations() {
		return methodDeclarations;
	}
	public void setMethodDeclarations(List<MethodDeclaration> methodDeclarations) {
		this.methodDeclarations = methodDeclarations;
	}
	
}
