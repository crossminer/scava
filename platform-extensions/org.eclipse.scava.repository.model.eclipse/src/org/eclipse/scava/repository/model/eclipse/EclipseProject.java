/*******************************************************************************
 * Copyright (c) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model.eclipse;

import java.util.List;

import com.googlecode.pongo.runtime.PongoList;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;
import com.mongodb.BasicDBList;


public class EclipseProject extends org.eclipse.scava.repository.model.Project {
	
	protected List<EclipsePlatform> platforms = null;
	protected List<Review> reviews = null;
	protected List<Article> articles = null;
	protected List<Release> releases = null;
	
	
	public EclipseProject() { 
		super();
		dbObject.put("platforms", new BasicDBList());
		dbObject.put("reviews", new BasicDBList());
		dbObject.put("articles", new BasicDBList());
		dbObject.put("releases", new BasicDBList());
		super.setSuperTypes("org.eclipse.scava.repository.model.eclipse.Project");
		PARAGRAPHURL.setOwningType("org.eclipse.scava.repository.model.eclipse.EclipseProject");
		DESCRIPTIONURL.setOwningType("org.eclipse.scava.repository.model.eclipse.EclipseProject");
		DOWNLOADSURL.setOwningType("org.eclipse.scava.repository.model.eclipse.EclipseProject");
		PROJECTPLANURL.setOwningType("org.eclipse.scava.repository.model.eclipse.EclipseProject");
		UPDATESITEURL.setOwningType("org.eclipse.scava.repository.model.eclipse.EclipseProject");
		STATE.setOwningType("org.eclipse.scava.repository.model.eclipse.EclipseProject");
	}
	
	public static StringQueryProducer PARAGRAPHURL = new StringQueryProducer("paragraphUrl"); 
	public static StringQueryProducer DESCRIPTIONURL = new StringQueryProducer("descriptionUrl"); 
	public static StringQueryProducer DOWNLOADSURL = new StringQueryProducer("downloadsUrl"); 
	public static StringQueryProducer PROJECTPLANURL = new StringQueryProducer("projectplanUrl"); 
	public static StringQueryProducer UPDATESITEURL = new StringQueryProducer("updatesiteUrl"); 
	public static StringQueryProducer STATE = new StringQueryProducer("state"); 
	
	
	public String getParagraphUrl() {
		return parseString(dbObject.get("paragraphUrl")+"", "");
	}
	
	public EclipseProject setParagraphUrl(String paragraphUrl) {
		dbObject.put("paragraphUrl", paragraphUrl);
		notifyChanged();
		return this;
	}
	public String getDescriptionUrl() {
		return parseString(dbObject.get("descriptionUrl")+"", "");
	}
	
	public EclipseProject setDescriptionUrl(String descriptionUrl) {
		dbObject.put("descriptionUrl", descriptionUrl);
		notifyChanged();
		return this;
	}
	public String getDownloadsUrl() {
		return parseString(dbObject.get("downloadsUrl")+"", "");
	}
	
	public EclipseProject setDownloadsUrl(String downloadsUrl) {
		dbObject.put("downloadsUrl", downloadsUrl);
		notifyChanged();
		return this;
	}
	public String getProjectplanUrl() {
		return parseString(dbObject.get("projectplanUrl")+"", "");
	}
	
	public EclipseProject setProjectplanUrl(String projectplanUrl) {
		dbObject.put("projectplanUrl", projectplanUrl);
		notifyChanged();
		return this;
	}
	public String getUpdatesiteUrl() {
		return parseString(dbObject.get("updatesiteUrl")+"", "");
	}
	
	public EclipseProject setUpdatesiteUrl(String updatesiteUrl) {
		dbObject.put("updatesiteUrl", updatesiteUrl);
		notifyChanged();
		return this;
	}
	public String getState() {
		return parseString(dbObject.get("state")+"", "");
	}
	
	public EclipseProject setState(String state) {
		dbObject.put("state", state);
		notifyChanged();
		return this;
	}
	
	
	public List<EclipsePlatform> getPlatforms() {
		if (platforms == null) {
			platforms = new PongoList<EclipsePlatform>(this, "platforms", true);
		}
		return platforms;
	}
	public List<Review> getReviews() {
		if (reviews == null) {
			reviews = new PongoList<Review>(this, "reviews", true);
		}
		return reviews;
	}
	public List<Article> getArticles() {
		if (articles == null) {
			articles = new PongoList<Article>(this, "articles", true);
		}
		return articles;
	}
	public List<Release> getReleases() {
		if (releases == null) {
			releases = new PongoList<Release>(this, "releases", true);
		}
		return releases;
	}
	
	
}
