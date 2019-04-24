/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.projectsearch.search;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.scava.plugin.mvc.view.TitleAreaDialogView;
import org.eclipse.scava.plugin.projectsearch.search.selectedResult.SelectedResultView;
import org.eclipse.scava.plugin.projectsearch.search.tab.ITab;
import org.eclipse.scava.plugin.ui.verticalList.VerticalList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

public class SearchView extends TitleAreaDialogView<ISearchViewEventListener> {
	private Text searchText;
	private ScrolledComposite selectedScrolledComposite;
	private CTabFolder searchTabs;
	private VerticalList selectedList;
	private Button btnInstall;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public SearchView(Shell parentShell) {
		super(parentShell);
		setHelpAvailable(false);
		setShellStyle(SWT.SHELL_TRIM | SWT.APPLICATION_MODAL);
		setBlockOnOpen(false);
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setTitleImage(
				ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/logo-titleimage-titlearedialog.png"));
		setMessage("Search for projects and select them for install");
		setTitle("Project search");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		Composite searchComposite = new Composite(container, SWT.NONE);
		searchComposite.setLayout(new GridLayout(3, false));
		searchComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblSearchForA = new Label(searchComposite, SWT.NONE);
		lblSearchForA.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSearchForA.setText("Search for a project:");

		searchText = new Text(searchComposite, SWT.BORDER);
		searchText.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				search();
			}
		});
		searchText.setTextLimit(1024);
		searchText.setText("");
		searchText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button btnSearch = new Button(searchComposite, SWT.NONE);
		btnSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				search();
			}
		});
		btnSearch.setText("Search");

		Composite selectedComposite = new Composite(container, SWT.NONE);
		selectedComposite.setLayout(new GridLayout(1, false));
		GridData gd_selectedComposite = new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 2);
		gd_selectedComposite.widthHint = 166;
		selectedComposite.setLayoutData(gd_selectedComposite);

		Label lblSelectedProjects = new Label(selectedComposite, SWT.NONE);
		lblSelectedProjects.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblSelectedProjects.setText("Selected projects");

		selectedScrolledComposite = new ScrolledComposite(selectedComposite, SWT.BORDER | SWT.V_SCROLL);
		selectedScrolledComposite.setAlwaysShowScrollBars(true);
		selectedScrolledComposite.setBackgroundMode(SWT.INHERIT_FORCE);
		selectedScrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		selectedScrolledComposite.setExpandHorizontal(true);
		selectedScrolledComposite.setExpandVertical(true);

		selectedList = new VerticalList(selectedScrolledComposite, SWT.NONE);

		selectedScrolledComposite.setContent(selectedList);
		selectedScrolledComposite.setMinSize(selectedList.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		Composite resultsComposite = new Composite(container, SWT.NONE);
		resultsComposite.setLayout(new GridLayout(1, false));
		resultsComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		searchTabs = new CTabFolder(resultsComposite, SWT.BORDER | SWT.CLOSE | SWT.FLAT);
		searchTabs.setMaximized(true);
		searchTabs.setMRUVisible(true);
		searchTabs.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		searchTabs.setHighlightEnabled(false);
		searchTabs.setSelectionBackground(
				Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));

		Shell shell = getShell();
		shell.getDisplay().asyncExec(() -> shell.setDefaultButton(btnSearch));

		return area;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		btnInstall = createButton(parent, IDialogConstants.OK_ID, "Install", true);
		btnInstall.setEnabled(false);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setToolTipText("");
		newShell.setImage(ResourceManager.getPluginImage("org.eclipse.scava.plugin",
				"icons/features/crossminer_project_search_16x16.png"));
		newShell.setText("Project search");

		newShell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		newShell.setBackgroundMode(SWT.INHERIT_FORCE);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(799, 551);
	}

	public void addToSelectedList(SelectedResultView view) {
		selectedList.add(view.getComposite());
	}

	public void addSearchTab(String title, ITab<?> view) {
		CTabItem tab = new CTabItem(searchTabs, SWT.NONE);
		tab.setText(title);

		Composite composite = view.getComposite();
		composite.setParent(searchTabs);

		tab.setControl(composite);

		searchTabs.setSelection(tab);

		// this part is a bit hacky, but there is no other way to notify the view about
		// the dispose
		tab.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				view.closed();
			}
		});
	}

	@Override
	protected void okPressed() {
		eventManager.invoke(l -> l.onInstall());
	}

	public void setEnableInstall(boolean value) {
		btnInstall.setEnabled(value);
	}

	private void search() {
		eventManager.invoke(l -> l.onSearch(searchText.getText()));
		searchText.setText("");
	}
	
	public void setVisible(boolean visible) {
		getShell().setVisible(visible);
	}
}
