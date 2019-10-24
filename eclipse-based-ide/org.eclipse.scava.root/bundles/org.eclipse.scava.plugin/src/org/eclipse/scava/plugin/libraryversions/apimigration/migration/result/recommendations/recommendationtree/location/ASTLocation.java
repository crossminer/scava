/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.location;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class ASTLocation implements ILocation, IJaccardComparable {
	private final String location;
	private List<PathPart> locationParts;
	private Set<PathPart> jaccardInput;
	private final ASTNode node;

	public ASTLocation(MethodInvocation node) {
		IMethodBinding methodBinding = node.resolveMethodBinding();
		String declaringClass = methodBinding.getDeclaringClass().getBinaryName();
		String methodName = methodBinding.getName();
		String parameters = Arrays.stream(methodBinding.getParameterTypes()).map(ITypeBinding::getBinaryName)
				.map(ASTLocation.this::javaJVMTypeToLanguageType).collect(Collectors.joining(";"));
		String constructedName = declaringClass + "." + methodName + "(" + parameters + ")";

		location = constructedName;
		this.node = node;
		calculateJaccardInput();
	}

	public ASTLocation(MethodDeclaration node) {
		IMethodBinding methodBinding = node.resolveBinding();
		String declaringClass = methodBinding.getDeclaringClass().getBinaryName();
		String methodName = methodBinding.getName();
		String parameters = Arrays.stream(methodBinding.getParameterTypes()).map(ITypeBinding::getBinaryName)
				.map(ASTLocation.this::javaJVMTypeToLanguageType).collect(Collectors.joining(";"));
		String constructedName = declaringClass + "." + methodName + "(" + parameters + ")";

		location = constructedName;
		this.node = node;
		calculateJaccardInput();
	}

	public ASTLocation(TypeDeclaration node) {
		ITypeBinding typeBinding = node.resolveBinding();
		String binaryName = typeBinding.getBinaryName();

		location = binaryName;
		this.node = node;
		calculateJaccardInput();
	}

	public ASTLocation(ClassInstanceCreation node) {
		ITypeBinding typeBinding = node.resolveTypeBinding();
		String binaryName = typeBinding.getBinaryName();

		location = binaryName;
		this.node = node;
		calculateJaccardInput();
	}

	private String javaJVMTypeToLanguageType(String jvmType) {
		switch (jvmType) {
		case "Z":
			return "boolean";
		case "B":
			return "byte";
		case "C":
			return "char";
		case "S":
			return "short";
		case "I":
			return "int";
		case "J":
			return "long";
		case "F":
			return "float";
		case "D":
			return "double";
		default:
			if (jvmType.startsWith("[")) {
				return javaJVMTypeToLanguageType(jvmType.substring(1)) + "[]";
			} else {
				return jvmType;
			}
		}
	}

	private void calculateJaccardInput() {
		locationParts = Arrays.stream(location.split("\\.(?![^\\(]*[\\)])")).map(PathPart::new)
				.collect(Collectors.toList());
		jaccardInput = new HashSet<>(locationParts);
	}

	@Override
	public String getLocation() {
		return location;
	}

	public Set<PathPart> getJaccardInput() {
		return jaccardInput;
	}

	@Override
	public List<PathPart> getLocationParts() {
		return locationParts;
	}

	public ASTNode getNode() {
		return node;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ASTLocation other = (ASTLocation) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		return true;
	}

}
