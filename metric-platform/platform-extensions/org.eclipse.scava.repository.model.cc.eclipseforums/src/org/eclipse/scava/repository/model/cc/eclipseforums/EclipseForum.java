/*******************************************************************************
 * Copyright (c) 2018 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model.cc.eclipseforums;

import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class EclipseForum extends org.eclipse.scava.repository.model.CommunicationChannel {
	
	
	
	public EclipseForum() { 
		super();
		super.setSuperTypes("org.eclipse.scava.repository.model.cc.eclipseforums.CommunicationChannel");
		CLIENT_ID.setOwningType("org.eclipse.scava.repository.model.cc.eclipseforums.EclipseForum");
		CLIENT_SECRET.setOwningType("org.eclipse.scava.repository.model.cc.eclipseforums.EclipseForum");
		FORUM_ID.setOwningType("org.eclipse.scava.repository.model.cc.eclipseforums.EclipseForum");
		FORUM_NAME.setOwningType("org.eclipse.scava.repository.model.cc.eclipseforums.EclipseForum");
	}
	
	public static StringQueryProducer CLIENT_ID = new StringQueryProducer("client_id"); 
	public static StringQueryProducer CLIENT_SECRET = new StringQueryProducer("client_secret"); 
	public static StringQueryProducer FORUM_ID = new StringQueryProducer("forum_id"); 
	public static StringQueryProducer FORUM_NAME = new StringQueryProducer("forum_name"); 
	
	public String getClient_id() {
		return parseString(dbObject.get("client_id")+"", "");
	}
	
	public EclipseForum setClient_id(String client_id) {
		dbObject.put("client_id", client_id);
		notifyChanged();
		return this;
	}
	public String getClient_secret() {
		return parseString(dbObject.get("client_secret")+"", "");
	}
	
	public EclipseForum setClient_secret(String client_secret) {
		dbObject.put("client_secret", client_secret);
		notifyChanged();
		return this;
	}
	public String getForum_id() {
		return parseString(dbObject.get("forum_id")+"", "");
	}
	
	public EclipseForum setForum_id(String forum_id) {
		dbObject.put("forum_id", forum_id);
		notifyChanged();
		return this;
	}
	public String getForum_name() {
		return parseString(dbObject.get("forum_name")+"", "");
	}
	
	public EclipseForum setForum_name(String forum_name) {
		dbObject.put("forum_name", forum_name);
		notifyChanged();
		return this;
	}

	@Override
	public String getCommunicationChannelType() {
		return "EclipseForums";
	}

	@Override
	public String getInstanceId() {
		return getForum_name() + "/" + getForum_id();
	}
	
	
	
	
}