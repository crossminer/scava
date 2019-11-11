/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.properties;

import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.scava.plugin.Activator;

public class Properties {
	public static final QualifiedName LIBRARY_UPDATE_VERSION_RULES = new QualifiedName(Activator.PLUGIN_ID,
			"LIBRARY_UPDATE_VERSION_RULES");
	public static final QualifiedName LIBRARY_UPDATE_API_MIGRATION_JAR_PATH = new QualifiedName(Activator.PLUGIN_ID,
			"LIBRARY_UPDATE_API_MIGRATION_JAR_PATH");
	public static final QualifiedName PROJECT_GITHUB_URL = new QualifiedName(Activator.PLUGIN_ID, "PROJECT_GITHUB_URL");
}
