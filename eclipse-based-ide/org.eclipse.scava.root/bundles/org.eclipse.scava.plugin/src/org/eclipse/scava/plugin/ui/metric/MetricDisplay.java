/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.ui.metric;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.Metric;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.MetricException;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.MetricProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.services.IDisposable;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ProgressBar;

public class MetricDisplay extends TitleAreaDialog implements IDisposable {
	private Table table;
	private Text metricCounter;
	private ProgressBar progressBar;

	public MetricDisplay(Shell parentShell) {
		super(parentShell);
		setHelpAvailable(false);
		setBlockOnOpen(false);
		setShellStyle(SWT.SHELL_TRIM | SWT.BORDER | SWT.APPLICATION_MODAL);

		open();

	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Metric");

		newShell.addListener(SWT.Close, new Listener() {
			public void handleEvent(Event e) {
				e.doit = false;
				dispose();

			}
		});
	}

	@Override
	protected Control createDialogArea(Composite parent) {

		setTitleImage(null);
		setTitle("Metric informations");
		Composite area = (Composite) super.createDialogArea(parent);
		area.setLayout(new GridLayout(1, false));

		Composite textContainer = new Composite(area, SWT.NONE);
		textContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		textContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));

		table = new Table(textContainer, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(300);
		tblclmnNewColumn.setText("ID");

		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(100);
		tblclmnNewColumn_1.setText("Value");
		
		TableColumn tblclmnNewColumn_2 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_2.setWidth(300);
		tblclmnNewColumn_2.setText("Description");
		

		progressBar = new ProgressBar(area, SWT.SMOOTH);
		progressBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		refreshMetrics();

		return area;
	}

	private void refreshMetrics() {
		getShell().getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				
				table.setItemCount(0);
				progressBar.setVisible(true);
				progressBar.setSelection(0);
				
				List<Metric> metricList = MetricProvider.getMetricList();
				progressBar.setMaximum(metricList.size());
				for (Metric metric : metricList) {
					
					TableItem tableItem = new TableItem(table, SWT.NONE);
					tableItem.setText(new String[] {});
					tableItem.setText(0, metric.getID());
					try {
						tableItem.setText(1, metric.getCurrentValue() + "");
					} catch (MetricException e1) {
						tableItem.setText(1, "Not enough event to calculate metric");
					}
					tableItem.setText(2,metric.getDescription());

					table.redraw();
					table.update();

					progressBar.setSelection(progressBar.getSelection() + 1);
					progressBar.redraw();
					progressBar.update();
				}
				progressBar.setVisible(false);
			}
		});
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {

		Button refresh = createButton(parent, IDialogConstants.RETRY_ID, "Refresh", false);
		refresh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				refreshMetrics();
			}
		});
		refresh.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {

			}
		});

		@SuppressWarnings("unused")
		Button button = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, false);
	}

	@Override
	protected void okPressed() {
		dispose();
	}

	@Override
	protected void cancelPressed() {
		dispose();
	}

	@Override
	protected Point getInitialSize() {
		return new Point(800, 600);
	}

	private Date toDate(DateTime dayPicker, DateTime timePicker) throws Exception {
		String input = "";

		int year = dayPicker.getYear();
		int month = dayPicker.getMonth() + 1;
		int day = dayPicker.getDay();
		int hours = timePicker.getHours();
		int minutes = timePicker.getMinutes();
		int seconds = timePicker.getSeconds();

		input = year + "/" + month + "/" + day + "/" + hours + "/" + minutes + "/" + seconds;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");
		Date t;

		t = ft.parse(input);
		return t;
	}

	@Override
	public boolean close() {
		return false;
	}

	@Override
	public void dispose() {
		super.close();
	}
}
