/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*    Zsolt J�nos Szamosv�lgyi
*    Endre Tam�s V�radi
*    Gerg� Balogh
**********************************************************************/
package org.eclipse.scava.commons.context.sourcecode.lineinfo.detail;

import java.util.List;

/**
 * Provides a representation of an annotation from the source code.
 *
 */
public class ASTResolvedAnnotation {
	private final ASTResolvedType type;
	private final List<Parameter> parameters;
	
	public ASTResolvedAnnotation(ASTResolvedType type, List<Parameter> parameters) {
		this.type = type;
		this.parameters = parameters;
	}
	
	public ASTResolvedType getType() {
		return type;
	}
	
	public List<Parameter> getParameters() {
		return parameters;
	}
	
	/**
	 * Provides a representation of a parameter of an annotation from the source code.
	 * @author Endre Tam�s V�radi
	 *
	 */
	public static class Parameter {
		private final String key;
		private final Object value;
		private final boolean isDefaultValue;
		
		public Parameter(String key, Object value, boolean isDefaultValue) {
			this.key = key;
			this.value = value;
			this.isDefaultValue = isDefaultValue;
		}
		
		public String getKey() {
			return key;
		}
		
		public Object getValue() {
			return value;
		}
		
		public boolean isDefaultValue() {
			return isDefaultValue;
		}
	}
}
