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

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.scava.commons.library.Library;
import org.eclipse.scava.commons.library.ReleaseType;
import org.eclipse.scava.commons.library.Version;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import junit.framework.Assert;

class LibraryTest {

	
	
	
	@Test
	void testLibraryEquality() {
		
		Library origin = new Library("testLibrary", new Version(4, 3, 2, 2), ReleaseType.ALPHA, "www.unittestlibrary.com");
		Library expected = new Library("testLibrary", new Version(4, 3, 2, 2), ReleaseType.ALPHA, "www.unittestlibrary.com");
		Library wrong = new Library("wrongLibrary", new Version(6, 5, 4, 3), ReleaseType.BETA, "www.wrongtestlibrary.com");
		
		assertTrue(origin.equals(origin),"Two library must equals");
		assertTrue(expected.equals(origin));
		
		
		assertFalse(origin.equals(wrong), "Not equals");
		assertFalse(wrong.equals(origin), "Not equals");
	}
	
	
	@Test
	void testLibraryInequality() {
		
		Library origin = new Library("testLibrary", new Version(4, 3, 2, 2), ReleaseType.ALPHA, "www.unittestlibrary.com");
		Library actual = new Library("wrongLibrary", new Version(6, 5, 4, 3), ReleaseType.BETA, "www.wrongtestlibrary.com");
		
		
		assertFalse(origin.equals(actual), "Not equals");
		assertFalse(actual.equals(origin), "Not equals");
	}
	
	
	@Test
	void testLibraryToString() {
		Library origin = new Library("testLibrary", new Version(4, 3, 2, 2), ReleaseType.ALPHA, "www.unittestlibrary.com");
		String expectedToSring = "Library [id=testLibrary, version=4.3.2.2, release=ALPHA, officialWebsite=www.unittestlibrary.com]";
		assertTrue(origin.toString().equals(expectedToSring));
	}

}
