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

import java.lang.reflect.Type;

import org.eclipse.scava.commons.ITypeRepresentation;

/**
 * Provides an enumeration-safe set of kinds of {@link ASTDetail}.
 * @see ASTDetail
 */
public enum ASTDetailKind implements ITypeRepresentation{
	
	VARIABLE_CLASS			(new String[]{"variable", "class"}, ASTDetailVariable.class),
	VARIABLE_INTERFACE		(new String[]{"variable", "interface"}, ASTDetailVariable.class),
	METHOD					(new String[]{"method"}, ASTDetailMethod.class),
	CONSTRUCTOR				(new String[]{"method", "constructor"}, ASTDetailConstructor.class),
	GETTER					(new String[]{"method", "getter"}, ASTDetailMethod.class),
	SETTER					(new String[]{"method", "setter"}, ASTDetailMethod.class),
	LAMBDAMETHOD			(new String[]{"method", "lambda"}, ASTDetailLambdaMethod.class),
	TYPE_CLASS				(new String[]{"type", "class"}, ASTDetailTypeName.class),
	TYPE_INTERFACE			(new String[]{"type", "interface"}, ASTDetailTypeName.class);
	
	private final String[] params;
	private final Type type;
	
	private ASTDetailKind(String[] params, Type type) {
		this.params = params;
		this.type = type;
	}

	public static ASTDetailKind getByName(String name) throws EnumConstantNotPresentException {
		for (ASTDetailKind kind : values()) {
			if( kind.toString().equals(name) ) {
				return kind;
			}
		}
		
		throw new EnumConstantNotPresentException(ASTDetailKind.class, name);
	}
	
	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		
		for (int i = 0; i < params.length; i++)	{
			if( i > 0 ) {
				stringBuffer.append('.');
			}
			stringBuffer.append( params[i] );
		}
		
		return stringBuffer.toString();
	}

	@Override
	public Type getType() {
		return type;
	}
}
