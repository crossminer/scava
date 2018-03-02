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

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.scava.plugin.ui.libraryupdate.AlternativeLibrary;
import org.eclipse.scava.plugin.ui.libraryupdate.ProjectLibrary;

public class LibraryTreeContentProvider implements ITreeContentProvider {

    private static final Object[] EMPTY_ARRAY = new Object[0];

    @SuppressWarnings("unchecked")
	@Override
    public Object[] getElements(Object inputElement) {
        if (inputElement instanceof List) {
            return ((List<ProjectLibrary>) inputElement).toArray();
        } else {
            return EMPTY_ARRAY;
        }
    }

    @Override
    public boolean hasChildren(Object element) {
        if (element instanceof ProjectLibrary) {
            return true;
        }
        return false;
    }

    @Override
    public Object[] getChildren(Object parentElement) {
        if (parentElement instanceof ProjectLibrary) {
            return ((ProjectLibrary) parentElement).getAlternativeLibraries().toArray();
        }
        return EMPTY_ARRAY;
    }

    @Override
    public void dispose() {
    }

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

    @Override
    public Object getParent(Object element) {
    	
    	if (element instanceof ProjectLibrary) {
			return ((ProjectLibrary) element);
		}
    	
    	if (element instanceof AlternativeLibrary) {
			return ((AlternativeLibrary) element).getOriginalLibrary();
		}
        return null;
    }

}