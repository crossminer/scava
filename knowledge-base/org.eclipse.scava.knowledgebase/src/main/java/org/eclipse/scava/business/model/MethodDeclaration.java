package org.eclipse.scava.business.model;

import java.util.ArrayList;
import java.util.List;

public class MethodDeclaration {
	private String name;
	private List<String> methodInvocations;
	
	
	public MethodDeclaration() {
		methodInvocations = new ArrayList<>();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getMethodInvocations() {
		return methodInvocations;
	}
	public void setMethodInvocations(List<String> methodInvocations) {
		this.methodInvocations = methodInvocations;
	}
}
