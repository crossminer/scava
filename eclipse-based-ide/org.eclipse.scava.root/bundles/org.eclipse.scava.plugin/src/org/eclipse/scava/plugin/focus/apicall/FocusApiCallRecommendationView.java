/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.focus.apicall;

import java.util.Map.Entry;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.scava.plugin.mvc.view.ViewPartView;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

public class FocusApiCallRecommendationView extends ViewPartView<IFocusApiCallRecommendationViewEventListener> {

	public static final String ID = "org.eclipse.scava.plugin.focus.apicallrecommendation.FocusRecommendation"; //$NON-NLS-1$
	private Composite defaultMessageComposite;
	private Composite resultsComposite;
	private Composite errorMessageComposite;
	private Label lblErrorMessage;
	private Composite loadingComposite;
	private Label lblLoading;
	private Composite composite;
	private Table table;
	private TableViewer tableViewer;
	private TableColumn tblclmnApiCall;
	private TableViewerColumn tableViewerColumnApiCall;
	private TableColumn tblclmnRanking;
	private TableViewerColumn tableViewerColumnRanking;
	private ApiCallComperator apiCallComperator;

	public FocusApiCallRecommendationView() {
		setTitleImage(ResourceManager.getPluginImage("org.eclipse.scava.plugin",
				"icons/features/crossminer_focus_api_call_recommendation_16x16.png"));
	}

	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		super.createPartControl(container);

		StackLayout layout = new StackLayout();
		getComposite().setLayout(layout);

		loadingComposite = new Composite(getComposite(), SWT.NONE);
		loadingComposite.setBackgroundMode(SWT.INHERIT_FORCE);
		loadingComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		loadingComposite.setLayout(new GridLayout(1, false));

		lblLoading = new Label(loadingComposite, SWT.NONE);
		lblLoading.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblLoading.setText("Loading results...");

		errorMessageComposite = new Composite(getComposite(), SWT.NONE);
		errorMessageComposite.setBackgroundMode(SWT.INHERIT_FORCE);
		errorMessageComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		errorMessageComposite.setLayout(new GridLayout(1, false));

		lblErrorMessage = new Label(errorMessageComposite, SWT.NONE);
		lblErrorMessage.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		lblErrorMessage.setText("Error messages will be shown here.");

		defaultMessageComposite = new Composite(getComposite(), SWT.NONE);
		defaultMessageComposite.setBackgroundMode(SWT.INHERIT_FORCE);
		defaultMessageComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		defaultMessageComposite.setLayout(new GridLayout(1, false));

		Label lblDefaultMessage = new Label(defaultMessageComposite, SWT.NONE);
		lblDefaultMessage.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblDefaultMessage.setText("Results of FOCUS API Call recommendation will be shown here.");

		layout.topControl = defaultMessageComposite;

		resultsComposite = new Composite(getComposite(), SWT.NONE);
		TableColumnLayout tcl_resultsComposite = new TableColumnLayout();
		resultsComposite.setLayout(tcl_resultsComposite);

		tableViewer = new TableViewer(resultsComposite,
				SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		tableViewer.setComparator(apiCallComperator = new ApiCallComperator());

		tableViewerColumnRanking = new TableViewerColumn(tableViewer, SWT.NONE);
		tblclmnRanking = tableViewerColumnRanking.getColumn();
		tcl_resultsComposite.setColumnData(tblclmnRanking, new ColumnPixelData(150, true, true));
		tblclmnRanking.setText("Rank");
		tableViewerColumnRanking.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return Float.toString(((Entry<String, Float>) element).getValue());
			}
		});
		tblclmnRanking.addSelectionListener(getSelectionAdapter(tblclmnRanking, 0));

		tableViewerColumnApiCall = new TableViewerColumn(tableViewer, SWT.NONE);
		tblclmnApiCall = tableViewerColumnApiCall.getColumn();
		tcl_resultsComposite.setColumnData(tblclmnApiCall, new ColumnPixelData(150, true, true));
		tblclmnApiCall.setText("API Call");
		tableViewerColumnApiCall.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Entry<String, Float>) element).getKey();
			}
		});
		tblclmnApiCall.addSelectionListener(getSelectionAdapter(tblclmnApiCall, 1));
		tableViewerColumnApiCall.setEditingSupport(new ApiCallEditingSupport(tableViewer));

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());

		createActions();
		initializeToolBar();
		initializeMenu();
	}

	public class ApiCallEditingSupport extends EditingSupport {

		private final TableViewer viewer;
		private final CellEditor editor;

		public ApiCallEditingSupport(TableViewer viewer) {
			super(viewer);
			this.viewer = viewer;
			this.editor = new TextCellEditor(viewer.getTable(), SWT.READ_ONLY);
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			return editor;
		}

		@Override
		protected boolean canEdit(Object element) {
			return true;
		}

		@Override
		protected Object getValue(Object element) {
			return ((Entry<String, Float>) element).getKey();
		}

		@Override
		protected void setValue(Object element, Object userInputValue) {

		}
	}

	public class ApiCallComperator extends ViewerComparator {
		private int propertyIndex = 0;
		private static final int DESCENDING = 1;
		private int direction = DESCENDING;

		public int getDirection() {
			return direction == 1 ? SWT.DOWN : SWT.UP;
		}

		public void setColumn(int column) {
			if (column == this.propertyIndex) {
				direction = 1 - direction;
			} else {
				this.propertyIndex = column;
				direction = DESCENDING;
			}
		}

		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			Entry<String, Float> p1 = (Entry<String, Float>) e1;
			Entry<String, Float> p2 = (Entry<String, Float>) e2;
			int rc = 0;
			switch (propertyIndex) {
			case 0:
				rc = p1.getValue().compareTo(p2.getValue());
				break;
			case 1:
				rc = -p1.getKey().compareTo(p2.getKey());
				break;
			default:
				rc = 0;
			}

			if (direction == DESCENDING) {
				rc = -rc;
			}

			return rc;
		}
	}

	private SelectionAdapter getSelectionAdapter(final TableColumn column, final int index) {
		SelectionAdapter selectionAdapter = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				apiCallComperator.setColumn(index);
				int dir = apiCallComperator.getDirection();
				tableViewer.getTable().setSortDirection(dir);
				tableViewer.getTable().setSortColumn(column);
				tableViewer.refresh();
			}
		};
		return selectionAdapter;
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		IMenuManager menuManager = getViewSite().getActionBars().getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}

	public void showErrorMessage(String message) {
		StackLayout layout = (StackLayout) getComposite().getLayout();
		layout.topControl = errorMessageComposite;

		lblErrorMessage.setText(message);
		getComposite().requestLayout();

		Display.getDefault().asyncExec(() -> {
			getComposite().requestLayout();
		});
	}

	public void showLoading() {
		StackLayout layout = (StackLayout) getComposite().getLayout();
		layout.topControl = loadingComposite;

		Display.getDefault().asyncExec(() -> {
			getComposite().requestLayout();
		});
	}

	public void showFunctionCallRecommendations(java.util.List<Entry<String, Float>> apiCalls) {
		StackLayout layout = (StackLayout) getComposite().getLayout();
		layout.topControl = resultsComposite;

		tableViewer.setInput(apiCalls.toArray());
		tableViewer.refresh();
		tableViewerColumnApiCall.getColumn().pack();

		Display.getDefault().asyncExec(() -> {
			getComposite().requestLayout();
		});

	}
}
