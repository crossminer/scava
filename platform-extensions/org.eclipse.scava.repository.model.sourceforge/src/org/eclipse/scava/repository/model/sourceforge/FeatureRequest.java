/*******************************************************************************
 * Copyright (c) 2017 aabherve
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model.sourceforge;

import com.googlecode.pongo.runtime.querying.*;


public class FeatureRequest extends Request {
	
	
	
	public FeatureRequest() { 
		super();
		super.setSuperTypes("org.eclipse.scava.repository.model.sourceforge.Request","org.eclipse.scava.repository.model.sourceforge.Tracker","org.eclipse.scava.repository.model.sourceforge.NamedElement");
		LOCATION.setOwningType("org.eclipse.scava.repository.model.sourceforge.FeatureRequest");
		STATUS.setOwningType("org.eclipse.scava.repository.model.sourceforge.FeatureRequest");
		SUMMARY.setOwningType("org.eclipse.scava.repository.model.sourceforge.FeatureRequest");
		CREATED_AT.setOwningType("org.eclipse.scava.repository.model.sourceforge.FeatureRequest");
		UPDATED_AT.setOwningType("org.eclipse.scava.repository.model.sourceforge.FeatureRequest");
	}
	
	public static StringQueryProducer LOCATION = new StringQueryProducer("location"); 
	public static StringQueryProducer STATUS = new StringQueryProducer("status"); 
	public static StringQueryProducer SUMMARY = new StringQueryProducer("summary"); 
	public static StringQueryProducer CREATED_AT = new StringQueryProducer("created_at"); 
	public static StringQueryProducer UPDATED_AT = new StringQueryProducer("updated_at"); 
	
	
	
	
	
	
}
