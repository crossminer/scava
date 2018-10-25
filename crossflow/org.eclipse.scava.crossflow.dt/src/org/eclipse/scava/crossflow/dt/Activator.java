package org.eclipse.scava.crossflow.dt;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "CrossflowExecutionUI"; //$NON-NLS-1$
	// The shared instance
	private static Activator plugin;

	/**
	* The constructor
	*/
	public Activator() {
	}

	public void start(BundleContext context) throws Exception {
	plugin = this;
	}

	public void stop(BundleContext context) throws Exception {
	plugin = null;
	}

	public static Activator getDefault() {
	return plugin;
	}

}
