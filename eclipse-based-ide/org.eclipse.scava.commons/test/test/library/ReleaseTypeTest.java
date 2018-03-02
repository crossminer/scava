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

import org.eclipse.scava.commons.library.ReleaseType;
import org.junit.jupiter.api.Test;



class ReleaseTypeTest {

	@Test
	void testFindByStringForKnownTypes() {
		
		ReleaseType alhpaType = ReleaseType.ALPHA;
		ReleaseType betaType = ReleaseType.BETA;
		ReleaseType developtmentType = ReleaseType.DEVELOPMENT;
		ReleaseType stableType = ReleaseType.STABLE;
	
		assertTrue(alhpaType.equals(ReleaseType.findByString("alpha")));
		assertTrue(betaType.equals(ReleaseType.findByString("beta")));
		assertTrue(developtmentType.equals(ReleaseType.findByString("development")));
		assertTrue(stableType.equals(ReleaseType.findByString("stable")));
		
		assertTrue(alhpaType.equals(ReleaseType.findByString("ALPHA")));
		assertTrue(betaType.equals(ReleaseType.findByString("BETA")));
		assertTrue(developtmentType.equals(ReleaseType.findByString("DEVELOPMENT")));
		assertTrue(stableType.equals(ReleaseType.findByString("STABLE")));
		
	}
	
	@Test
	void testFindByStringForUnknownTypes() {
		
		ReleaseType unkownType = ReleaseType.UNKNOWN;
		
		assertTrue(unkownType.equals(ReleaseType.findByString("gamma")));
		assertTrue(unkownType.equals(ReleaseType.findByString("foo")));
		assertTrue(unkownType.equals(ReleaseType.findByString("bar")));
		assertTrue(unkownType.equals(ReleaseType.findByString("FOOBAR")));
		
	}

}
