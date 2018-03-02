/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.business.dto;

import java.util.List;

/**
 * @author Juri Di Rocco
 *
 */
public class Query {

	private List<Dependency> projectDependencies;
	private String projectName;
	private List<String> annotations;
	private List<String> comments;
	private int textOffset;
	private List<Dependency> classDependencies;
	private String compilationUnit;
	private String methodInvocation;
	private List<Parameter> parameter;
	private String refClassInvocation;
	private boolean live;
	private String similarityMethod;
	public String getCompilationUnit() {
		return compilationUnit;
	}
	public void setCompilationUnit(String compilationUnit) {
		this.compilationUnit = compilationUnit;
	}
	public List<Dependency> getProjectDependencies() {
		return projectDependencies;
	}
	public void setProjectDependencies(List<Dependency> projectDependecies) {
		this.projectDependencies = projectDependecies;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public List<String> getAnnotations() {
		return annotations;
	}
	public void setAnnotations(List<String> annotations) {
		this.annotations = annotations;
	}
	public List<String> getComments() {
		return comments;
	}
	public void setComments(List<String> comments) {
		this.comments = comments;
	}
	public List<Dependency> getClassDependencies() {
		return classDependencies;
	}
	public void setClassDependencies(List<Dependency> classDependencies) {
		this.classDependencies = classDependencies;
	}
	
	public List<Parameter> getParameter() {
		return parameter;
	}
	public void setParameter(List<Parameter> parameter) {
		this.parameter = parameter;
	}
	public int getTextOffset() {
		return textOffset;
	}
	public void setTextOffset(int textOffset) {
		this.textOffset = textOffset;
	}
	public String getMethodInvocation() {
		return methodInvocation;
	}
	public void setMethodInvocation(String methodInvocation) {
		this.methodInvocation = methodInvocation;
	}
	public String getRefClassInvocation() {
		return refClassInvocation;
	}
	public void setRefClassInvocation(String refClassInvocation) {
		this.refClassInvocation = refClassInvocation;
	}
	public boolean isLive() {
		return live;
	}
	public void setLive(boolean live) {
		this.live = live;
	}
	public String getSimilarityMethod() {
		return similarityMethod;
	}
	public void setSimilarityMethod(String similarityMethod) {
		this.similarityMethod = similarityMethod;
	}
}
