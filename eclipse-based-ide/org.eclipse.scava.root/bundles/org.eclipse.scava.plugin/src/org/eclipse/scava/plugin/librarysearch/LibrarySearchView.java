package org.eclipse.scava.plugin.librarysearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.scava.plugin.librarysearch.list.ILibraryListView;
import org.eclipse.scava.plugin.librarysearch.tabs.finish.ILibrarySearchFinishView;
import org.eclipse.scava.plugin.librarysearch.tabs.recommendedlibs.ILibrarySearchRecommendedLibsView;
import org.eclipse.scava.plugin.librarysearch.tabs.results.ILibrarySearchResultView;
import org.eclipse.scava.plugin.mvc.implementation.CloseEventRaiser;
import org.eclipse.scava.plugin.mvc.implementation.jface.JFaceTitleAreaDialogView;
import org.eclipse.scava.plugin.mvc.implementation.swt.IHasComposite;
import org.eclipse.scava.plugin.utils.ScrolledComposites;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
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

public class LibrarySearchView extends JFaceTitleAreaDialogView implements ILibrarySearchView {
	private ScrolledComposite toBeInstalledScrolledComposite;
	private CTabFolder searchTabFolder;
	private Text textQueryString;
	private Composite toBeInstalledListComposite;

	private List<TabData> hiddenTabs;
	private Button okButton;
	private Button btnSearch;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public LibrarySearchView(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.SHELL_TRIM);
		setBlockOnOpen(false);

		hiddenTabs = new ArrayList<>();
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setMessage("Search for a library to use it in your project");
		setTitle("Library search");
		setTitleImage(ResourceManager.getPluginImage("org.eclipse.scava.plugin", "bin/logo-titleimage-titlearedialog.png"));
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		Composite composite = new Composite(container, SWT.NONE);
		composite.setLayout(new GridLayout(3, false));
		GridData gd_composite = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 2);
		gd_composite.heightHint = 37;
		composite.setLayoutData(gd_composite);

		Label lblSearch = new Label(composite, SWT.NONE);
		lblSearch.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSearch.setText("Search for a library:");

		textQueryString = new Text(composite, SWT.BORDER);

		textQueryString.setText("");

		textQueryString.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		btnSearch = new Button(composite, SWT.NONE);
		btnSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				onSearchButtonClicked();
			}
		});
		btnSearch.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		btnSearch.setText("Search");

		Label lblLibrariesToBe = new Label(container, SWT.NONE);
		lblLibrariesToBe.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblLibrariesToBe.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblLibrariesToBe.setText("Libraries to be installed:");

		toBeInstalledScrolledComposite = new ScrolledComposite(container, SWT.BORDER | SWT.V_SCROLL);
		toBeInstalledScrolledComposite.setAlwaysShowScrollBars(true);
		GridData gd_toBeInstalledScrolledComposite = new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 2);
		gd_toBeInstalledScrolledComposite.widthHint = 185;
		toBeInstalledScrolledComposite.setLayoutData(gd_toBeInstalledScrolledComposite);
		toBeInstalledScrolledComposite.setExpandHorizontal(true);
		toBeInstalledScrolledComposite.setExpandVertical(true);

		toBeInstalledListComposite = new Composite(toBeInstalledScrolledComposite, SWT.NONE);
		toBeInstalledListComposite.setBackground(SWTResourceManager.getColor(255, 255, 255));
		toBeInstalledListComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		toBeInstalledScrolledComposite.setContent(toBeInstalledListComposite);
		toBeInstalledScrolledComposite.setMinSize(toBeInstalledListComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		searchTabFolder = new CTabFolder(container, SWT.BORDER | SWT.FLAT);
		searchTabFolder.setMaximized(true);
		searchTabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		searchTabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));

		searchTabFolder.addCTabFolder2Listener(new CTabFolder2Adapter() {

			@Override
			public void close(CTabFolderEvent event) {
				onTabClosed(event);
			}

		});

		Shell shell = getShell();
		shell.getDisplay().asyncExec(() -> shell.setDefaultButton(btnSearch));

		return area;
	}

	private void onTabClosed(CTabFolderEvent event) {
		if (!hiddenTabs.isEmpty()) {
			hiddenTabs.forEach(tabData -> showTabWithContent(tabData.getLabel(), tabData.getControl()));
			hiddenTabs.clear();
		}

		CTabItem tabItem = (CTabItem) event.item;
		new CloseEventRaiser(tabItem.getControl()).raiseCloseEvent(getEventBus());

		okButton.setEnabled(true);
		btnSearch.setEnabled(true);
	}

	private void onSearchButtonClicked() {
		String queryString = textQueryString.getText();
		ILibrarySearchView.SearchRequestEvent event = new ILibrarySearchView.SearchRequestEvent(this, queryString);
		getEventBus().post(event);
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		okButton = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(816, 576);
	}

	@Override
	protected void configureShell(Shell newShell) {
		newShell.setToolTipText("");
		super.configureShell(newShell);
		newShell.setText("Library search");

		newShell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		newShell.setBackgroundMode(SWT.INHERIT_DEFAULT);
	}

	@Override
	public void showToBeInstalled(ILibraryListView view) {
		Composite composite = ((IHasComposite) view).getComposite();

		composite.setParent(toBeInstalledListComposite);
		ScrolledComposites.updateOnlyVerticalScrollableComposite(toBeInstalledScrolledComposite);
		toBeInstalledListComposite.requestLayout();
	}

	@Override
	public void showSearchResult(String label, ILibrarySearchResultView view) {
		Composite composite = ((IHasComposite) view).getComposite();

		showTabWithContent(label, composite);
	}

	@Override
	public void showSuggestedLibraries(String label, ILibrarySearchRecommendedLibsView view) {
		CTabItem finishTab = searchTabFolder.getSelection();
		new CloseEventRaiser(finishTab.getControl()).raiseCloseEvent(getEventBus());
		finishTab.dispose();

		hiddenTabs.forEach(tabData -> showTabWithContent(tabData.getLabel(), tabData.getControl()));
		hiddenTabs.clear();

		Composite composite = ((IHasComposite) view).getComposite();

		showTabWithContent(label, composite);

		okButton.setEnabled(true);
		btnSearch.setEnabled(true);
	}

	@Override
	protected void okPressed() {
		// super.okPressed();
		FinishRequestEvent event = new FinishRequestEvent(this);
		getEventBus().post(event);
	}

	@Override
	public void showFinish(ILibrarySearchFinishView view) {
		hiddenTabs = Arrays.stream(searchTabFolder.getItems()).map(tab -> new TabData(tab.getText(), tab.getControl())).collect(Collectors.toList());
		for (CTabItem tab : searchTabFolder.getItems()) {
			tab.dispose();
		}

		Composite composite = ((IHasComposite) view).getComposite();

		showTabWithContent("Finish", composite);

		okButton.setEnabled(false);
		btnSearch.setEnabled(false);
	}

	private void showTabWithContent(String label, Control control) {
		CTabItem resultTab = new CTabItem(searchTabFolder, SWT.CLOSE);
		resultTab.setShowClose(true);
		resultTab.setText(label);
		control.setParent(searchTabFolder);
		resultTab.setControl(control);

		searchTabFolder.setSelection(resultTab);
	}

	private class TabData {
		private final String label;
		private final Control control;

		public TabData(String label, Control control) {
			super();
			this.label = label;
			this.control = control;
		}

		public String getLabel() {
			return label;
		}

		public Control getControl() {
			return control;
		}

	}

}
