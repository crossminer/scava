/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.newsgroups.threads.model;

import java.util.Comparator;

public class ArticleDataComparator implements Comparator<ArticleData>{

	@Override
	public int compare(ArticleData articleA, ArticleData articleB) {
		if (!articleA.getNewsgroupName().equals(articleB.getNewsgroupName()))
			return articleA.getNewsgroupName().compareTo(articleB.getNewsgroupName());
		else 
			return articleA.getArticleNumber() - articleB.getArticleNumber();
	}

}
