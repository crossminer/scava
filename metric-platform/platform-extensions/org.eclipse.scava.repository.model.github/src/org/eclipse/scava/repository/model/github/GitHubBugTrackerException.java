/*******************************************************************************
 * Copyright (c) 2018 Edge Hill University.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: 
 * 	  Luis Adri�n Cabrera Diego - Implementation.
 *  
 *******************************************************************************/
package org.eclipse.scava.repository.model.github;

@SuppressWarnings("serial")
public class GitHubBugTrackerException extends Exception {
	public GitHubBugTrackerException(String message)
	{
		super(message);
	}
}
