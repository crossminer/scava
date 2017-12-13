/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Jacob Carter - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.platform.bugtrackingsystem.bitbucket.api;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BitbucketLinks implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Map<String, String> values = new HashMap<String, String>();
	
	public Map<String, String> getValues() {
		return values;
	}

}
