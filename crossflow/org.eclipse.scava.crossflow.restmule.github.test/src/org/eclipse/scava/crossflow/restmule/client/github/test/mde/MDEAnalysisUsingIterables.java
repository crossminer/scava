package org.eclipse.scava.crossflow.restmule.client.github.test.mde;

import java.util.HashSet;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.scava.crossflow.restmule.client.github.api.IGitHubApi;
import org.eclipse.scava.crossflow.restmule.client.github.model.Commits;
import org.eclipse.scava.crossflow.restmule.client.github.model.SearchCode;
import org.eclipse.scava.crossflow.restmule.client.github.model.SearchCode.Repository;
import org.eclipse.scava.crossflow.restmule.client.github.test.query.CodeSearchQuery;
import org.eclipse.scava.crossflow.restmule.client.github.util.GitHubUtils;
import org.eclipse.scava.crossflow.restmule.core.data.IDataSet;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

public class MDEAnalysisUsingIterables extends GitHubUtils {

	private static final Logger logger = LogManager.getLogger(MDEAnalysisUsingIterables.class);

	private int totalCount = 0;
	private int totalCommitCount = 0;

	// (full repo name;file download url), github user email, number of commits
	// for [file download url, github user email] combination
	Table<String, String, Integer> resultTable = HashBasedTable.create();

	MDE currentMDE;

	public MDEAnalysisUsingIterables(MDE githubQuery) {

		currentMDE = githubQuery;

	}

	private static final Logger LOG = LogManager.getLogger(MDEAnalysis.class);

	private static CloseableHttpAsyncClient asyncClient;
	private static IGitHubApi localApi;

	public void runSearch() {
		try {

			// Cache cache = new Cache(new File("cache/"), 1000 * 1024 * 1024);
			// // 1GB cache
			// OkHttpClient client = new OkHttpClient().setCache(cache);
			// OkUrlFactory urlFactory = new OkUrlFactory(client);
			// HttpConnector connector = new ImpatientHttpConnector(new
			// OkHttpConnector(urlFactory),
			// DEFAULT_CONNECTION_TIMEOUT_IN_MILLI_SECONDS,
			// DEFAULT_READ_TIMEOUT_IN_MILLI_SECONDS);

			// GitHub github = new GitHubBuilder().withPassword(githubUserName,
			// githubUserPass).withConnector(connector).withAbuseLimitHandler(AbuseLimitHandler.WAIT).withRateLimitHandler(RateLimitHandler.WAIT).build();

			//

			localApi = GitHubUtils.getOAuthClient();
			asyncClient = HttpAsyncClients.createDefault();

			LOG.info("DAEMON? " + Thread.currentThread().isDaemon());
			
			String query = currentMDE.query();
			LOG.info(query);
			IDataSet<SearchCode> searchCode = localApi.getSearchCode("asc", query, null);

			List<SearchCode> searchCodeList = searchCode.observe().toList().blockingGet();

			totalCount = searchCodeList.size();
			logger.info("TOTAL RESULT COUNT: " + Integer.toString(totalCount));

			int currResNo = 1;

			HashSet<String> repoNameSet = new HashSet<>();
			// files
			for (SearchCode initialResultItem : searchCodeList) {
				Repository initialResultRepo = initialResultItem.getRepository();
				String initialResultItemFullRepoName = initialResultRepo.getFullName();

				if (!repoNameSet.contains(initialResultItemFullRepoName)) {

					String q = new CodeSearchQuery().create(currentMDE.getKeyword())
							.extension(currentMDE.getExtension()).repo(initialResultItemFullRepoName).build()
							.getQuery();
					// System.err.println(q);
					IDataSet<SearchCode> ret = GitHubUtils.getOAuthClient().getSearchCode("asc", q, null);

					List<SearchCode> repoFiles = ret.observe().toList().blockingGet();

					// files in current repo
					for (SearchCode resultItem : repoFiles) {

						Repository resultRepo = resultItem.getRepository();
						String resultItemPath = java.net.URLDecoder.decode(resultItem.getPath(), "UTF-8");
						String resultItemFullRepoName = resultRepo.getFullName();
						// String resultItemDownloadUrl =
						// java.net.URLDecoder.decode(resultItem.getDownloadUrl(),"UTF-8");

						// commits of file
						List<Commits> commits = GitHubUtils
								.getOAuthClient().getReposCommits(resultRepo.getOwner().getLogin(),
										resultRepo.getName(), null, null, resultItem.getPath(), null, null)
								.observe().toList().blockingGet();

						for (Commits commit : commits) {
							String authorEmail = commit.getCommit().getCommitterInner().getEmail();
							int count = 0;
							String tableKey = resultItemFullRepoName + ";" + commit.getCommit().getUrl();
							if (resultTable.get(tableKey, authorEmail) != null) {
								count = resultTable.get(tableKey, authorEmail);
							}
							resultTable.put(tableKey, authorEmail, count + 1);
							++totalCommitCount;
						} // commits of file

						logger.info("ADDED (" + currResNo + " of " + totalCount + "): " + resultItemFullRepoName);
						++currResNo;

					}

				} // files in current repo

			} // files end

			logger.info("TOTAL COMMIT COUNT: " + totalCommitCount);
			logger.info("=============== RESULT TABLE PRINTOUT ===============");
			logger.info(resultTable.toString());
			logger.info("=============== RESULT TABLE PRINTOUT (END) =========");

		} catch (Exception e1) {
			logger.info("Failed to connect to GitHub (bad credentials or timeout).");
			e1.printStackTrace();
		}
	}

}