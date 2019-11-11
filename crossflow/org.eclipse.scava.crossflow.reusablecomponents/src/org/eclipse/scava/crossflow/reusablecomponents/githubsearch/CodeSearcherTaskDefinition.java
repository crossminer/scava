package org.eclipse.scava.crossflow.reusablecomponents.githubsearch;

import java.util.List;

import org.eclipse.scava.crossflow.restmule.client.github.model.SearchCode;
import org.eclipse.scava.crossflow.restmule.client.github.query.CodeSearchQuery;
import org.eclipse.scava.crossflow.restmule.client.github.util.GitHubUtils;
import org.eclipse.scava.crossflow.restmule.core.data.IDataSet;

/**
 * 
 * Consumes elements (from a single incoming queue) under the assumption that
 * they extend the Technology data type, containing the following attributes:
 * 
 * techKey : String, fileExt : String,
 * 
 * then for each such element outputs a corresponding element (to a single
 * outgoing queue), extending the data type Repository, containing the following
 * attributes:
 * 
 * fullName : String, htmlUrl : String
 */
public abstract class CodeSearcherTaskDefinition {

	public void consumeInputQueue(Technology tech) throws Exception {
		
		String query = new CodeSearchQuery().create(tech.getTechKey()).extension(tech.getFileExt()).inFile().build()
				.getQuery();
		
		IDataSet<SearchCode> ret = GitHubUtils.getOAuthClient().getSearchCode("asc", query, null);
		
		List<SearchCode> repoFiles = ret.observe().toList().blockingGet();

		System.out.println("found " + repoFiles.size() + " repos, returning the first one only! (...testing)");

		for (SearchCode resultItem : repoFiles) {
			SearchCode.Repository resultRepo = resultItem.getRepository();

			Repository repositoryInst = new Repository();
			repositoryInst.setHtmlUrl(resultRepo.getHtmlUrl());
			repositoryInst.setFullName(resultRepo.getFullName());

			sendToOutputQueue(repositoryInst);
			//
			break;
		}

	}

	public abstract void sendToOutputQueue(Repository repo) throws Exception;


}
