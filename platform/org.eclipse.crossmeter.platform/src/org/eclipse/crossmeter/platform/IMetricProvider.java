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
package org.eclipse.crossmeter.platform;

import java.util.List;

import org.eclipse.crossmeter.repository.model.Project;

public interface IMetricProvider {
	
	/**
	 * Unique identifier of the metric provider. Usually the fully qualified name
	 * of the class.
	 * @return
	 */
	public String getIdentifier();

	/**
	 * Simple identifier string
	 * @return
	 */
	public String getShortIdentifier();
	
	/**
	 * 
	 * @return A print-friendly name of the metric provider.
	 */
	public String getFriendlyName();
	
	/**
	 * Provides a short summary of the metric provider.
	 * @return
	 */
	public String getSummaryInformation();
	
	/**
	 * Returns true if this metric provider is applicable to the given Project.
	 * @param project
	 * @return
	 */
	public boolean appliesTo(Project project);
	
	/**
	 * Presents the MP with the set of MPs that it depends on
	 * for its execution.
	 * 
	 * If this MP is *provided* information by other MPs then
	 * they will also be assigned here.
	 * @param uses
	 */
	public void setUses(List<IMetricProvider> uses);
	
	/**
	 * Specifies the list of MPs that this MP depends on for
	 * its execution.
	 * @return
	 */
	public List<String> getIdentifiersOfUses();
	
	/**
	 * Provides the MP with contextual information that it can choose
	 * to use or ignore for its execution.
	 */
	public void setMetricProviderContext(MetricProviderContext context);
}