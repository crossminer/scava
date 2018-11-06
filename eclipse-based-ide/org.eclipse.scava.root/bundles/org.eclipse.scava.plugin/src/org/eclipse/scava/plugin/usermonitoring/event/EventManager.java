package org.eclipse.scava.plugin.usermonitoring.event;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.scava.plugin.usermonitoring.event.classpath.ClasspathChangeListener;
import org.eclipse.scava.plugin.usermonitoring.event.eclipse.EclipseCloseEventListener;
import org.eclipse.scava.plugin.usermonitoring.event.launch.LaunchEventListener;
import org.eclipse.scava.plugin.usermonitoring.event.resource.ResourceEventListener;
import org.eclipse.scava.plugin.usermonitoring.event.window.WindowEventListener;
import org.eclipse.scava.plugin.usermonitoring.metric.adapter.GremlinAdapter;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class EventManager {

	private static ITextEditor textEditor;
	private static boolean saveToDatabase = true;
	private final GremlinAdapter adapter;

	public EventManager(GremlinAdapter adapter, EventBus eventBus) {
		this.adapter = adapter;
		eventBus.register(this);
		enableListeners();

	}

	public void setEditor(ITextEditor tEditor) {
		if (textEditor == null) {
			EventManager.textEditor = tEditor;
		}
	}

	public ITextEditor getEditor() {
		return EventManager.textEditor;
	}

	@Subscribe
	public void processEvent(IEvent event) {

		if (saveToDatabase) {
			adapter.insertVertex(event);
		}

	}

	public void enableListeners() {

		JavaCore.addElementChangedListener(new ClasspathChangeListener(), ElementChangedEvent.POST_CHANGE);

		subscribeResourceListener();
		subscribeWindowListener();
		subscribeEclipseCloseListener();
		subscribeLaunchListener();
	}

	private void subscribeEclipseCloseListener() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		workbench.addWorkbenchListener(new EclipseCloseEventListener());
	}

	private void subscribeLaunchListener() {

		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		manager.addLaunchListener(new LaunchEventListener());
	}

	private void subscribeWindowListener() {
		IWorkbench workbench = PlatformUI.getWorkbench();

		WindowEventListener windowListener = new WindowEventListener();
		windowListener.windowOpened(workbench.getWorkbenchWindows()[0]);

		workbench.addWindowListener(windowListener);

	}

	private void subscribeResourceListener() {
		ResourceEventListener listener = new ResourceEventListener();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(listener,
				IResourceChangeEvent.PRE_CLOSE | IResourceChangeEvent.PRE_DELETE | IResourceChangeEvent.PRE_BUILD | IResourceChangeEvent.POST_BUILD | IResourceChangeEvent.POST_CHANGE);
	}

}
