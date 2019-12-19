/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.librarysuggestion;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.scava.plugin.librarysuggestion.library.LibraryView;
import org.eclipse.scava.plugin.mvc.view.TitleAreaDialogView;
import org.eclipse.scava.plugin.ui.scrolledcomposite.ScrolledComposites;
import org.eclipse.scava.plugin.ui.verticalList.VerticalList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

public class LibrarySuggestionView extends TitleAreaDialogView<ILibrarySuggestionViewEventListener> {
	private ScrolledComposite usedScrolledComposite;
	private ScrolledComposite suggestionsScrolledComposite;
	private Label lblSelectTheLibraries;
	private Label lblTheShownLibraries;
	private Composite composite;
	private VerticalList basedOnVerticalList;
	private VerticalList usedVerticalList;
	private Composite composite_1;
	private Label lblSelectedLibrariesTo;
	private Label label_2;
	private VerticalList pickedVerticalList;
	private Label lblSuggestedLibraries;
	private Label label_4;
	private VerticalList suggestedVerticalList;
	private Label lblLoadingResults;
	private CLabel lblUseNone;
	private CLabel lblUseAll;
	private CLabel lblInstallNone;
	private CLabel lblInstallAll;
	private Label lblCouldNotLoad;
	private Button btnTryAgain;
	private Button btnInstall;
	private Label lblSearchBasedOn;
	private Label lblUsedInYour;
	private Button btnCancel;
	private Label lblNoLibrariesWere;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public LibrarySuggestionView(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.SHELL_TRIM | SWT.APPLICATION_MODAL);
		setHelpAvailable(false);
		setBlockOnOpen(false);
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setMessage("Find new libraries that could be useful for your project");
		setTitleImage(
				ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/logo-titleimage-titlearedialog.png"));
		setTitle("Library suggestion");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setBackgroundMode(SWT.INHERIT_FORCE);
		container.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		Group grpCurrentlyUsed = new Group(container, SWT.NONE);
		grpCurrentlyUsed.setLayout(new GridLayout(1, false));
		grpCurrentlyUsed.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpCurrentlyUsed.setText("Currently used");

		lblSelectTheLibraries = new Label(grpCurrentlyUsed, SWT.WRAP);
		GridData gd_lblSelectTheLibraries = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_lblSelectTheLibraries.widthHint = 100;
		lblSelectTheLibraries.setLayoutData(gd_lblSelectTheLibraries);
		lblSelectTheLibraries
				.setText("Select the libraries that you want to be used as the base of the search for suggestions.");

		usedScrolledComposite = new ScrolledComposite(grpCurrentlyUsed, SWT.BORDER | SWT.V_SCROLL);
		usedScrolledComposite.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				ScrolledComposites.updateOnlyVerticalScrollableComposite(usedScrolledComposite);
			}
		});
		usedScrolledComposite.setAlwaysShowScrollBars(true);
		GridData gd_usedScrolledComposite = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_usedScrolledComposite.widthHint = 100;
		usedScrolledComposite.setLayoutData(gd_usedScrolledComposite);
		usedScrolledComposite.setExpandHorizontal(true);
		usedScrolledComposite.setExpandVertical(true);

		composite = new Composite(usedScrolledComposite, SWT.NONE);
		GridLayout gl_composite = new GridLayout(2, false);
		gl_composite.verticalSpacing = 2;
		composite.setLayout(gl_composite);

		lblSearchBasedOn = new Label(composite, SWT.WRAP);
		lblSearchBasedOn.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblSearchBasedOn.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.BOLD));
		lblSearchBasedOn.setText("Libraries the search will base on");

		lblUseNone = new CLabel(composite, SWT.NONE);
		lblUseNone.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblUseNone.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				eventManager.invoke(l -> l.onUseNoneForSearch());
			}
		});
		lblUseNone.setCursor(SWTResourceManager.getCursor(SWT.CURSOR_HAND));
		lblUseNone.setImage(
				ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/control/remove_all_01.png"));
		lblUseNone.setText("Remove all");

		Label label = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("New Label");
		label.setBounds(0, 0, 55, 15);

		basedOnVerticalList = new VerticalList(composite, SWT.NONE);
		basedOnVerticalList.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		basedOnVerticalList.setBounds(0, 0, 10, 10);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		lblUsedInYour = new Label(composite, SWT.WRAP);
		lblUsedInYour.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblUsedInYour.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.BOLD));
		lblUsedInYour.setText("Libraries used in your project");

		lblUseAll = new CLabel(composite, SWT.NONE);
		lblUseAll.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblUseAll.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				eventManager.invoke(l -> l.onUseAllForSearch());
			}
		});
		lblUseAll.setCursor(SWTResourceManager.getCursor(SWT.CURSOR_HAND));
		lblUseAll.setImage(
				ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/control/select_all_01.png"));
		lblUseAll.setText("Select all");

		Label lblNewLabel = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblNewLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		lblNewLabel.setText("New Label");

		usedVerticalList = new VerticalList(composite, SWT.NONE);
		usedVerticalList.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		usedScrolledComposite.setContent(composite);
		usedScrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		Group grpSuggestions = new Group(container, SWT.NONE);
		grpSuggestions.setLayout(new GridLayout(1, false));
		grpSuggestions.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpSuggestions.setText("Suggestions");

		lblTheShownLibraries = new Label(grpSuggestions, SWT.WRAP);
		GridData gd_lblTheShownLibraries = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_lblTheShownLibraries.widthHint = 100;
		lblTheShownLibraries.setLayoutData(gd_lblTheShownLibraries);
		lblTheShownLibraries.setText(
				"The shown libraries may be useful in your project because they are often used by other developers together with the given ones. Choose which one you want to install into your project.");

		suggestionsScrolledComposite = new ScrolledComposite(grpSuggestions, SWT.BORDER | SWT.V_SCROLL);
		suggestionsScrolledComposite.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				ScrolledComposites.updateOnlyVerticalScrollableComposite(suggestionsScrolledComposite);
			}
		});
		suggestionsScrolledComposite.setAlwaysShowScrollBars(true);
		GridData gd_suggestionsScrolledComposite = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_suggestionsScrolledComposite.widthHint = 100;
		suggestionsScrolledComposite.setLayoutData(gd_suggestionsScrolledComposite);
		suggestionsScrolledComposite.setExpandHorizontal(true);
		suggestionsScrolledComposite.setExpandVertical(true);

		composite_1 = new Composite(suggestionsScrolledComposite, SWT.NONE);
		GridLayout gl_composite_1 = new GridLayout(2, false);
		gl_composite_1.verticalSpacing = 2;
		composite_1.setLayout(gl_composite_1);

		lblSelectedLibrariesTo = new Label(composite_1, SWT.WRAP);
		lblSelectedLibrariesTo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblSelectedLibrariesTo.setText("Selected libraries to be installed");
		lblSelectedLibrariesTo.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.BOLD));

		lblInstallNone = new CLabel(composite_1, SWT.NONE);
		lblInstallNone.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblInstallNone.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				eventManager.invoke(l -> l.onPickNoneToInstall());
			}
		});
		lblInstallNone.setCursor(SWTResourceManager.getCursor(SWT.CURSOR_HAND));
		lblInstallNone.setImage(
				ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/control/remove_all_02.png"));
		lblInstallNone.setText("Remove all");

		label_2 = new Label(composite_1, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label_2.setText("New Label");

		pickedVerticalList = new VerticalList(composite_1, SWT.NONE);
		pickedVerticalList.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		lblSuggestedLibraries = new Label(composite_1, SWT.WRAP);
		lblSuggestedLibraries.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblSuggestedLibraries.setText("Suggested libraries");
		lblSuggestedLibraries.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.BOLD));

		lblInstallAll = new CLabel(composite_1, SWT.NONE);
		lblInstallAll.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblInstallAll.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				eventManager.invoke(l -> l.onPickAllToInstall());
			}
		});
		lblInstallAll.setCursor(SWTResourceManager.getCursor(SWT.CURSOR_HAND));
		lblInstallAll.setImage(
				ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/control/select_all_02.png"));
		lblInstallAll.setText("Select all");

		label_4 = new Label(composite_1, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label_4.setText("New Label");

		suggestedVerticalList = new VerticalList(composite_1, SWT.NONE);
		suggestedVerticalList.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		lblNoLibrariesWere = new Label(composite_1, SWT.NONE);
		lblNoLibrariesWere.setAlignment(SWT.CENTER);
		lblNoLibrariesWere.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		lblNoLibrariesWere.setText("No libraries were found");

		lblLoadingResults = new Label(composite_1, SWT.NONE);
		lblLoadingResults.setEnabled(false);
		lblLoadingResults.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
		lblLoadingResults.setAlignment(SWT.CENTER);
		lblLoadingResults.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		lblLoadingResults.setText("Loading results...");

		lblCouldNotLoad = new Label(composite_1, SWT.NONE);
		lblCouldNotLoad.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		lblCouldNotLoad.setAlignment(SWT.CENTER);
		lblCouldNotLoad.setText("Could not load the results");

		btnTryAgain = new Button(composite_1, SWT.NONE);
		btnTryAgain.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				eventManager.invoke(l -> l.onTryAgainLoadSugestions());
			}
		});
		btnTryAgain.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));
		btnTryAgain.setText("Try again");
		suggestionsScrolledComposite.setContent(composite_1);
		suggestionsScrolledComposite.setMinSize(composite_1.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		return area;
	}

	@Override
	public void init() {
		super.init();

		lblLoadingResults.setVisible(false);
		lblCouldNotLoad.setVisible(false);
		btnTryAgain.setVisible(false);
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		btnInstall = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, false);
		btnInstall.setText("Install");
		btnCancel = createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, true);

		btnCancel.setFocus();
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setToolTipText("");
		newShell.setImage(ResourceManager.getPluginImage("org.eclipse.scava.plugin",
				"icons/features/crossminer_library_search_16x16.png"));
		newShell.setText("Library search");

		newShell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		newShell.setBackgroundMode(SWT.INHERIT_FORCE);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(752, 539);
	}

	public void addBasedOnLibrary(LibraryView view) {
		basedOnVerticalList.add(view.getComposite());
		ScrolledComposites.updateOnlyVerticalScrollableComposite(usedScrolledComposite);
	}

	public void addUsedLibrary(LibraryView view) {
		usedVerticalList.add(view.getComposite());
		ScrolledComposites.updateOnlyVerticalScrollableComposite(usedScrolledComposite);
	}

	public void addSuggestedLibrary(LibraryView view) {
		lblLoadingResults.setVisible(false);
		lblCouldNotLoad.setVisible(false);
		btnTryAgain.setVisible(false);

		suggestedVerticalList.add(view.getComposite());
		ScrolledComposites.updateOnlyVerticalScrollableComposite(suggestionsScrolledComposite);

	}

	public void addPickedLibrary(LibraryView view) {
		pickedVerticalList.add(view.getComposite());
		ScrolledComposites.updateOnlyVerticalScrollableComposite(suggestionsScrolledComposite);
	}

	public void showLoadingResults() {
		lblLoadingResults.setVisible(true);
		lblCouldNotLoad.setVisible(false);
		btnTryAgain.setVisible(false);
		lblNoLibrariesWere.setVisible(false);
	}

	public void showTryAgain() {
		lblLoadingResults.setVisible(false);
		lblCouldNotLoad.setVisible(true);
		btnTryAgain.setVisible(true);
		lblNoLibrariesWere.setVisible(false);
	}

	public void showNoResults() {
		lblLoadingResults.setVisible(false);
		lblCouldNotLoad.setVisible(false);
		btnTryAgain.setVisible(false);
		lblNoLibrariesWere.setVisible(true);
	}

	@Override
	protected void okPressed() {
		if (MessageDialog.openConfirm(getShell(), "Confirmation",
				"All custom informations from the project's pom.xml file will be erased.\nAre you sure you want to continue?")) {
			eventManager.invoke(l -> l.onInstall());
		}
	}

	public void setEnableInstall(boolean enabled) {
		btnInstall.setEnabled(enabled);
	}

	public void setNumberOfLibsUsedInProject(int number) {
		lblUsedInYour.setText(setCounterOnString(lblUsedInYour.getText(), number));
	}

	public void setNumberOfLibsSearchBasedOn(int number) {
		lblSearchBasedOn.setText(setCounterOnString(lblSearchBasedOn.getText(), number));
	}

	public void setNumberOfLibsSuggested(int number) {
		lblSuggestedLibraries.setText(setCounterOnString(lblSuggestedLibraries.getText(), number));
	}

	public void setNumberOfLibsPickedForInstall(int number) {
		lblSelectedLibrariesTo.setText(setCounterOnString(lblSelectedLibrariesTo.getText(), number));
	}

	private String setCounterOnString(String text, int count) {
		int lastIndexOf = text.lastIndexOf(" (");
		if (lastIndexOf != -1) {
			text = text.substring(0, lastIndexOf);
		}

		return text + " (" + count + ")";
	}
}
