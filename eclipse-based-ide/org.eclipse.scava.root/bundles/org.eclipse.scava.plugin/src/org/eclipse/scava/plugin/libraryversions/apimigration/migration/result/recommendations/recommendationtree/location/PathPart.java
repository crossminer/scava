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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PathPart {
	private final String name;
	private final List<PathPart.Parameter> parameters;

	public PathPart(String location) {

		if (location.contains("(") && location.contains(")")) {
			parameters = new ArrayList<>();
			int indexOfOpenParenthesis = location.indexOf("(");
			int indexOfCloseParenthesis = location.indexOf(")");

			this.name = location.substring(0, indexOfOpenParenthesis);

			String parametersString = location.substring(indexOfOpenParenthesis + 1, indexOfCloseParenthesis);
			String[] parametersPart = parametersString.split(";");
			for (String parameter : parametersPart) {
				if (!parameter.isEmpty()) {
					parameters.add(new Parameter(parameter));
				}
			}
		} else {
			parameters = null;
			this.name = location;
		}
	}

	public String getName() {
		return name;
	}

	public String toLocationString() {
		if (parameters == null) {
			return name;
		} else {
			return name + "(" + parameters.stream().map(Parameter::getType).collect(Collectors.joining(";")) + ")";
		}
	}

	public List<PathPart.Parameter> getParameters() {
		return parameters;
	}

	@Override
	public String toString() {
		return "PathPart [name=" + name + ", parameters=" + parameters + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parameters == null) ? 0 : parameters.hashCode());
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
		PathPart other = (PathPart) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parameters == null) {
			if (other.parameters != null)
				return false;
		} else if (!parameters.equals(other.parameters))
			return false;
		return true;
	}

	public static class Parameter {
		private final String type;

		public Parameter(String type) {
			super();
			this.type = type;
		}

		public String getType() {
			return type;
		}

		@Override
		public String toString() {
			return "Parameter [type=" + type + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((type == null) ? 0 : type.hashCode());
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
			Parameter other = (Parameter) obj;
			if (type == null) {
				if (other.type != null)
					return false;
			} else if (!type.equals(other.type))
				return false;
			return true;
		}

	}

}