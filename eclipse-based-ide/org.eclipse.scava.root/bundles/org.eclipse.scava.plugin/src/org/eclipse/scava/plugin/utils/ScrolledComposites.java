package org.eclipse.scava.plugin.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Rectangle;

public class ScrolledComposites {
	public static final void updateOnlyVerticalScrollableComposite(ScrolledComposite scrolledComposite) {
		if (scrolledComposite.getContent() == null)
			return;

		Rectangle clientArea = scrolledComposite.getClientArea();
		scrolledComposite.setMinSize(scrolledComposite.getContent().computeSize(clientArea.width, SWT.DEFAULT));
		scrolledComposite.requestLayout();
	}
}
