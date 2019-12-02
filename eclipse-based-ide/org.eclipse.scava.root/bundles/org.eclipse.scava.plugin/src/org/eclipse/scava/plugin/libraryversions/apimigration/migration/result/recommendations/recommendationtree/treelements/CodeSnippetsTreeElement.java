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

public class CodeSnippetsTreeElement extends TreeElement {

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

	public CodeSnippetsTreeElement() {
		super();

		// ensure that this kind of elements are at the bottom of the list
		setOrderBias(1000);
	}

	@Override
	public StyledString getStyledString() {
		StyledString styledString = new StyledString("Recommended code snippet" + (getChildren().length > 1 ? "s" : ""),
				styler);
		return styledString;
	}
}
