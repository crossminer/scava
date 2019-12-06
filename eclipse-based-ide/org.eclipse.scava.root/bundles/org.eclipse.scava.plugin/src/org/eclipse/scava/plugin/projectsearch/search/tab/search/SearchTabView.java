/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.projectsearch.search.tab.search;

import java.util.List;

import org.eclipse.scava.plugin.mvc.view.CompositeView;
import org.eclipse.scava.plugin.projectsearch.search.details.DetailsView;
import org.eclipse.scava.plugin.projectsearch.search.searchresult.SearchResultView;
import org.eclipse.scava.plugin.projectsearch.search.tab.ITab;
import org.eclipse.scava.plugin.ui.scrolledcomposite.ScrolledComposites;
import org.eclipse.scava.plugin.ui.verticalList.VerticalList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class SearchTabView extends CompositeView<ISearchTabViewEventListener>
		implements ITab<ISearchTabViewEventListener> {
	private VerticalList resultList;
	private ScrolledComposite resultsScrolledComposite;
	private Label lblDescription;
	private ScrolledComposite detailsScrolledComposite;
	private Label lblNoProjectsWere;
	private Composite detailsHelp;
	private Button btnShowMore;
	private Composite resultsContentComposite;
	
	private CTabItem tabReference;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public SearchTabView() {
		super(SWT.NONE);
		setBackgroundMode(SWT.INHERIT_FORCE);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setLayout(new GridLayout(2, false));

		Composite resultsComposite = new Composite(this, SWT.NONE);
		resultsComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		GridData gd_resultsComposite = new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 1);
		gd_resultsComposite.widthHint = 169;
		resultsComposite.setLayoutData(gd_resultsComposite);

		resultsScrolledComposite = new ScrolledComposite(resultsComposite, SWT.BORDER | SWT.V_SCROLL);
		resultsScrolledComposite.setExpandHorizontal(true);
		resultsScrolledComposite.setExpandVertical(true);
		resultsScrolledComposite.setAlwaysShowScrollBars(true);

		resultsContentComposite = new Composite(resultsScrolledComposite, SWT.NONE);
		resultsContentComposite.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				ScrolledComposites.updateOnlyVerticalScrollableComposite(resultsScrolledComposite);
			}
		});
		resultsContentComposite.setLayout(new GridLayout(1, false));

		lblDescription = new Label(resultsContentComposite, SWT.WRAP);
		lblDescription.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		lblDescription.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
		GridData gd_lblDescription = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_lblDescription.widthHint = 158;
		lblDescription.setLayoutData(gd_lblDescription);
		lblDescription.setText("<short description of the search>");
		
				resultList = new VerticalList(resultsContentComposite, SWT.NONE);
				resultList.setBackgroundMode(SWT.INHERIT_FORCE);
				resultList.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblNoProjectsWere = new Label(resultsContentComposite, SWT.NONE);
		GridData gd_lblNoProjectsWere = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblNoProjectsWere.exclude = true;
		lblNoProjectsWere.setLayoutData(gd_lblNoProjectsWere);
		lblNoProjectsWere.setText("No projects were found");

		resultsScrolledComposite.setContent(resultsContentComposite);
		resultsScrolledComposite.setMinSize(resultsContentComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		detailsScrolledComposite = new ScrolledComposite(this, SWT.BORDER | SWT.V_SCROLL);
		detailsScrolledComposite.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				ScrolledComposites.updateOnlyVerticalScrollableComposite(detailsScrolledComposite);
			}
		});
		detailsScrolledComposite.setAlwaysShowScrollBars(true);
		detailsScrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		detailsScrolledComposite.setExpandHorizontal(true);
		detailsScrolledComposite.setExpandVertical(true);

		detailsHelp = new Composite(detailsScrolledComposite, SWT.NONE);
		detailsHelp.setLayout(new GridLayout(1, false));

		Label lblSelectAProject = new Label(detailsHelp, SWT.NONE);
		lblSelectAProject.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		lblSelectAProject.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		lblSelectAProject.setAlignment(SWT.CENTER);
		lblSelectAProject.setText("Select a project to see the details of it");
		detailsScrolledComposite.setContent(detailsHelp);
		detailsScrolledComposite.setMinSize(detailsHelp.computeSize(SWT.DEFAULT, SWT.DEFAULT));

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void setDescription(String value) {
		lblDescription.setText(value);
	}

	public void showResults(List<SearchResultView> results) {
		if( results.isEmpty() ) {
			GridData data = (GridData) lblNoProjectsWere.getLayoutData();
			data.exclude = false;
			resultsContentComposite.requestLayout();
		}else {
			lblNoProjectsWere.setText("No more projects found");
		}
		
		for (SearchResultView searchResultView : results) {
			resultList.add(searchResultView.getComposite());
		}
	}

	public void setDetails(DetailsView view) {
		if (!detailsHelp.isDisposed()) {
			detailsHelp.dispose();
		}

		view.getComposite().setParent(detailsScrolledComposite);
		detailsScrolledComposite.setContent(view.getComposite());
		ScrolledComposites.updateOnlyVerticalScrollableComposite(detailsScrolledComposite);
	}

	public void showShowMoreButton() {
		if (btnShowMore != null && !btnShowMore.isDisposed()) {
			return;
		}
		
		btnShowMore = new Button(resultsContentComposite, SWT.NONE);
		btnShowMore.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				eventManager.invoke(l -> l.onShowMore());
			}
		});
		btnShowMore.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnShowMore.setText("Show more...");
	}

	public void hideShowMoreButton() {
		if (btnShowMore != null && !btnShowMore.isDisposed()) {
			btnShowMore.dispose();
		}
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
