/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.eclipse.handlers;

import javax.jws.soap.SOAPBinding.Use;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.scava.plugin.ui.metric.MetricDisplay;
import org.eclipse.scava.plugin.usermonitoring.UserMonitor;
import org.eclipse.scava.plugin.usermonitoring.metric.adapter.GremlinAdapter;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.MetricProvider;

public class MetricHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		new MetricDisplay(null);

		return null;
	}

}
