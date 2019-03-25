/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.github;

import java.io.Serializable;

import org.eclipse.scava.crossflow.restmule.client.github.model.PullRequest;

public class GitHubPullRequestMarker implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long repository;
    private String label;
    private String ref;
    private String sha;
    private String user;

    
    
	// ----------------------------------------------------------------------------------
	// Setters
	// ----------------------------------------------------------------------------------
	    public void setRepository(PullRequest pullRequest) {
	    	
	    	try{
	    		
	    		this.repository = pullRequest.getHead().getRepo().getId().longValue();
	    	}catch(NullPointerException np){
	    		
	    		
	    	}
	        
	    }
	    
	     public void setLabel(String label) {
	        this.label = label;
	    }
	    
	     public void setRef(String ref) {
	        this.ref = ref;
	    }
	    
	     public void setSha(String sha) {
	        this.sha = sha;
	    }
	    
	     public void setUser(String user) {
	         this.user = user;
	     }
    

	// ----------------------------------------------------------------------------------
	// Getters
	// ----------------------------------------------------------------------------------

    public Long getRepository() {
        return repository;
    }

   

    public String getLabel() {
        return label;
    }

   

    public String getRef() {
        return ref;
    }

   

    public String getSha() {
        return sha;
    }

   

    public String getUser() {
        return user;
    }



}
