/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.crossmeter.platform.util;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

public class ExtensionPointHelper {
	public static IConfigurationElement[] getConfigurationElementsForExtensionPoint(String extensionPointId) {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] configurationElements=null;
		
		IExtensionPoint extensionPoint = registry.getExtensionPoint(extensionPointId);
		configurationElements =  extensionPoint.getConfigurationElements();
		
		return configurationElements;
	}
}
