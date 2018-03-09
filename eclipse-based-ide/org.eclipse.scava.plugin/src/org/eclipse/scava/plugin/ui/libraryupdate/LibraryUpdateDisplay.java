/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.ui.libraryupdate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.scava.commons.library.Library;
import org.eclipse.scava.plugin.event.informative.IInformativeEvent;
import org.eclipse.scava.plugin.event.informative.IInformativeEventSubscriber;
import org.eclipse.scava.plugin.event.informative.InformativeEvent;
import org.eclipse.scava.plugin.event.notifier.INotifierEvent;
import org.eclipse.scava.plugin.event.notifier.INotifierEventSubscriber;
import org.eclipse.scava.plugin.event.notifier.NotifierEvent;
import org.eclipse.scava.plugin.ui.libraryupdate.provider.LibraryNameLabelProvider;
import org.eclipse.scava.plugin.ui.libraryupdate.provider.LibraryTreeContentProvider;
import org.eclipse.scava.plugin.ui.libraryupdate.provider.LibraryVersionLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.services.IDisposable;
import org.eclipse.wb.swt.ResourceManager;

public class LibraryUpdateDisplay extends TitleAreaDialog implements IDisposable {
	private final IInformativeEvent<List<AlternativeLibrary>> librariesAccepted = new InformativeEvent<>();
	private final INotifierEvent librariesCancelled = new NotifierEvent();
	private final IInformativeEvent<Library> librarySelected = new InformativeEvent<>();
	
	private CheckboxTreeViewer checkboxTreeViewer;
	private Label detailLabel;
	private Composite container;
	
	public LibraryUpdateDisplay(Shell parentShell) {
		super(parentShell);
		setHelpAvailable(false);
		setBlockOnOpen(false);
		
		setShellStyle(SWT.SHELL_TRIM | SWT.BORDER | SWT.APPLICATION_MODAL);
		open();
		
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Library update");
		
		newShell.addListener(SWT.Close, new Listener() {
			public void handleEvent(Event e) {
				e.doit = false;
				librariesCancelled.invoke();
			}
		});
	}
	
	public IInformativeEventSubscriber<List<AlternativeLibrary>> getLibrariesAccepted() {
		return librariesAccepted;
	}
	
	public INotifierEventSubscriber getLibrariesCancelled() {
		return librariesCancelled;
	}
	
	public IInformativeEventSubscriber<Library> getLibrarySelected() {
		return librarySelected;
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		
		setMessage("Select library to update");
		setTitleImage(ResourceManager.getPluginImage("org.scava.plugin", "icons/SCAVA-logo-small.png"));
		setTitle("Available library updates");
		Composite area = (Composite) super.createDialogArea(parent);
		container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Composite composite = new Composite(container, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		checkboxTreeViewer = new CheckboxTreeViewer(composite, SWT.MULTI);
		checkboxTreeViewer.setAutoExpandLevel(2);
		checkboxTreeViewer.setContentProvider(new LibraryTreeContentProvider());
		
		Tree tree = checkboxTreeViewer.getTree();
		tree.setLayoutDeferred(true);
		tree.setHeaderVisible(true);
		
		TreeViewerColumn mainColumn = new TreeViewerColumn(checkboxTreeViewer, SWT.NONE);
		mainColumn.getColumn().setText("Name");
		mainColumn.getColumn().setWidth(300);
		mainColumn.setLabelProvider(new DelegatingStyledCellLabelProvider(new LibraryNameLabelProvider(
				createImageDescriptor())));
		
		TreeViewerColumn versionColumn = new TreeViewerColumn(checkboxTreeViewer, SWT.NONE);
		TreeColumn treeColumn = versionColumn.getColumn();
		treeColumn.setImage(null);
		treeColumn.setAlignment(SWT.CENTER);
		versionColumn.getColumn().setText("Version");
		versionColumn.getColumn().setWidth(100);
		versionColumn.setLabelProvider(new DelegatingStyledCellLabelProvider(new LibraryVersionLabelProvider()));
		
		Composite composite_1 = new Composite(container, SWT.NONE);
		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false, 1, 1));
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Group group = new Group(composite_1, SWT.NONE);
		group.setText("Details");
		FillLayout fl_group = new FillLayout(SWT.HORIZONTAL);
		fl_group.spacing = 20;
		fl_group.marginWidth = 10;
		fl_group.marginHeight = 10;
		group.setLayout(fl_group);
		
		detailLabel = new Label(group, SWT.NONE);
		detailLabel.setText("Select a library for further information.");
		
		checkboxTreeViewer.addCheckStateListener(checkListener());
		checkboxTreeViewer.addSelectionChangedListener(selectionListener());
		
		return area;
	}
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		@SuppressWarnings("unused")
		Button button = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}
	
	@Override
	protected void okPressed() {
		List<AlternativeLibrary> acceptedLibraries = getSelectedLibraries();
		librariesAccepted.invoke(acceptedLibraries);
	}
	
	@Override
	protected void cancelPressed() {
		librariesCancelled.invoke();
	}
	
	private ICheckStateListener checkListener() {
		return new ICheckStateListener() {
			
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				
				ITreeContentProvider provider = (ITreeContentProvider) checkboxTreeViewer.getContentProvider();
				Object parent = provider.getParent(event.getElement());
				Object checked[] = checkboxTreeViewer.getCheckedElements();
				
				if (event.getElement() instanceof ProjectLibrary) {
					if (!event.getChecked()) {
						checkboxTreeViewer.setSubtreeChecked(event.getElement(), false);
						return;
					} else {
						checkboxTreeViewer.setChecked(event.getElement(), false);
					}
				}
				
				if (!event.getChecked()) {
					checkboxTreeViewer.setChecked(parent, false);
					return;
				}
				
				for (Object library : checked) {
					
					if (library instanceof ProjectLibrary) {
						continue;
					}
					
					if (event.getElement() instanceof AlternativeLibrary) {
						checkboxTreeViewer.setChecked(parent, true);
						checkboxTreeViewer.setGrayed(parent, true);
						
						if (!event.getElement().equals(library) && parent == ((AlternativeLibrary) library)
								.getOriginalLibrary()) {
							checkboxTreeViewer.setChecked(library, false);
							
						}
					}
					
				}
			}
		};
	}
	
	private ISelectionChangedListener selectionListener() {
		return new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				
				IStructuredSelection selection = (IStructuredSelection) checkboxTreeViewer.getSelection();
				if (selection.size() == 1) {
					Object selectedObject = selection.getFirstElement();
					
					Library selectedLibrary = null;
					
					if (selectedObject instanceof AlternativeLibrary) {
						selectedLibrary = ((AlternativeLibrary) selectedObject).getLibrary();
					}
					if (selectedObject instanceof ProjectLibrary) {
						selectedLibrary = ((ProjectLibrary) selectedObject).getLibrary();
					}
					
					if (selectedLibrary != null) {
						librarySelected.invoke(selectedLibrary);
					}
				}
			}
		};
	}
	
	@Override
	protected Point getInitialSize() {
		return new Point(800, 600);
	}
	
	private List<AlternativeLibrary> getSelectedLibraries() {
		Object checked[] = checkboxTreeViewer.getCheckedElements();
		
		return Arrays.asList(checked).stream().filter(AlternativeLibrary.class::isInstance).map(
				AlternativeLibrary.class::cast).collect(Collectors.toList());
	}
	
	public void showLibraries(List<ProjectLibrary> libraries) {
		checkboxTreeViewer.setInput(libraries);
	}
	
	public void showDescription(String description) {
		detailLabel.setText(description);
		container.layout();
	}
	
	private ImageDescriptor createImageDescriptor() {
		return ImageDescriptor.createFromImage(ResourceManager.getPluginImage("org.scava.plugin",
				"icons/bulb.png"));
	}
	
	@Override
	public boolean close() {
		return false;
	}
	
	@Override
	public void dispose() {
		librariesAccepted.dispose();
		librariesCancelled.dispose();
		librarySelected.dispose();
		super.close();
	}
}
