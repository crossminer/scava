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
package test.library;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.scava.commons.library.Version;
import org.junit.jupiter.api.Test;

class VersionTest {

	@Test
	void testVersionFromStringConstructor() {

		Version versionFromString = new Version("3.4.5.12");
		Version versionFromIntegers = new Version(3, 4, 5, 12);
		
		assertTrue(versionFromIntegers.equals(versionFromString));
		
	}
	
	
	@Test
	void testVersionEquality() {
		
		Version origin = new Version(3, 4, 5, 12);
		Version expected = new Version(3, 4, 5, 12);
		
		assertTrue(origin.equals(expected));
		assertTrue(expected.equals(origin));

	}
	
	@Test
	void testVersionInequality() {
		
		Version origin = new Version(3, 4, 5, 12);
		Version actual = new Version(5, 6, 7, 8);
		
		assertFalse(actual.equals(origin));
		assertFalse(origin.equals(actual));
		
	}

}
