/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.client.api;

import org.eclipse.scava.platform.osgi.api.ApiStartServiceToken;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Server;
import org.restlet.data.Protocol;

public class Activator implements BundleActivator {

    private Component component;

    public void start(BundleContext context) throws Exception {
		context.addServiceListener(new ServiceListener() {
					
			@Override
			public void serviceChanged(ServiceEvent event) {
				if (event.getType() == ServiceEvent.REGISTERED){
			    	component = new Component();
			    	
			    	Server server = new Server(Protocol.HTTP, 8182);
			    	component.getServers().add(server);
			    	
			    	final Application app = new ApiApplication();
			    	component.getDefaultHost().attachDefault(app);
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
        if (component != null && component.isStarted()) component.stop();
    }

}
