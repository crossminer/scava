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
import java.util.List;

public class SourceForgeTopic {

	private String topicId;
    private String discussionId;
    private String subject;

    private String internalId;
    
    List<SourceForgeArticle> articles;
    
    public SourceForgeTopic() {
    	super();
    	articles = new ArrayList<SourceForgeArticle>();
    }
    
    public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public String getDiscussionId() {
		return discussionId;
	}

	public void setDiscussionId(String discussionId) {
		this.discussionId = discussionId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }

    public List<SourceForgeArticle> getArticles() {
        return articles;
    }

	public void setForumId(int forumId) {
		for (SourceForgeArticle article: articles) {
			article.setForumId(forumId);
		}
	}

}
