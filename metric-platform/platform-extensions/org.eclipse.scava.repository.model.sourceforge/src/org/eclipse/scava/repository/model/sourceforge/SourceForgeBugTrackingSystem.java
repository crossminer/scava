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

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;

//protected region custom-imports on begin
//protected region custom-imports end


public class SourceForgeBugTrackingSystem extends org.eclipse.scava.repository.model.BugTrackingSystem {
	
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
		super.setSuperTypes("org.eclipse.scava.repository.model.sourceforge.BugTrackingSystem");
	}
	
	
	
	
	
	public List<BugTS> getBugsTS() {
		if (bugsTS == null) {
			bugsTS = new PongoList<BugTS>(this, "bugsTS", true);
		}
		return bugsTS;
	}
	
	
}
