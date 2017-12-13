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
package org.eclipse.crossmeter.platform.client.api.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.restlet.Response;
import org.restlet.data.MediaType;

public abstract class TestAbstractResource {

	protected void validateResponse(Response response, int expectedResponse) {
		assertEquals(expectedResponse, response.getStatus().getCode());
		assertTrue(response.isEntityAvailable());
		assertTrue(response.getEntity().getMediaType().equals(MediaType.APPLICATION_JSON));
		
	}
}
