package org.eclipse.scava.plugin.usermonitoring.event;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.scava.plugin.usermonitoring.database.DatabaseManager;
import org.eclipse.scava.plugin.usermonitoring.event.classpath.ClasspathChangeListener;
import org.eclipse.scava.plugin.usermonitoring.event.document.DocumentEvent;
import org.eclipse.scava.plugin.usermonitoring.event.eclipse.EclipseCloseEventListener;
import org.eclipse.scava.plugin.usermonitoring.event.launch.LaunchEventListener;
import org.eclipse.scava.plugin.usermonitoring.event.resource.ResourceEvent;
import org.eclipse.scava.plugin.usermonitoring.event.resource.ResourceEventListener;
import org.eclipse.scava.plugin.usermonitoring.event.window.WindowEventListener;
import org.eclipse.scava.plugin.usermonitoring.metric.MetricManager;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.ui.IWorkbench;
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

	public static void processEvent(IEvent event) {

		if (saveToDatabase) {		
			manager.insertVertex(event);
		}

		
		
	}
	
	

	private static void toLog(IEvent event) {
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(
					new FileWriter(System.getProperty("user.home") + "/Desktop" + "/" + "Eventlog.txt", true));
			bufferedWriter.append(event.toString());
			bufferedWriter.append("\n");
			bufferedWriter.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public static void enableListeners() {

		JavaCore.addElementChangedListener(new ClasspathChangeListener(), ElementChangedEvent.POST_CHANGE);
		
		manager = new DatabaseManager();
		subscribeResourceListener();
		subscribeWindowListener();
		subscribeEclipseCloseListener();
		subscribeLaunchListener();
	}

	public static MetricManager getMetricManager() {

		return manager.getMetricManager();
	}

	private static void subscribeEclipseCloseListener() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		workbench.addWorkbenchListener(new EclipseCloseEventListener());
	}

	private static void subscribeLaunchListener() {
		
		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		manager.addLaunchListener(new LaunchEventListener());
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
