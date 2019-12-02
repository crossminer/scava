/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.communicationchannel.nntp.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class NntpDatum extends Pongo {
	
	
	
	public NntpDatum() { 
		super();
		OSSMERTERID.setOwningType("org.eclipse.scava.platform.communicationchannel.nntp.model.NntpDatum");
		LASTARTICLECHECKED.setOwningType("org.eclipse.scava.platform.communicationchannel.nntp.model.NntpDatum");
		DATE.setOwningType("org.eclipse.scava.platform.communicationchannel.nntp.model.NntpDatum");
	}
	
	public static StringQueryProducer OSSMERTERID = new StringQueryProducer("ossmerterId"); 
	public static NumericalQueryProducer LASTARTICLECHECKED = new NumericalQueryProducer("lastArticleChecked");
	public static StringQueryProducer DATE = new StringQueryProducer("date"); 
	
	
	public String getOssmerterId() {
		return parseString(dbObject.get("ossmerterId")+"", "");
	}
	
	public NntpDatum setOssmerterId(String ossmerterId) {
		dbObject.put("ossmerterId", ossmerterId);
		notifyChanged();
		return this;
	}
	public long getLastArticleChecked() {
		return parseLong(dbObject.get("lastArticleChecked")+"", 0);
	}
	
	public NntpDatum setLastArticleChecked(long lastArticleChecked) {
		dbObject.put("lastArticleChecked", lastArticleChecked);
		notifyChanged();
		return this;
	}
	public String getDate() {
		return parseString(dbObject.get("date")+"", "");
	}
	
	public NntpDatum setDate(String date) {
		dbObject.put("date", date);
		notifyChanged();
		return this;
	}
	
	
	
	
}