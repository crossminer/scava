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


public class GitHubDownload extends Pongo {
	
	
	
	public GitHubDownload() { 
		super();
		_ID.setOwningType("org.eclipse.scava.repository.model.github.GitHubDownload");
		URL.setOwningType("org.eclipse.scava.repository.model.github.GitHubDownload");
		HTML_URL.setOwningType("org.eclipse.scava.repository.model.github.GitHubDownload");
		NAME.setOwningType("org.eclipse.scava.repository.model.github.GitHubDownload");
		DESCRIPTION.setOwningType("org.eclipse.scava.repository.model.github.GitHubDownload");
		SIZE.setOwningType("org.eclipse.scava.repository.model.github.GitHubDownload");
		DOWNLOAD_COUNT.setOwningType("org.eclipse.scava.repository.model.github.GitHubDownload");
		CONTENT_TYPE.setOwningType("org.eclipse.scava.repository.model.github.GitHubDownload");
	}
	
	public static NumericalQueryProducer _ID = new NumericalQueryProducer("_id");
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	public static StringQueryProducer HTML_URL = new StringQueryProducer("html_url"); 
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer DESCRIPTION = new StringQueryProducer("description"); 
	public static NumericalQueryProducer SIZE = new NumericalQueryProducer("size");
	public static NumericalQueryProducer DOWNLOAD_COUNT = new NumericalQueryProducer("download_count");
	public static StringQueryProducer CONTENT_TYPE = new StringQueryProducer("content_type"); 
	
	
	public int get_id() {
		return parseInteger(dbObject.get("_id")+"", 0);
	}
	
	public GitHubDownload set_id(int _id) {
		dbObject.put("_id", _id);
		notifyChanged();
		return this;
	}
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public GitHubDownload setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	public String getHtml_url() {
		return parseString(dbObject.get("html_url")+"", "");
	}
	
	public GitHubDownload setHtml_url(String html_url) {
		dbObject.put("html_url", html_url);
		notifyChanged();
		return this;
	}
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public GitHubDownload setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	public String getDescription() {
		return parseString(dbObject.get("description")+"", "");
	}
	
	public GitHubDownload setDescription(String description) {
		dbObject.put("description", description);
		notifyChanged();
		return this;
	}
	public int getSize() {
		return parseInteger(dbObject.get("size")+"", 0);
	}
	
	public GitHubDownload setSize(int size) {
		dbObject.put("size", size);
		notifyChanged();
		return this;
	}
	public int getDownload_count() {
		return parseInteger(dbObject.get("download_count")+"", 0);
	}
	
	public GitHubDownload setDownload_count(int download_count) {
		dbObject.put("download_count", download_count);
		notifyChanged();
		return this;
	}
	public String getContent_type() {
		return parseString(dbObject.get("content_type")+"", "");
	}
	
	public GitHubDownload setContent_type(String content_type) {
		dbObject.put("content_type", content_type);
		notifyChanged();
		return this;
	}
	
	
	
	
}
