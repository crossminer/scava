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
package org.eclipse.crossmeter.platform.client.api;


import org.restlet.data.Status;
import org.restlet.representation.Representation;

public class PingResource extends AbstractApiResource {
	
	public Representation doRepresent() {
		getResponse().setStatus(new Status(200));
		return Util.createJsonRepresentation("{\"status\":200, \"message\" : \"hello :)\"}");
	}
}
