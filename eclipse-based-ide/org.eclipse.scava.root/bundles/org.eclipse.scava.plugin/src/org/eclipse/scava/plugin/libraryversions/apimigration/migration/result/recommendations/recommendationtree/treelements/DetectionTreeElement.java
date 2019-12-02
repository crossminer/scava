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

import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.location.RascalLocation;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.widgets.Display;

public class DetectionTreeElement extends TreeElement {
	private final RascalLocation oldLibraryLocation;
	private final RascalLocation newLibraryLocation;

	private static final Styler styler;

	static {
		styler = new Styler() {
			private final Font font = FontDescriptor.createFrom(new FontData()).setHeight(9).setStyle(SWT.ITALIC)
					.createFont(Display.getCurrent());

			@Override
			public void applyStyles(TextStyle textStyle) {
				textStyle.foreground = Display.getDefault().getSystemColor(SWT.COLOR_DARK_RED);
				textStyle.font = font;
			}

		};
	}

	public DetectionTreeElement(String oldLibraryLocation, String newLibraryLocation) {
		super();
		this.oldLibraryLocation = new RascalLocation(oldLibraryLocation);
		this.newLibraryLocation = new RascalLocation(newLibraryLocation);
	}

	public RascalLocation getNewLibraryLocation() {
		return newLibraryLocation;
	}

	public RascalLocation getOldLibraryLocation() {
		return oldLibraryLocation;
	}

	@Override
	public StyledString getStyledString() {
		StringBuilder sb = new StringBuilder();

		sb.append("Migration issue near ");

		if (oldLibraryLocation != null && !oldLibraryLocation.isEmpty()) {
			sb.append(oldLibraryLocation.getLocation());
			sb.append(" (old ");
			sb.append(oldLibraryLocation.getSchemeType());
			sb.append(")");

			if (newLibraryLocation != null && !newLibraryLocation.isEmpty()) {
				sb.append(" and ");
			}
		}

		if (newLibraryLocation != null && !newLibraryLocation.isEmpty()) {
			sb.append(newLibraryLocation.getLocation());
			sb.append(" (new ");
			sb.append(newLibraryLocation.getSchemeType());
			sb.append(")");
		}

		StyledString styledString = new StyledString(sb.toString(), styler);
		return styledString;
	}
}
