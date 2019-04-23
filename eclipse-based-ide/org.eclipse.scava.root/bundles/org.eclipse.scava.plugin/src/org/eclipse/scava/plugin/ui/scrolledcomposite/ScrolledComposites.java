/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.ui.scrolledcomposite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class ScrolledComposites {
	public static final void updateOnlyVerticalScrollableComposite(ScrolledComposite scrolledComposite) {
		Control content = scrolledComposite.getContent();
		
		if (content == null)
			return;
		
		if( content instanceof Composite ) {
			((Composite)content).layout();
		}
		
		Rectangle clientArea = scrolledComposite.getClientArea();
		
		Point computeSize = content.computeSize(clientArea.width, SWT.DEFAULT, true);
		
		scrolledComposite.setMinSize(computeSize);
		scrolledComposite.layout();
		
		content.requestLayout();
	}
}
