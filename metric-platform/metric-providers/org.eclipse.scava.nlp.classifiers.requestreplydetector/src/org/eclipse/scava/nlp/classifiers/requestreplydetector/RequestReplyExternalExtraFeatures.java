/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.classifiers.requestreplydetector;

import org.eclipse.scava.nlp.tools.predictions.externalExtra.ExternalExtraFeaturesObject;

public class RequestReplyExternalExtraFeatures implements ExternalExtraFeaturesObject
{
	private boolean hasCode =false;
	private boolean hadReplies = false;
	
	
	public RequestReplyExternalExtraFeatures(boolean hasCode, boolean hadReplies)
	{
		this.hasCode=hasCode;
		this.hadReplies=hadReplies;
	}
	
	public boolean hasCode()
	{
		return hasCode;
	}

	public boolean hadReplies()
	{
		return hadReplies;
	}
	
	

}
