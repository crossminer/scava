/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.projectsearch.search.tab.details;

import org.eclipse.scava.plugin.mvc.view.CompositeView;
import org.eclipse.scava.plugin.projectsearch.search.details.DetailsView;
import org.eclipse.scava.plugin.projectsearch.search.tab.ITab;
import org.eclipse.scava.plugin.ui.scrolledcomposite.ScrolledComposites;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.wb.swt.SWTResourceManager;

public class DetailsTabView extends CompositeView<IDetailsTabViewEventListener> implements ITab<IDetailsTabViewEventListener> {
	private ScrolledComposite scrolledComposite;
	
	private CTabItem tabReference;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public DetailsTabView() {
		super(SWT.NONE);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		FillLayout fillLayout = new FillLayout(SWT.HORIZONTAL);
		fillLayout.marginWidth = 5;
		fillLayout.marginHeight = 5;
		setLayout(fillLayout);
		
		scrolledComposite = new ScrolledComposite(this, SWT.BORDER | SWT.V_SCROLL);
		scrolledComposite.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				ScrolledComposites.updateOnlyVerticalScrollableComposite(scrolledComposite);
			}
		});
		scrolledComposite.setAlwaysShowScrollBars(true);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	public void setDetails(DetailsView view) {
		view.getComposite().setParent(scrolledComposite);
		scrolledComposite.setContent(view.getComposite());
		ScrolledComposites.updateOnlyVerticalScrollableComposite(scrolledComposite);
	}

	@Override
	public void setTabReference(CTabItem tab) {
		tabReference = tab;
		
		// this part is a bit hacky, but there is no other way to notify the view about
		// the dispose
		tab.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				eventManager.invoke(l -> l.requestViewClose());
			}
		});
	}
	
	@Override
	public void dispose() {
		if( tabReference != null ) {
			tabReference.dispose();
		}
		super.dispose();
	}
}
