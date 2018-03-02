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

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.scava.commons.library.Library;
import org.eclipse.scava.plugin.ui.libraryupdate.AlternativeLibrary;
import org.eclipse.scava.plugin.ui.libraryupdate.ProjectLibrary;
import org.eclipse.swt.graphics.Image;

public class LibraryNameLabelProvider extends LabelProvider implements IStyledLabelProvider{
	
	StyledString sstring;
	private ImageDescriptor directoryImage;
    private ResourceManager resourceManager;
    
    public LibraryNameLabelProvider(ImageDescriptor directoryImage) {
        this.directoryImage = directoryImage;
    }
	
	
    @Override
    public String getText(Object element) {
        if (element instanceof Library) {
            return ((Library) element).getId();
        }
        return (String) element;
    }

	@Override
	public StyledString getStyledText(Object element) {
		if (element instanceof AlternativeLibrary) {
            return sstring= new StyledString( ((AlternativeLibrary) element).getLibrary().getId());
        }
		if (element instanceof ProjectLibrary) {
            return sstring= new StyledString( ((ProjectLibrary) element).getLibrary().getId());
        }
        return sstring= new StyledString((String) element);
	}
	
	@Override
    public Image getImage(Object element) {
        if (element instanceof ProjectLibrary) {
                return getResourceManager().createImage(directoryImage);
        }

        return super.getImage(element);
    }
	
	protected ResourceManager getResourceManager() {
        if (resourceManager == null) {
            resourceManager = new LocalResourceManager(JFaceResources.getResources());
        }
        return resourceManager;
	}
}