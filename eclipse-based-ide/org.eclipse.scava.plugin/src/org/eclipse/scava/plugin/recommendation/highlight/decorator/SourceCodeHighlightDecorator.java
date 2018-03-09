/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.recommendation.highlight.decorator;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.NamedMember;
import org.eclipse.jdt.internal.core.SourceType;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.scava.plugin.recommendation.highlight.SourceCodeHighlighterView;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;

@SuppressWarnings("restriction")
public class SourceCodeHighlightDecorator extends LabelProvider implements ILightweightLabelDecorator {
	
	@Override
	public void decorate(Object element, IDecoration decoration) {
		if (element instanceof NamedMember && !(element instanceof SourceType)) {
			NamedMember namedMember = (NamedMember) element;
			decorateNamedMember(decoration, namedMember);
		} else if (element instanceof IResource) {
			IResource resource = (IResource) element;
			decorateResource(decoration, resource);
		}
	}
	
	private void decorateNamedMember(IDecoration decoration, NamedMember namedMember) {
		try {
			if( !namedMember.exists() )
				return;
			
			IResource resource = namedMember.getResource();
			
			if (resource == null )
				return;
			
			IMarker[] markers = getMarkersOfResource(resource, IResource.DEPTH_ZERO);
						
			ISourceRange range = namedMember.getSourceRange();
			
			int numberOfMarkersInRange = 0;
			
			for (IMarker marker : markers) {
				if (isMarkerInRange(range, marker)) {
					numberOfMarkersInRange++;
				}
			}
			
			if (numberOfMarkersInRange > 0) {
				applyDecorationOnContainer(decoration, numberOfMarkersInRange);
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
	}
	
	private boolean isMarkerInRange(ISourceRange range, IMarker marker) {
		return marker.getAttribute(IMarker.CHAR_START, Integer.MAX_VALUE) <= range.getOffset() + range.getLength()
				&& marker.getAttribute(IMarker.CHAR_END, Integer.MIN_VALUE) >= range.getOffset();
	}
	
	private void decorateResource(IDecoration decoration, IResource resource) {
		IMarker[] markers = getMarkersOfResource(resource, IResource.DEPTH_INFINITE);
		
		if (markers.length == 0)
			return;
		
		if (isMarkedResource(resource)) {
			applyDecorationOnContainer(decoration, markers.length);
		} else {
			applyDecorationOnParent(decoration, markers.length);
		}
	}
	
	private boolean isMarkedResource(IResource resource) {
		return getMarkersOfResource(resource, IResource.DEPTH_ZERO).length > 0;
	}
	
	private IMarker[] getMarkersOfResource(IResource resource, int depth) {
		try {
			return resource.findMarkers(SourceCodeHighlighterView.MARKER_ID, true, depth);
		} catch (CoreException e) {
			return new IMarker[0];
		}
	}
	
	private void applyDecorationOnParent(IDecoration decoration, int founds) {
		decoration.setForegroundColor(new Color(null, 230, 0, 230));
		decoration.setFont(new Font(null, new FontData("Arial", 9, SWT.NORMAL)));
		decoration.addSuffix(" [SCAVA: " + founds + " recommendations" + pluralSign(founds) + "]");
		setIcon(decoration);
	}
	
	private void applyDecorationOnContainer(IDecoration decoration, int founds) {
		decoration.setForegroundColor(new Color(null, 255, 0, 255));
		decoration.setFont(new Font(null, new FontData("Arial", 9, SWT.BOLD)));
		decoration.addSuffix(" [SCAVA: " + founds + " recommendation" + pluralSign(founds) + "]");
		setIcon(decoration);
	}
	
	public void setIcon(IDecoration decoration) {
		decoration.addOverlay(ImageDescriptor.createFromFile(getClass(), "/icons/cm.gif"));
	}
	
	public String pluralSign(int number) {
		return number > 1 ? "s" : "";
	}
}
