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
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.widgets.Display;

public class DetectionTypeTreeElement extends TreeElement {
	private final String type;
	private static final Styler styler;

	static {
		styler = new Styler() {
			private final Font font = FontDescriptor.createFrom(new FontData()).setHeight(9).setStyle(SWT.BOLD)
					.createFont(Display.getCurrent());

			@Override
			public void applyStyles(TextStyle textStyle) {
				textStyle.foreground = Display.getDefault().getSystemColor(SWT.COLOR_DARK_GRAY);
				textStyle.font = font;
			}

		};
	}

	public String getType() {
		return type;
	}

	public DetectionTypeTreeElement(String type) {
		super();
		this.type = type;
	}

	@Override
	public StyledString getStyledString() {
		StyledString styledString = new StyledString(type, styler);
		return styledString;
	}

}
