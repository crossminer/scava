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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
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
import org.eclipse.swt.custom.StyledText;
import org.eclipse.jface.text.Document;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

public class MetricDisplay extends TitleAreaDialog implements IDisposable {
	private Text metricCounter;
	private String calculatedMetrics = "";
	StyledText styledText;

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
		
		Composite composite = new Composite(area, SWT.NONE);
		composite.setBackgroundImage(SWTResourceManager.getImage("C:\\Users\\Szamos\\Desktop\\maven.png"));
		
		
		styledText = new StyledText(area, SWT.BORDER | SWT.V_SCROLL);
		styledText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		Document document = new Document();
		document.set("almafa");

		refreshMetrics();

		return area;
	}

	private void refreshMetrics() {
		getShell().getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				
			
				
				
			

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
		Button button = createButton(parent, IDialogConstants.OK_ID, "Close", false);
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
