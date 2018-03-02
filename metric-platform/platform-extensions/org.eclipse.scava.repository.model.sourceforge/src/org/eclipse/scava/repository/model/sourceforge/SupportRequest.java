/*******************************************************************************
 * Copyright (c) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model.sourceforge;

import com.googlecode.pongo.runtime.querying.*;


public class SupportRequest extends Request {
	
	
	
	public SupportRequest() { 
		super();
		super.setSuperTypes("org.eclipse.scava.repository.model.sourceforge.Request","org.eclipse.scava.repository.model.sourceforge.Tracker","org.eclipse.scava.repository.model.sourceforge.NamedElement");
		LOCATION.setOwningType("org.eclipse.scava.repository.model.sourceforge.SupportRequest");
		STATUS.setOwningType("org.eclipse.scava.repository.model.sourceforge.SupportRequest");
		SUMMARY.setOwningType("org.eclipse.scava.repository.model.sourceforge.SupportRequest");
		CREATED_AT.setOwningType("org.eclipse.scava.repository.model.sourceforge.SupportRequest");
		UPDATED_AT.setOwningType("org.eclipse.scava.repository.model.sourceforge.SupportRequest");
	}
	
	public static StringQueryProducer LOCATION = new StringQueryProducer("location"); 
	public static StringQueryProducer STATUS = new StringQueryProducer("status"); 
	public static StringQueryProducer SUMMARY = new StringQueryProducer("summary"); 
	public static StringQueryProducer CREATED_AT = new StringQueryProducer("created_at"); 
	public static StringQueryProducer UPDATED_AT = new StringQueryProducer("updated_at"); 
	
	
	
	
	
	
}
