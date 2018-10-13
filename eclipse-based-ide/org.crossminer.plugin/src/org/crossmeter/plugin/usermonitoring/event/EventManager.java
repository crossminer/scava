package org.crossmeter.plugin.usermonitoring.event;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.crossmeter.plugin.Activator;
import org.crossmeter.plugin.usermonitoring.database.DatabaseManager;
import org.crossmeter.plugin.usermonitoring.event.document.DocumentEvent;
import org.crossmeter.plugin.usermonitoring.event.eclipse.EclipseCloseEventListener;
import org.crossmeter.plugin.usermonitoring.event.element.ResourceElementEventListener;
import org.crossmeter.plugin.usermonitoring.event.part.PartEvent;
import org.crossmeter.plugin.usermonitoring.event.resource.ResourceEvent;
import org.crossmeter.plugin.usermonitoring.event.resource.ResourceEventListener;
import org.crossmeter.plugin.usermonitoring.event.window.WindowEvent;
import org.crossmeter.plugin.usermonitoring.event.window.WindowEventListener;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchListener;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;

public class EventManager {

	private static DatabaseManager manager;
	private static ITextEditor textEditor;
	private static boolean saveToDatabase = true;

	private EventManager() {

	}

	public static void setEditor(ITextEditor tEditor) {
		if (textEditor == null) {
			EventManager.textEditor = tEditor;
		}
	}

	public static ITextEditor getEditor() {
		return EventManager.textEditor;
	}

	public static void triggerEvent(IEvent event) {

		try {
			BufferedWriter bufferedWriter = new BufferedWriter(
					new FileWriter(System.getProperty("user.home") + "/Desktop" + "/" + "Eventlog.txt", true));
			bufferedWriter.append(event.toString());
			bufferedWriter.append("\n");
			bufferedWriter.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

		System.out.println(event);

		if (saveToDatabase) {

			manager.insertEventNode(event);

		}

	}

	public static void enableListeners() {

		manager = new DatabaseManager();
		subscribeResourceListener();
		subscribeWindowListener();
		subscribeEclipseCloseListener();
	}
	
	private static void subscribeEclipseCloseListener() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		
		workbench.addWorkbenchListener(new EclipseCloseEventListener());
	}
	
	private static void subscribeWindowListener() {
		IWorkbench workbench = PlatformUI.getWorkbench();

		WindowEventListener windowListener = new WindowEventListener();
		windowListener.windowOpened(workbench.getWorkbenchWindows()[0]);

		workbench.addWindowListener(windowListener);

	}

	private static void subscribeResourceListener() {
		ResourceEventListener listener = new ResourceEventListener();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(listener,
				IResourceChangeEvent.PRE_CLOSE | IResourceChangeEvent.PRE_DELETE | IResourceChangeEvent.PRE_BUILD
						| IResourceChangeEvent.POST_BUILD | IResourceChangeEvent.POST_CHANGE);
	}

}
