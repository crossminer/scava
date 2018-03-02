/*******************************************************************************
 * Copyright (c) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model.github;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class GitHubContent extends Pongo {
	
	
	
	public GitHubContent() { 
		super();
		TYPE.setOwningType("org.eclipse.scava.repository.model.github.GitHubContent");
		ENVODING.setOwningType("org.eclipse.scava.repository.model.github.GitHubContent");
		SIZE.setOwningType("org.eclipse.scava.repository.model.github.GitHubContent");
		NAME.setOwningType("org.eclipse.scava.repository.model.github.GitHubContent");
		PATH.setOwningType("org.eclipse.scava.repository.model.github.GitHubContent");
		SHA.setOwningType("org.eclipse.scava.repository.model.github.GitHubContent");
	}
	
	public static StringQueryProducer TYPE = new StringQueryProducer("type"); 
	public static StringQueryProducer ENVODING = new StringQueryProducer("envoding"); 
	public static NumericalQueryProducer SIZE = new NumericalQueryProducer("size");
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer PATH = new StringQueryProducer("path"); 
	public static StringQueryProducer SHA = new StringQueryProducer("sha"); 
	
	
	public String getType() {
		return parseString(dbObject.get("type")+"", "");
	}
	
	public GitHubContent setType(String type) {
		dbObject.put("type", type);
		notifyChanged();
		return this;
	}
	public String getEnvoding() {
		return parseString(dbObject.get("envoding")+"", "");
	}
	
	public GitHubContent setEnvoding(String envoding) {
		dbObject.put("envoding", envoding);
		notifyChanged();
		return this;
	}
	public int getSize() {
		return parseInteger(dbObject.get("size")+"", 0);
	}
	
	public GitHubContent setSize(int size) {
		dbObject.put("size", size);
		notifyChanged();
		return this;
	}
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public GitHubContent setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	public String getPath() {
		return parseString(dbObject.get("path")+"", "");
	}
	
	public GitHubContent setPath(String path) {
		dbObject.put("path", path);
		notifyChanged();
		return this;
	}
	public String getSha() {
		return parseString(dbObject.get("sha")+"", "");
	}
	
	public GitHubContent setSha(String sha) {
		dbObject.put("sha", sha);
		notifyChanged();
		return this;
	}
	
	
	
	
}
