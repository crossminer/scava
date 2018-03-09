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

import org.eclipse.scava.commons.transaction.Transaction;
import org.eclipse.scava.commons.transaction.TransactionKind;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * Provides a specific deserializer class for {@link Transaction}
 *
 */
public class TransactionDeserializer  implements JsonDeserializer<Transaction>{

	@Override
	public Transaction deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
			throws JsonParseException {
		
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		
		JsonElement jsonMessageKind = jsonObject.get("kind");
		TransactionKind messageKind = jsonDeserializationContext.deserialize(jsonMessageKind, TransactionKind.class);
		 
		
		return jsonDeserializationContext.deserialize(jsonElement, messageKind.getType());
	}

}
