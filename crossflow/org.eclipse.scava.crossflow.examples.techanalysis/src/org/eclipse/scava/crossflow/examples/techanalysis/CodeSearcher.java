package org.eclipse.scava.crossflow.examples.techanalysis;

import java.util.List;

import org.eclipse.scava.crossflow.restmule.client.github.model.SearchCode;
import org.eclipse.scava.crossflow.restmule.client.github.query.CodeSearchQuery;
import org.eclipse.scava.crossflow.restmule.client.github.util.GitHubUtils;
import org.eclipse.scava.crossflow.restmule.core.data.IDataSet;

public class CodeSearcher extends CodeSearcherBase {

	@Override
	public void consumeTechnologies(Technology tech) throws Exception {

		search(tech);

	}

	private void search(Technology tech) {

		String query = new CodeSearchQuery().create(tech.getTechKey()).extension(tech.getFileExt()).inFile().build()
				.getQuery();

		IDataSet<SearchCode> ret = GitHubUtils.getOAuthClient().getSearchCode("asc", query, null);

		List<SearchCode> repoFiles = ret.observe().toList().blockingGet();

		// files in current repo
		for (SearchCode resultItem : repoFiles) {
			org.eclipse.scava.crossflow.restmule.client.github.model.SearchCode.Repository resultRepo = resultItem
					.getRepository();
			Repository repositoryInst = new Repository();
			repositoryInst.setUrl(resultRepo.getHtmlUrl());
			repositoryInst.setName(resultRepo.getFullName());

			sendToRepositories(repositoryInst);
		}

	}// search

}
