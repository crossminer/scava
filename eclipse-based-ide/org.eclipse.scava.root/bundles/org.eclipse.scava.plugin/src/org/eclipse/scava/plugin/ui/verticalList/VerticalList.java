/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.ui.verticalList;

import org.eclipse.scava.plugin.ui.scrolledcomposite.ScrolledComposites;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public class VerticalList extends Composite {
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public VerticalList(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void add(Composite composite) {
		composite.setParent(this);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		composite.addDisposeListener(new DisposeListener() {
			
			@Override
			public void widgetDisposed(DisposeEvent e) {
				if( composite.getParent() == VerticalList.this ) {
					remove(composite);
				}
			}
		});
		
		notifyParentScrolledComposite();
	}
	
	public void remove(Composite composite) {
		composite.setParent(new Shell());

		notifyParentScrolledComposite();
	}
	
	private void notifyParentScrolledComposite() {
		layout();
		Composite parent = getParent();
		while( parent != null ) {
			if (parent instanceof ScrolledComposite) {
				ScrolledComposite scrolledComposite = (ScrolledComposite) parent;
				ScrolledComposites.updateOnlyVerticalScrollableComposite(scrolledComposite);
				break;
			}else {
				parent = parent.getParent();
			}
		}
	}
}
