/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.treelements;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.location.PathPart;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.location.PathPartLocation;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.widgets.Display;

public class PathPartTreeElement extends TreeElement {

	private final PathPart path;
	private static final Styler styler;

	static {
		styler = new Styler() {
			private final Font font = FontDescriptor.createFrom(new FontData()).setHeight(9).setStyle(SWT.BOLD)
					.createFont(Display.getCurrent());

			@Override
			public void applyStyles(TextStyle textStyle) {
				textStyle.font = font;
			}

		};
	}

	public PathPartTreeElement(PathPart path) {
		super();
		this.path = path;
	}

	public PathPart getPathPart() {
		return path;
	}

	public PathPartLocation getLocation() {
		List<PathPart> pathParts = streamFromRoot().map(PathPartTreeElement.class::cast)
				.map(PathPartTreeElement::getPathPart).collect(Collectors.toList());
		return new PathPartLocation(pathParts);
	}

	@Override
	public StyledString getStyledString() {
		StyledString styledString = new StyledString(path.toLocationString());
		if (hasDetection()) {
			styledString.setStyle(0, styledString.length(), styler);
		}
		return styledString;
	}
}
