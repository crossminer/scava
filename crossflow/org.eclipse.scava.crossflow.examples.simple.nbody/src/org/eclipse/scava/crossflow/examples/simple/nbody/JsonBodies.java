/*********************************************************************
* Copyright (c) 2019 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* Contributors:
*     Horacio Hoyos - initial API and implementation
**********************************************************************/
package org.eclipse.scava.crossflow.examples.simple.nbody;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Create bodies by loading them from a JSON file. The file must be an array of objects, where
 * each object has the following structure:
 * <p>
 * <code>
 *	{
		"x": -0.773767,
		"y": 0.843888,
		"z": 0.739989,
		"vx": -0.725803,
		"vy": 0.41869,
		"vz": 0.944066,
		"m": -0.544131,
	}
 * 
 * </code>
 * 
 * @author Horacio Hoyos Rodriguez
 *
 */
public class JsonBodies implements Bodies {

	private final Path data;
	
	public JsonBodies(Path data) {
		super();
		this.data = data;
	};

	@Override
	public Set<NBody3DBody> createBodies() throws CreatingBodiesException {
		Set<NBody3DBody> result = new HashSet<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(data.toFile());
			System.out.println(jsonNode.isArray());
			for (final JsonNode body : jsonNode) {
	    		result.add(new SofteningNBodyBody(
					new StockVector3D(
							body.get("x").asDouble(),
							body.get("y").asDouble(),
							body.get("z").asDouble()),
					new StockVector3D(
							body.get("vx").asDouble(),
							body.get("vy").asDouble(),
							body.get("vz").asDouble()),
					body.get("m").asDouble())
					);
			}
		} catch (IOException e) {
			throw new CreatingBodiesException("Error reading body information from JSON");
		}	
		return result;
	}

}
