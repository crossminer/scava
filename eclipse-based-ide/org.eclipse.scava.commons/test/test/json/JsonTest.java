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
package test.json;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.scava.commons.json.Json;
import org.eclipse.scava.commons.library.Library;
import org.eclipse.scava.commons.library.ReleaseType;
import org.eclipse.scava.commons.library.Version;
import org.junit.jupiter.api.Test;

class JsonTest {

	@Test
	void testSerializeAnJsonObject() {
	
		Library origin = new Library("testLibrary", new Version(4, 3, 2, 2), ReleaseType.ALPHA, "www.unittestlibrary.com");
		String expected = "{\"id\":\"testLibrary\",\"version\":{\"major\":4,\"minor\":3,\"sub\":2,\"revision\":2},\"release\":\"ALPHA\",\"officialWebsite\":\"www.unittestlibrary.com\"}";
		String jsonFromLibrary;
		
		jsonFromLibrary = Json.toJson(origin);
		
		assertTrue(expected.equals(jsonFromLibrary));
	}
	
	
	@Test
	void testBuildObjectFromJson() {
	
		String origin = "{\"id\":\"testLibrary\",\"version\":{\"major\":4,\"minor\":3,\"sub\":2,\"revision\":2},\"release\":\"ALPHA\",\"officialWebsite\":\"www.unittestlibrary.com\"}";
		Library expected = new Library("testLibrary", new Version(4, 3, 2, 2), ReleaseType.ALPHA, "www.unittestlibrary.com");
		Library actual;
		
		actual = Json.fromJson(origin, Library.class);
		
		assertTrue(actual.equals(expected));
	}

}
