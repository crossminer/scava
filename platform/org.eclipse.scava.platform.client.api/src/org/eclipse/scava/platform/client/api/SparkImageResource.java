/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.client.api;

import org.eclipse.scava.platform.client.api.cache.SparkCache;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.ByteArrayRepresentation;
import org.restlet.representation.EmptyRepresentation;
import org.restlet.representation.Representation;

/**	
 * 
 * The reason for separating these, and using a unique ID for each spark instead of
 * resolving using the projectid and metricid is that we can make numerous sparks
 * for the same project-metric combo. I.e. different date ranges or aggregation methods.
 * 
 * If the cache misses, the cache misses. 
 * @author jimmy
 *
 */
public class SparkImageResource extends AbstractApiResource {

	public Representation doRepresent() {
		String sparkId = (String) getRequest().getAttributes().get("sparkid");

		byte[] s = SparkCache.getSparkCache().getSpark(sparkId);
			
		if (s == null){
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return new EmptyRepresentation();
		} else {
			return new ByteArrayRepresentation(s, MediaType.IMAGE_PNG);
		}
	}
}
