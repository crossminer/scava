/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.eclipse.propertytester;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.jface.text.ITextSelection;

public class TextSelectionTester extends PropertyTester {

	private static final String NonEmptyProperty = "nonEmpty";

	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (NonEmptyProperty.equals(property)) {
			ITextSelection iTextSelection = (ITextSelection) receiver;
			boolean nonEmpty = iTextSelection.getLength() != 0;
			return nonEmpty == (boolean)expectedValue;
		}
		
		return false;
	}

}
