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


public class Bug extends Tracker {
	
	
	
	public Bug() { 
		super();
		super.setSuperTypes("org.eclipse.crossmeter.repository.model.sourceforge.Tracker","org.eclipse.crossmeter.repository.model.sourceforge.NamedElement");
		LOCATION.setOwningType("org.eclipse.crossmeter.repository.model.sourceforge.Bug");
		STATUS.setOwningType("org.eclipse.crossmeter.repository.model.sourceforge.Bug");
	}
	
	public static StringQueryProducer LOCATION = new StringQueryProducer("location"); 
	public static StringQueryProducer STATUS = new StringQueryProducer("status"); 
	
	
	
	
	
	
}