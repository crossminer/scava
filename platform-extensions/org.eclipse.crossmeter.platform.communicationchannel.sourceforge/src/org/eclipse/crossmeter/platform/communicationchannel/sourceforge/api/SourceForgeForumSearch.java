/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Yannis Korkontzelos - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.platform.communicationchannel.sourceforge.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SourceForgeForumSearch {
    private List<Integer> forumIds = new ArrayList<Integer>();
    private int count;

    public void addForumId(int forumId) {
    	forumIds.add(forumId);
    }

    public List<Integer> getForumIds() {
        return Collections.unmodifiableList(forumIds);
    }
    
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
