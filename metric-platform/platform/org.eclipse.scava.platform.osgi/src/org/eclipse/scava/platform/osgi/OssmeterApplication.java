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
import java.util.Properties;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.scava.platform.Configuration;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.platform.osgi.api.ApiStartServiceToken;
import org.eclipse.scava.platform.osgi.services.TaskCheckExecutor;
import org.eclipse.scava.platform.osgi.services.WorkerExecutor;

import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
import com.mongodb.Mongo;

public class OssmeterApplication implements IApplication{
	
	private boolean slave = false;
	private boolean apiServer = false;
	
	private OssmeterLogger logger;
	private boolean done = false;
	private Object appLock = new Object();
	
	private Mongo mongo;
	private Properties prop;
	
	
	private Thread analysis;
	private Thread checker;
	
	public OssmeterApplication() {

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
		
		if (slave) {
			WorkerExecutor workerExecutor = new WorkerExecutor(mongo);
			Thread analysis = new Thread(workerExecutor);
			analysis.start();
				
			TaskCheckExecutor checkerExecutor = new TaskCheckExecutor(mongo);
			Thread checker = new Thread(checkerExecutor);
			checker.start();		
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
			}else if ("-slave".equals(args[i])) { 
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
			mongo.close();
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


}
