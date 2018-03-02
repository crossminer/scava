/*******************************************************************************
 * Copyright (c) 2018 aabherve
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.admin;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class AdminIndex extends ServerResource {

	@Get("html")
	public String represent() {
		
		return "<html><strong>s</strong>illy</html>";
	}
}
