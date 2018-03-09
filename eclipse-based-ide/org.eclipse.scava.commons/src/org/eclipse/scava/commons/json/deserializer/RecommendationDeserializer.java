/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*    Zsolt J�nos Szamosv�lgyi
*    Endre Tam�s V�radi
*    Gerg� Balogh
**********************************************************************/
package org.eclipse.scava.commons.json.deserializer;

import java.lang.reflect.Type;

import org.eclipse.scava.commons.recommendation.Recommendation;
import org.eclipse.scava.commons.recommendation.RecommendationKind;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * Provides a specific deserializer class for {@link Recommendation}
 *
 */
public class RecommendationDeserializer implements JsonDeserializer<Recommendation>{

	@Override
	public Recommendation deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
			throws JsonParseException {
		
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		
		JsonElement jsonKind = jsonObject.get("kind");
		RecommendationKind kind = jsonDeserializationContext.deserialize(jsonKind, RecommendationKind.class);
				
		return jsonDeserializationContext.deserialize(jsonElement, kind.getType());		
	}
}
