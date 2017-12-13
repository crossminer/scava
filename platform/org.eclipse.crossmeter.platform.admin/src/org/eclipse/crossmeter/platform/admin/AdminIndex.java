/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    James Williams - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.platform.admin;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class AdminIndex extends ServerResource {

	@Get("html")
	public String represent() {
		
		return "<html><strong>s</strong>illy</html>";
	}
}
