/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.bitbucket.api;

public class Bitbucket {
	public static final String PRIORITY_TRIVIAL = "trivial";
	public static final String PRIORITY_MINOR = "minor";
	public static final String PRIORITY_MAJOR = "major";
	public static final String PRIORITY_CRITICAL = "critical";
	public static final String PRIORITY_BLOCKER = "blocker";
	
	public static final String PRIORITY_NOT_TRIVIAL = "!trivial";
	public static final String PRIORITY_NOT_MINOR = "!minor";
	public static final String PRIORITY_NOT_MAJOR = "!major";
	public static final String PRIORITY_NOT_CRITICAL = "!critical";
	public static final String PRIORITY_NOT_BLOCKER = "!blocker";
	
	public static final String STATUS_NEW = "new";
	public static final String STATUS_OPEN = "open";
	public static final String STATUS_RESOLVED = "resolved";
	public static final String STATUS_ON_HOLD = "on hold";
	public static final String STATUS_INVALID = "invalid";
	public static final String STATUS_DUPLICATE = "duplicate";
	public static final String STATUS_WONTFIX = "wontfix";
	
	public static final String STATUS_NOT_NEW = "!new";
	public static final String STATUS_NOT_OPEN = "!open";
	public static final String STATUS_NOT_RESOLVED = "!resolved";
	public static final String STATUS_NOT_ON_HOLD = "!on hold";
	public static final String STATUS_NOT_INVALID = "!invalid";
	public static final String STATUS_NOT_DUPLICATE = "!duplicate";
	public static final String STATUS_NOT_WONTFIX = "!wontfix";
}
