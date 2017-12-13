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

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;

//protected region custom-imports on begin
//protected region custom-imports end


public class SourceForgeBugTrackingSystem extends org.eclipse.crossmeter.repository.model.BugTrackingSystem {
	
	protected List<BugTS> bugsTS = null;
	
	// protected region custom-fields-and-methods on begin
    @Override
    public String getBugTrackerType() {
        return "sourceforge";
    }

    @Override
    public String getInstanceId() {
        return getUrl();
    }

    // protected region custom-fields-and-methods end
	
	public SourceForgeBugTrackingSystem() { 
		super();
		dbObject.put("bugsTS", new BasicDBList());
		super.setSuperTypes("org.eclipse.crossmeter.repository.model.sourceforge.BugTrackingSystem");
	}
	
	
	
	
	
	public List<BugTS> getBugsTS() {
		if (bugsTS == null) {
			bugsTS = new PongoList<BugTS>(this, "bugsTS", true);
		}
		return bugsTS;
	}
	
	
}