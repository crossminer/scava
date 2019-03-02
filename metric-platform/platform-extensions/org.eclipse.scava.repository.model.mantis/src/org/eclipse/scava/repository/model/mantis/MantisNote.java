/*******************************************************************************
 * Copyright (c) 2018 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model.mantis;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class MantisNote extends Pongo {
	
	protected List<MantisReporter> reporter = null;
	protected List<MantisViewState> view_state = null;
	
	
	public MantisNote() { 
		super();
		dbObject.put("reporter", new BasicDBList());
		dbObject.put("view_state", new BasicDBList());
		_ID.setOwningType("org.eclipse.scava.repository.model.mantis.MantisNote");
		TEXT.setOwningType("org.eclipse.scava.repository.model.mantis.MantisNote");
		TYPE.setOwningType("org.eclipse.scava.repository.model.mantis.MantisNote");
		CREATED_AT.setOwningType("org.eclipse.scava.repository.model.mantis.MantisNote");
		UPDATED_AT.setOwningType("org.eclipse.scava.repository.model.mantis.MantisNote");
	}
	
	public static StringQueryProducer _ID = new StringQueryProducer("_id"); 
	public static StringQueryProducer TEXT = new StringQueryProducer("text"); 
	public static StringQueryProducer TYPE = new StringQueryProducer("type"); 
	public static StringQueryProducer CREATED_AT = new StringQueryProducer("created_at"); 
	public static StringQueryProducer UPDATED_AT = new StringQueryProducer("updated_at"); 
	
	
	public String get_id() {
		return parseString(dbObject.get("_id")+"", "");
	}
	
	public MantisNote set_id(String _id) {
		dbObject.put("_id", _id);
		notifyChanged();
		return this;
	}
	public String getText() {
		return parseString(dbObject.get("text")+"", "");
	}
	
	public MantisNote setText(String text) {
		dbObject.put("text", text);
		notifyChanged();
		return this;
	}
	public String getType() {
		return parseString(dbObject.get("type")+"", "");
	}
	
	public MantisNote setType(String type) {
		dbObject.put("type", type);
		notifyChanged();
		return this;
	}
	public String getCreated_at() {
		return parseString(dbObject.get("created_at")+"", "");
	}
	
	public MantisNote setCreated_at(String created_at) {
		dbObject.put("created_at", created_at);
		notifyChanged();
		return this;
	}
	public String getUpdated_at() {
		return parseString(dbObject.get("updated_at")+"", "");
	}
	
	public MantisNote setUpdated_at(String updated_at) {
		dbObject.put("updated_at", updated_at);
		notifyChanged();
		return this;
	}
	
	
	public List<MantisReporter> getReporter() {
		if (reporter == null) {
			reporter = new PongoList<MantisReporter>(this, "reporter", true);
		}
		return reporter;
	}
	public List<MantisViewState> getView_state() {
		if (view_state == null) {
			view_state = new PongoList<MantisViewState>(this, "view_state", true);
		}
		return view_state;
	}
	
	
}