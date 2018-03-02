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

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.scava.platform.AbstractTransientMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.repository.model.Project;

import com.googlecode.pongo.runtime.PongoDB;
import com.mongodb.DB;

public class ErrorThrowingTransientMetricProvider extends AbstractTransientMetricProvider<PongoDB> {

	protected Logger logger;
	
	public ErrorThrowingTransientMetricProvider() {
		this.logger = OssmeterLogger.getLogger(getShortIdentifier());
	}
	
	@Override
	public String getShortIdentifier() {
		return "dummy";
	}

	@Override
	public String getFriendlyName() {
		return null;
	}

	@Override
	public String getSummaryInformation() {
		return null;
	}

	@Override
	public boolean appliesTo(Project project) {
		return true;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Collections.emptyList();
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		
	}

	@Override
	public PongoDB adapt(DB db) {
		return null;
	}

	@Override
	public void measure(Project project, ProjectDelta delta, PongoDB db) {
		int[] is = new int[1];
		is[5] = 4;
		logger.info("ErrorThrowingMetricProvider executed."); // Will never execute
	}

}
