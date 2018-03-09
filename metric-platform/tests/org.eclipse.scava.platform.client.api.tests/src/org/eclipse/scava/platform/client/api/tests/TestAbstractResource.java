/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.client.api.tests;

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
