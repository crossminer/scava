package org.eclipse.scava.crossflow.dt.views;

import java.io.PrintStream;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.scava.crossflow.runtime.BuiltinStreamConsumer;
import org.eclipse.scava.crossflow.runtime.utils.Result;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.ui.internal.util.BundleUtility;
import org.eclipse.ui.part.ViewPart;
import org.osgi.framework.Bundle;

public class CrossflowExecutionView extends ViewPart {

	public static final String ID = "org.eclipse.scava.crossflow.dt.views.CrossflowExecutionView";

	private class Row {
		private String key;
		private Result values;

		public Row(String key) {
			this.key = key;
			values = new Result();
		}

		public Row(String key, Result values) {
			this.key = key;
			this.values = values;
		}

		public String getId() {
			return key;
		}

		@Override
		public String toString() {
			return key + ": " + values;
		}

		public Result getValues() {
			return values;
		}

		public void setValues(Result values) {
			if (this.values == null || this.values.size() == values.size())
				this.values = values;
			else
				throw new UnsupportedOperationException(
						"cannot set values to ones of a different length, original length was: " + this.values.size()
								+ ", new length is: " + values.size());
		}

		public void addToValues(Result values2) {
			for (int i = 0; i < values.size(); i++)
				if (values.get(i) instanceof Integer && values2.get(i) instanceof Integer)
					values.set(i, (Integer) values.get(i) + (Integer) values2.get(i));

		}

		public void addToValue(Result values2, int arrayPosition) {
			if (values.get(arrayPosition) instanceof Integer && values2.get(arrayPosition) instanceof Integer)
				values.set(arrayPosition, (Integer) values.get(arrayPosition) + (Integer) values2.get(arrayPosition));
		}

		public void addToAllValues(Result values2) {
			if (values.size() == values2.size())
				for (int i = 0; i < values.size(); i++) {
					if (values.get(i) instanceof Integer && values2.get(i) instanceof Integer)
						values.set(i, (Integer) values.get(i) + (Integer) values2.get(i));
				}
			else
				throw new UnsupportedOperationException(
						"cannot update all values to ones from a source of a different length, original length was: "
								+ values.size() + ", new length is: " + values2.size());

		}
	}

	private HashMap<String, Row> contents;

	private boolean columnNamesInitialized = false;
	protected TableViewer viewer;
	protected Table table;

	protected MessageConsoleStream out;

	/**
	 * The constructor.
	 */
	public CrossflowExecutionView() {

		contents = new HashMap<>();

		MessageConsole myConsole = findConsole("Crossflow Execution");

		out = myConsole.newMessageStream();
		MessageConsoleStream err = myConsole.newMessageStream();
		Device device = Display.getCurrent();
		Color red = new Color(device, 255, 0, 0);
		err.setColor(red);

		// avoid setting generic system.out to avoid spam from kafka and flink
		// (try find another way to limit this output)
		System.setOut(new PrintStream(out));
		System.setErr(new PrintStream(err));
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize it.
	 */
	@Override
	public void createPartControl(Composite parent) {

		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);

		table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		// Font f = table.getFont();
		// FontData[] fd = f.getFontData();
		// fd[0].setHeight(18);
		// table.setFont(new Font(table.getDisplay(), fd[0]));
		viewer.setContentProvider(ArrayContentProvider.getInstance());

		//
		hookContextMenu();
		contributeToActionBars();

		//

	}

	public boolean initializeColumnNames(Result cols) {
		if (columnNamesInitialized)
			return false;

		createColumns(cols);
		columnNamesInitialized = true;
		return true;
	}

	private void createColumns(Result cols) {

		for (int i = 0; i < cols.size(); i++) {

			final int it = i;
			final String name = (String) cols.get(it);
			TableViewerColumn colI = new TableViewerColumn(viewer, SWT.NONE);
			colI.getColumn().setWidth(200);
			colI.getColumn().setText(name);
			colI.setLabelProvider(new ColumnLabelProvider() {
				@Override
				public String getText(Object element) {
					Row r = (Row) element;
					return r.getValues().get(it).toString();
				}
			});

			Listener sortListener = new Listener() {
				public void handleEvent(Event e) {
					TableItem[] items = table.getItems();
					// Collator collator =
					// Collator.getInstance(Locale.getDefault());
					TableColumn column = (TableColumn) e.widget;
					TableColumn[] cols = table.getColumns();
					//
					int columnIndex = -1;
					for (int l = 0; l < cols.length; l++)
						if (cols[l] == column) {
							columnIndex = l;
							break;
						}
					//
					for (int i = 1; i < items.length; i++) {
						String value1 = items[i].getText(columnIndex);
						for (int j = 0; j < i; j++) {
							String value2 = items[j].getText(columnIndex);
							if (simpleComparison(value1, value2) < 0) {
								//
								String[] values = new String[cols.length];
								for (int k = 0; k < cols.length; k++) {
									values[k] = items[i].getText(k);
								}
								//
								items[i].dispose();
								TableItem item = new TableItem(table, SWT.NONE, j);
								item.setText(values);
								items = table.getItems();
								break;
							}
						}
					}
					table.setSortColumn(column);
					// viewer.refresh();
				}

			};

			colI.getColumn().addListener(SWT.Selection, sortListener);

		}
	}

	// tries to cast the strings as integers then doubles and then does string
	// comparison if both fail -- alphabetical comparison and largest ->
	// smallest number
	private int simpleComparison(String value1, String value2) {

		try {
			Integer v1 = Integer.parseInt(value1);
			Integer v2 = Integer.parseInt(value2);
			table.setSortDirection(SWT.DOWN);
			return v2.compareTo(v1);
		} catch (Exception e) {
		}

		try {
			Double v1 = Double.parseDouble(value1);
			Double v2 = Double.parseDouble(value2);
			table.setSortDirection(SWT.DOWN);
			return v2.compareTo(v1);
		} catch (Exception e) {
		}

		table.setSortDirection(SWT.UP);
		return value1.compareTo(value2);

	}

	private void createResultsListener() {

		// runStub();
		out.println("trying to connect to results publisher queue");

		try {
			ResultsReceiver rp = new ResultsReceiver();
			rp.addConsumer(new BuiltinStreamConsumer<Result>() {
				@Override
				public void consume(Result job) {
					System.err.println("consuming...");
					System.err.println(job);
					updateRowUsingAddition((String) job.get(0), job);
					out.println("updated technology: " + job.get(0));

				}

			});

			out.println("connected to results publisher queue");

		} catch (Exception e) {
			System.err.println("error during connection:");
			e.printStackTrace();
		}

	}

	// private void runStub() {
	//
	// updateRowUsingReplacement("row one", 0);
	// updateRowUsingReplacement("row two", 0);
	// updateRowUsingAddition("adding row", 0);
	//
	// updateRowUsingReplacement("row one", 2);
	// updateRowUsingReplacement("row two", 1);
	// updateRowUsingAddition("adding row", 1);
	//
	// updateRowUsingReplacement("row one", 1);
	// updateRowUsingReplacement("row two", 2);
	// updateRowUsingAddition("adding row", 2);
	//
	// }

	private void updateRowUsingAddition(String key, Result values) {

		out.println("updating row: " + key);
		out.println("by adding values: " + values);

		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				Row r = null;
				if ((r = contents.get(key)) == null) {
					contents.put(key, new Row(key, values));
					// viewer.add(r);
				} else {
					r.addToAllValues(values);
					// viewer.update(r, null);
				}
				viewer.setInput(contents.values());
				viewer.refresh();
			}
		});

	}

	void updateRowUsingReplacement(String key, Result values) {

		out.println("updating row: " + key);
		out.println("with values: " + values);

		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				Row r = null;
				if ((r = contents.get(key)) == null) {
					contents.put(key, new Row(key, values));
					// viewer.add(r);
				} else {
					r.setValues(values);
					// viewer.update(r, null);
				}
				viewer.setInput(contents.values());
				viewer.refresh();
			}
		});
	}

	// void updateRowUsingAddition(String key, int value) {
	//
	// Display.getDefault().asyncExec(new Runnable() {
	// @Override
	// public void run() {
	//
	// Row r = contents.get(key);
	// if (r == null) {
	// r = new Row(key, value);
	// contents.put(key, r);
	// viewer.add(r);
	// } else {
	// r.setValue(r.getValue() + value);
	// viewer.update(r, null);
	// }
	// }
	// });
	// }

	// void updateRowUsingReplacement(String key, int value) {
	//
	// Display.getDefault().asyncExec(new Runnable() {
	// @Override
	// public void run() {
	//
	// Row r = contents.get(key);
	// if (r == null) {
	// r = new Row(key, value);
	// contents.put(key, r);
	// viewer.add(r);
	// } else {
	// r.setValue(value);
	// viewer.update(r, null);
	// }
	// }
	// });
	// }

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				CrossflowExecutionView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		//
	}

	private void fillContextMenu(IMenuManager manager) {
		//
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		IAction action = new Action("Connect to Queue", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				createResultsListener();
			}
		};
		action.setDescription("Connect to Queue");

		Bundle bundle = Platform.getBundle("org.eclipse.scava.crossflow.dt");
		URL fullPathString = BundleUtility.find(bundle, "icons/running.gif");
		ImageDescriptor image = ImageDescriptor.createFromURL(fullPathString);

		action.setImageDescriptor(image);
		manager.add(action);

		//

		action = new Action("Clear Results", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				viewer.setInput(null);
				contents.clear();
				viewer.refresh();
				out.println("cleared table contents...");
				//
			}
		};
		action.setDescription("Clear Results");

		fullPathString = BundleUtility.find(bundle, "icons/busy.gif");
		image = ImageDescriptor.createFromURL(fullPathString);

		action.setImageDescriptor(image);
		manager.add(action);

	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	private MessageConsole findConsole(String name) {

		// TODO temp fix to reduce console spam
		Logger.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME).setLevel(Level.WARNING);
		Logger.getLogger("org").setLevel(Level.WARNING);
		Logger.getLogger("akka").setLevel(Level.WARNING);
		Logger.getLogger("kafka").setLevel(Level.WARNING);

		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager conMan = plugin.getConsoleManager();
		IConsole[] existing = conMan.getConsoles();
		for (int i = 0; i < existing.length; i++)
			if (name.equals(existing[i].getName()))
				return (MessageConsole) existing[i];
		// no console found, so create a new one
		MessageConsole myConsole = new MessageConsole(name, null);
		conMan.addConsoles(new IConsole[] { myConsole });
		return myConsole;
	}

}
