/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.communicationchannel.sourceforge.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SourceForgeTopicSearch {
    private List<String> topicIds = new ArrayList<String>();
    private int count;

    public void addTopicId(String topicId) {
    	topicIds.add(topicId);
    }

    public List<String> getTopicIds() {
        return Collections.unmodifiableList(topicIds);
    }
    
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
