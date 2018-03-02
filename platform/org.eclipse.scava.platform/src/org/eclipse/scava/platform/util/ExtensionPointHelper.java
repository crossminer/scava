/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.util;

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
