/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*    Zsolt János Szamosvölgyi
*    Endre Tamás Váradi
*    Gergõ Balogh
**********************************************************************/
package org.eclipse.scava.plugin.usermonitoring.event;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.scava.plugin.properties.Properties;
import org.eclipse.scava.plugin.ui.errorhandler.ErrorHandler;
import org.eclipse.scava.plugin.usermonitoring.ErrorType;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.MetricException;
import org.eclipse.swt.widgets.Display;

public interface IProjectRelated {

	default void isProjectUnderObservation(ICompilationUnit unit) throws MetricException {

		String projectId = null;
		try {
			projectId = unit.getJavaProject().getProject().getPersistentProperty(Properties.PROJECT_GITHUB_URL);
		} catch (CoreException e) {
			e.printStackTrace();
			ErrorHandler.handle(Display.getDefault().getActiveShell(), e, ErrorType.ECLIPSE_PROPERTY_ERROR);
		}

		if (projectId == null || projectId == "") {
			throw new MetricException();
		}
	}

	default void isProjectUnderObservation(IJavaProject project) throws MetricException {
		String projectId = null;
		try {
			projectId = project.getProject().getPersistentProperty(Properties.PROJECT_GITHUB_URL);
		} catch (CoreException e) {
			e.printStackTrace();
			ErrorHandler.handle(Display.getDefault().getActiveShell(), e, ErrorType.ECLIPSE_PROPERTY_ERROR);
		}

		if (projectId == null || projectId == "") {
			throw new MetricException();
		}

	}

}
