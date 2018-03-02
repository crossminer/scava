/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.osgi;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.eclipse.scava.platform.Configuration;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.platform.osgi.executors.SlaveScheduler;
import org.eclipse.scava.platform.osgi.services.ApiStartServiceToken;
import org.eclipse.scava.platform.osgi.services.IWorkerService;
import org.eclipse.scava.platform.osgi.services.MasterService;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
import com.mongodb.Mongo;

public class OssmeterApplication implements IApplication, ServiceTrackerCustomizer<IWorkerService, IWorkerService> {
	
	protected boolean slave = false;
	protected boolean apiServer = false;
	protected boolean master = false; 
	
	protected OssmeterLogger logger;
	protected boolean done = false;
	protected Object appLock = new Object();
	
	protected Mongo mongo;
	protected Properties prop;
	
	protected ServiceTracker<IWorkerService, IWorkerService> workerServiceTracker;
	protected ServiceRegistration<IWorkerService> workerRegistration;
	
	protected List<IWorkerService> workers;
	private MasterService masterService;
	
	public OssmeterApplication() {
		workers = new ArrayList<IWorkerService>();
	}
	
	@Override
	public Object start(IApplicationContext context) throws Exception {
		// Setup platform
		processArguments(context);
		
		prop = new Properties();
		prop.load(this.getClass().getResourceAsStream("/config/log4j.properties"));
		
		logger = (OssmeterLogger)OssmeterLogger.getLogger("OssmeterApplication");
		logger.info("Application initialising.");

		// Connect to Mongo - single instance per node
		mongo = Configuration.getInstance().getMongoConnection();
		
		// Ensure OSGi contributors are active
		PongoFactory.getInstance().getContributors().add(new OsgiPongoFactoryContributor());
		
		// If master, start
		if (master) {
			masterService = new MasterService(workers);
			masterService.start();
		}

		if (slave) {
			SlaveScheduler slave = new SlaveScheduler(mongo);
			slave.run();
		}
		
		// Start web servers
		if (apiServer) {
			Activator.getContext().registerService(ApiStartServiceToken.class, new ApiStartServiceToken(), null);
		}

		// Now, rest.
  		waitForDone();
		return IApplication.EXIT_OK;
	}

	protected void processArguments(IApplicationContext context) {
		String[] args = (String[])context.getArguments().get("application.args");
		if (args == null) return;
		
		for (int i = 0; i < args.length; i++) {
			if ("-ossmeterConfig".equals(args[i])) {
				Properties configuration = new Properties();
				try {
					configuration.load(new FileReader(args[i+1]));
				} catch (Exception e) {
					logger.error("Unable to read the specified platform configuration file. Using defaults.", e);
				}
				// Update the configuraiton instance
				Configuration.getInstance().setConfigurationProperties(configuration);
				
				// Ensure maven is configured
				if (System.getProperty("MAVEN_EXECUTABLE") == null) {
					String mvn = configuration.getProperty(Configuration.MAVEN_EXECUTABLE, "/Applications/apache-maven-3.2.3/bin/mvn");
					System.setProperty("MAVEN_EXECUTABLE", mvn);
				}
				
				i++;
			} else if ("-master".equals(args[i])) { 
				master = true;
			} else if ("-slave".equals(args[i])) { 
				slave = true;
			} else if ("-apiServer".equals(args[i])) { 
				apiServer = true;
			}
		}
	}

	@Override
	public void stop() {
		synchronized (appLock) {
			done = true;
			appLock.notifyAll();
			
			// Clean up
			if (master && masterService != null) masterService.shutdown();
			mongo.close();
			workerRegistration.unregister();
			workerServiceTracker.close();
		}	
	}
	
	private void waitForDone() {
		// then just wait here
		synchronized (appLock) {
			while (!done) {
				try {
					appLock.wait();
				} catch (InterruptedException e) {
					// do nothing
				}
			}
		}
	}

	@Override
	public IWorkerService addingService(ServiceReference<IWorkerService> reference) {
		IWorkerService worker = Activator.getContext().getService(reference);
		workers.add(worker);
		return worker;
	}

	@Override
	public void modifiedService(ServiceReference<IWorkerService> reference, IWorkerService service) {
		
		
	}

	@Override
	public void removedService(ServiceReference<IWorkerService> reference, IWorkerService service) {
		workers.remove(service);
	}
}
