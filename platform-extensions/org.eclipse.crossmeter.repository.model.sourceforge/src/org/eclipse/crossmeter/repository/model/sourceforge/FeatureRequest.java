/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Davide Di Ruscio - Implementation,
 *    Juri Di Rocco - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.repository.model.sourceforge;

import com.googlecode.pongo.runtime.querying.*;


public class FeatureRequest extends Request {
	
	
	
	public FeatureRequest() { 
		super();
		super.setSuperTypes("org.eclipse.crossmeter.repository.model.sourceforge.Request","org.eclipse.crossmeter.repository.model.sourceforge.Tracker","org.eclipse.crossmeter.repository.model.sourceforge.NamedElement");
		LOCATION.setOwningType("org.eclipse.crossmeter.repository.model.sourceforge.FeatureRequest");
		STATUS.setOwningType("org.eclipse.crossmeter.repository.model.sourceforge.FeatureRequest");
		SUMMARY.setOwningType("org.eclipse.crossmeter.repository.model.sourceforge.FeatureRequest");
		CREATED_AT.setOwningType("org.eclipse.crossmeter.repository.model.sourceforge.FeatureRequest");
		UPDATED_AT.setOwningType("org.eclipse.crossmeter.repository.model.sourceforge.FeatureRequest");
	}
	
	public static StringQueryProducer LOCATION = new StringQueryProducer("location"); 
	public static StringQueryProducer STATUS = new StringQueryProducer("status"); 
	public static StringQueryProducer SUMMARY = new StringQueryProducer("summary"); 
	public static StringQueryProducer CREATED_AT = new StringQueryProducer("created_at"); 
	public static StringQueryProducer UPDATED_AT = new StringQueryProducer("updated_at"); 
	
	
	
	
	
	
}