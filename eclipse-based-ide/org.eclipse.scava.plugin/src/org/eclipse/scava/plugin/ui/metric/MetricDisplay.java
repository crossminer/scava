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
import java.util.Set;

import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.scava.plugin.usermonitoring.database.VertexType;
import org.eclipse.scava.plugin.usermonitoring.event.EventManager;
import org.eclipse.scava.plugin.usermonitoring.metric.MetricManager;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.averageTimeBetweenSaveEvents;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.manualAndScavaLibraryChangeRatio;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.milestoneBasedSaveEventAndDocumentEventRatioMetric;
import org.eclipse.scava.plugin.usermonitoring.metric.provider.MetricProvider;
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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.wb.swt.SWTResourceManager;

public class MetricDisplay extends TitleAreaDialog implements IDisposable {
	private MetricManager manager;
	private DateTime endDay;
	private DateTime endTime;
	private DateTime startDay;
	private DateTime startTime;
	private Combo combo;
	private Text logText;
	Label counter;

	public MetricDisplay(Shell parentShell) {
		super(parentShell);
		manager = EventManager.getMetricManager();
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

		TabFolder tabFolder = new TabFolder(area, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
				TabItem tbtmNewItem = new TabItem(tabFolder, SWT.NONE);
				tbtmNewItem.setText("Metrics");
				
						Composite composite_3 = new Composite(tabFolder, SWT.NONE);
						tbtmNewItem.setControl(composite_3);
						composite_3.setLayout(new GridLayout(1, false));
						new Label(composite_3, SWT.NONE);
						new Label(composite_3, SWT.NONE);
						
								Label m1Label = new Label(composite_3, SWT.NONE);
								m1Label.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.NORMAL));
								m1Label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
								m1Label.setText("Average time between save events: ");
								new Label(composite_3, SWT.NONE);
								
										Label m2Label = new Label(composite_3, SWT.NONE);
										m2Label.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.NORMAL));
										m2Label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
										m2Label.setText("Saving and typing ratio: ");
										new Label(composite_3, SWT.NONE);
										
												Label m3Label = new Label(composite_3, SWT.NONE);
												m3Label.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.NORMAL));
												m3Label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
												m3Label.setText("Manual and Scava library change ratio: ");
												new Label(composite_3, SWT.NONE);
												new Label(composite_3, SWT.NONE);
												
														Button button = new Button(composite_3, SWT.NONE);
														button.addSelectionListener(new SelectionAdapter() {
															@Override
															public void widgetSelected(SelectionEvent e) {
																Double value = new averageTimeBetweenSaveEvents().getValue();
																Double value2 = new milestoneBasedSaveEventAndDocumentEventRatioMetric().getValue();
																Double value3 = new manualAndScavaLibraryChangeRatio().getValue();
																
																m1Label.setText("Average time between save events: " + String.format( "%.2f", value) +" ms");
																m2Label.setText("Saving and typing ratio: " + String.format( "%.2f", value2));
																m3Label.setText("Manual and Scava library change ratio: " + String.format( "%.2f", value3));
															}
														});
														button.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
														button.setText("Refresh metric");

		TabItem tbtmEvents = new TabItem(tabFolder, SWT.NONE);
		tbtmEvents.setText("Events");
		Composite container = new Composite(tabFolder, SWT.NONE);
		tbtmEvents.setControl(container);
		container.setLayout(new GridLayout(3, false));

		Composite composite_1 = new Composite(container, SWT.NONE);
		composite_1.setLayout(new GridLayout(1, false));

		startDay = new DateTime(composite_1, SWT.CALENDAR);
		startDay.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		startTime = new DateTime(composite_1, SWT.TIME);
		startTime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		Composite composite_2 = new Composite(container, SWT.NONE);
		composite_2.setLayout(new GridLayout(2, false));
		composite_2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		combo = new Combo(composite_2, SWT.NONE);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		combo.setItems(VertexType.getEvents().toArray(new String[0]));
		
		counter = new Label(composite_2, SWT.NONE);
		counter.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		counter.setText("Count: ");
		counter.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.NORMAL));

		Composite composite = new Composite(container, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false, 1, 1));
		composite.setLayout(new GridLayout(1, false));

		endDay = new DateTime(composite, SWT.CALENDAR);
		endDay.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		endTime = new DateTime(composite, SWT.TIME);
		endTime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		Composite textContainer = new Composite(area, SWT.NONE);
		textContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		textContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));

		logText = new Text(textContainer, SWT.BORDER | SWT.READ_ONLY | SWT.V_SCROLL | SWT.MULTI);

		return area;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {

		Button refresh = createButton(parent, IDialogConstants.RETRY_ID, "Refresh", false);
		refresh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		refresh.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {

				try {
					List<Vertex> vertexTypeListTimeBased = MetricProvider.getVertexTypeListTimeBased(
							combo.getItem(combo.getSelectionIndex()), toDate(startDay, startTime),
							toDate(endDay, endTime));
					counter.setText("Count: " + vertexTypeListTimeBased.size());

					logText.setText("");

					for (Vertex vertex : vertexTypeListTimeBased) {
						Set<String> keys = vertex.keys();
						logText.append(vertex.toString() + "\n");
						for (String key : keys) {
							logText.append(
									"\t" + vertex.property(key).key() + " : " + vertex.property(key).value() + "\n");

						}

					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

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
