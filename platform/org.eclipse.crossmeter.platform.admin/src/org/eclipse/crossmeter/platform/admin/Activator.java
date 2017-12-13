/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    James Williams - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.platform.admin;

import org.eclipse.crossmeter.platform.Configuration;
import org.eclipse.crossmeter.platform.osgi.services.ApiStartServiceToken;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Protocol;
import org.restlet.security.ChallengeAuthenticator;
import org.restlet.security.MapVerifier;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator implements BundleActivator{

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.crossmeter.platform.admin"; //$NON-NLS-1$

	private Component component;
	
	public void start(BundleContext context) throws Exception {
		
		System.err.println("Starting Admin bundle");
		
		context.addServiceListener(new ServiceListener() {
			
			@Override
			public void serviceChanged(ServiceEvent event) {
				System.err.println(event);
				if (event.getType() == ServiceEvent.REGISTERED){
					Application application = new AdminApplication();

					component = new Component();
					component.getServers().add(Protocol.HTTP, 8183);
					component.getClients().add(Protocol.FILE);

					boolean useAuth = Boolean.valueOf(Configuration.getInstance().getProperty("adminapi.use_authentication", "false"));
					
					if (useAuth) {
						String username = Configuration.getInstance().getProperty("adminapi.username", null);
						String password = Configuration.getInstance().getProperty("adminapi.password", null);
						
						ChallengeAuthenticator guard = new ChallengeAuthenticator(null, ChallengeScheme.HTTP_BASIC, "myRealm");
						MapVerifier verifier = new MapVerifier();
						verifier.getLocalSecrets().put(username, password.toCharArray());
						guard.setVerifier(verifier);
						guard.setNext(application);
						
						component.getDefaultHost().attachDefault(guard);
					} else {
						component.getDefaultHost().attachDefault(application);
					}
					
					try {
						component.start();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}, "(objectclass=" + ApiStartServiceToken.class.getName() +")");
	}

	public void stop(BundleContext context) throws Exception {
		if (component != null)
			component.stop();
	}

}
