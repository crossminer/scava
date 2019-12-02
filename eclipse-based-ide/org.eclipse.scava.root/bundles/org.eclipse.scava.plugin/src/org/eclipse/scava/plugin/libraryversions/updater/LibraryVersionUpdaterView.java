/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.libraryversions.updater;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.scava.plugin.libraryversions.Library;
import org.eclipse.scava.plugin.mvc.view.TitleAreaDialogView;
import org.eclipse.scava.plugin.ui.verticalList.VerticalList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

public class LibraryVersionUpdaterView extends TitleAreaDialogView<ILibraryVersionUpdaterViewEventListener> {
	private VerticalList libraryList;
	private Map<Library, LibraryRepresentation> libraries = new HashMap<>();
	private Button button_ok;
	private Button btnKeepCurrentVersions;
	private Button btnUpdateToTheNext;
	private Button btnUpdateToTheNewest;
	private Group grpApiMigration;
	private Label lblIfYouWould;
	private Text txtJarPath;
	private Button btnJarBrowse;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public LibraryVersionUpdaterView(Shell parentShell) {
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
		setTitle("New library versions available");
		setTitleImage(
				ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/logo-titleimage-titlearedialog.png"));
		setMessage("Select the version which you want to install");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setBackgroundMode(SWT.INHERIT_FORCE);
		container.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		container.setLayout(new GridLayout(3, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		ScrolledComposite scrolledComposite = new ScrolledComposite(container,
				SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		libraryList = new VerticalList(scrolledComposite, SWT.NONE);
		libraryList.setLayout(new GridLayout(1, false));

		scrolledComposite.setContent(libraryList);
		scrolledComposite.setMinSize(libraryList.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		btnKeepCurrentVersions = new Button(container, SWT.NONE);
		btnKeepCurrentVersions.setEnabled(false);
		btnKeepCurrentVersions.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				libraries.values().forEach(librep -> librep.comboBox.select(0));
				updateButtons();
			}
		});
		btnKeepCurrentVersions.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		btnKeepCurrentVersions.setText("Keep current versions");

		btnUpdateToTheNext = new Button(container, SWT.NONE);
		btnUpdateToTheNext.setEnabled(false);
		btnUpdateToTheNext.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				libraries.entrySet().forEach(entry -> {
					if (entry.getKey().getReleaseDate() != null) {
						entry.getValue().comboBox.select(1);
					}
				});
				updateButtons();
			}
		});
		btnUpdateToTheNext.setText("Update to the next versions");

		btnUpdateToTheNewest = new Button(container, SWT.NONE);
		btnUpdateToTheNewest.setEnabled(false);
		btnUpdateToTheNewest.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				libraries.values().forEach(librep -> librep.comboBox.select(librep.comboBox.getItemCount() - 1));
				updateButtons();
			}
		});
		btnUpdateToTheNewest.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnUpdateToTheNewest.setText("Update to the newests");

		grpApiMigration = new Group(container, SWT.NONE);
		grpApiMigration.setLayout(new GridLayout(2, false));
		grpApiMigration.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		grpApiMigration.setText("API Migration");

		lblIfYouWould = new Label(grpApiMigration, SWT.WRAP);
		lblIfYouWould.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		lblIfYouWould.setText(
				"The API Migration feature may help you to upgrade your project to use newer versions of a set of library. To use this feature:\r\n1) Export your project to a JAR file\r\n2) Insert the path of this JAR into the following field");

		txtJarPath = new Text(grpApiMigration, SWT.BORDER);
		txtJarPath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		btnJarBrowse = new Button(grpApiMigration, SWT.NONE);
		btnJarBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(getShell(), SWT.OPEN);
				fileDialog.setFilterExtensions(new String[] { "*.jar", "*.JAR" });

				File currentFile = new File(txtJarPath.getText());
				if (currentFile.exists()) {
					fileDialog.setFilterPath(currentFile.getAbsolutePath());
				}

				String result = fileDialog.open();
				if (result != null) {
					txtJarPath.setText(result);
				}

			}
		});
		btnJarBrowse.setText("Browse");

		return area;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		button_ok = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		button_ok.setEnabled(false);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setToolTipText("");
		newShell.setImage(ResourceManager.getPluginImage("org.eclipse.scava.plugin",
				"icons/features/new_library_available_16x16.png"));
		newShell.setText("Library version updater");

		newShell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		newShell.setBackgroundMode(SWT.INHERIT_FORCE);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(713, 645);
	}

	public void showLibrary(Library library, List<Library> versions) {

		Group grpGroupidartifactid = new Group(libraryList, SWT.NONE);
		grpGroupidartifactid.setLayout(new GridLayout(2, false));
		grpGroupidartifactid.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpGroupidartifactid.setText(library.getGroupId() + ":" + library.getArtifactId());

		if (library.getReleaseDate() == null) {
			Label lblUnknownVersion = new Label(grpGroupidartifactid, SWT.NONE);
			lblUnknownVersion.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
			lblUnknownVersion.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
			lblUnknownVersion.setText(
					"Could not determine which versions are newer than the currently used. All versions are listed.");
		}

		Label lblTargetVersion = new Label(grpGroupidartifactid, SWT.NONE);
		lblTargetVersion.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTargetVersion.setText("Target version:");

		List<String> versionStrings = new ArrayList<>();
		versions.forEach(lib -> versionStrings.add(lib.getVersion() + " [" + lib.getReleaseDate() + "]"));
		versionStrings.add(library.getVersion() + " "
				+ (library.getReleaseDate() == null ? "" : "[" + library.getReleaseDate() + "]") + " (Current)");
		Collections.reverse(versionStrings);

		Combo combo = new Combo(grpGroupidartifactid, SWT.READ_ONLY);
		combo.setItems(versionStrings.toArray(new String[0]));
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		combo.select(0);
		combo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateButtons();
			};
		});

		libraryList.add(grpGroupidartifactid);
		ArrayList<Library> versionsCopy = new ArrayList<>(versions);
		Collections.reverse(versionsCopy);
		libraries.put(library, new LibraryRepresentation(combo, versionsCopy));
		updateButtons();
	}

	private class LibraryRepresentation {
		Combo comboBox;
		List<Library> versions;

		public LibraryRepresentation(Combo comboBox, List<Library> versions) {
			super();
			this.comboBox = comboBox;
			this.versions = versions;
		}

	}

	@Override
	protected void okPressed() {
		Map<Library, Library> selectedLibraries = getLibrariesToBeUpdated();
		eventManager.invoke(l -> l.onInstall(selectedLibraries, txtJarPath.getText()));
	}

	private Map<Library, Library> getLibrariesToBeUpdated() {
		Map<Library, Library> selectedLibraries = new HashMap<>();
		libraries.entrySet().forEach(entry -> {
			Library library = entry.getKey();
			LibraryRepresentation libraryRepresentation = entry.getValue();
			Combo comboBox = libraryRepresentation.comboBox;
			int selectionIndex = comboBox.getSelectionIndex();
			if (selectionIndex != 0) {
				selectedLibraries.put(library, libraryRepresentation.versions.get(selectionIndex - 1));
			}
		});
		return selectedLibraries;
	}

	private void updateButtons() {
		boolean isAnyToBeUpdated = libraries.values().stream()
				.anyMatch(libRep -> libRep.comboBox.getSelectionIndex() != 0);
		button_ok.setEnabled(isAnyToBeUpdated);
		btnKeepCurrentVersions.setEnabled(isAnyToBeUpdated);

		boolean isAnyNotToBeUpdatedToTheNextVersion = libraries.values().stream()
				.anyMatch(libRep -> libRep.comboBox.getSelectionIndex() != 1 && libRep.comboBox.getItemCount() > 0);
		btnUpdateToTheNext.setEnabled(isAnyNotToBeUpdatedToTheNextVersion);

		boolean isAnyNotToBeUpdatedToTheLatestVersion = libraries.values().stream()
				.anyMatch(libRep -> libRep.comboBox.getSelectionIndex() != libRep.comboBox.getItemCount() - 1
						&& libRep.comboBox.getItemCount() > 0);
		btnUpdateToTheNewest.setEnabled(isAnyNotToBeUpdatedToTheLatestVersion);
	}

	public void showLastJarPath(String path) {
		txtJarPath.setText(path);
	}
}
