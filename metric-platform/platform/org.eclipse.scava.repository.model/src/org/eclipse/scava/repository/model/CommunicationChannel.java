/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public abstract class CommunicationChannel extends Pongo {
	
	protected List<Person> persons = null;
	
	/**
     * @return a string unique to each subclass of Communication Channels (e.g.
     *         "nntp", "IRC", "eclipse Forums")
     */
    public abstract String getCommunicationChannelType();
    
    /**
     * @return a string uniquely identifying an instance of a Communication Channel
     *         (e.g. for Eclipse Forums, this could be 'Forum Name/Forum ID')
     */
    public abstract String getInstanceId();
    
    @Override
    protected void notifyChanged() {
        dbObject.put("_ossmeterId", getInstanceId() + ':' + getCommunicationChannelType());

        super.notifyChanged();
    }
    
    /**
     * @return a string uniquely identifying a Communication Channel instance within
     *         the Scava environment
     */
    public String getOSSMeterId() {
        return parseString(dbObject.get("_ossmeterId") + "", null);
    }
	
	public CommunicationChannel() { 
		super();
		dbObject.put("persons", new BasicDBList());
		URL.setOwningType("org.eclipse.scava.repository.model.CommunicationChannel");
		NONPROCESSABLE.setOwningType("org.eclipse.scava.repository.model.CommunicationChannel");
	}
	
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	public static StringQueryProducer NONPROCESSABLE = new StringQueryProducer("nonProcessable"); 
	
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public CommunicationChannel setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	public boolean getNonProcessable() {
		return parseBoolean(dbObject.get("nonProcessable")+"", false);
	}
	
	public CommunicationChannel setNonProcessable(boolean nonProcessable) {
		dbObject.put("nonProcessable", nonProcessable);
		notifyChanged();
		return this;
	}
	
	
	public List<Person> getPersons() {
		if (persons == null) {
			persons = new PongoList<Person>(this, "persons", false);
		}
		return persons;
	}
	
	
}
