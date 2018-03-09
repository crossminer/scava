/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.ui.libraryupdate.provider;

import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.scava.commons.library.Library;
import org.eclipse.scava.plugin.ui.libraryupdate.AlternativeLibrary;
import org.eclipse.scava.plugin.ui.libraryupdate.ProjectLibrary;

public class LibraryVersionLabelProvider extends LabelProvider implements IStyledLabelProvider{
	
	StyledString sstring;
	
	 @Override
	    public String getText(Object element) {
	        if (element instanceof Library) {
	            return ((Library) element).getVersion().toString();
	        }
	        return (String) element;
	    }

	@Override
	public StyledString getStyledText(Object element) {
		if (element instanceof AlternativeLibrary) {
            return sstring= new StyledString( ((AlternativeLibrary) element).getLibrary().getVersion().toString());
        }
		if (element instanceof ProjectLibrary) {
            return sstring = new StyledString(((ProjectLibrary) element).getLibrary().getVersion().toString());           		
        }
        return sstring = new StyledString((String) element);
	}
	
}
