package org.eclipse.scava.crossflow.restmule.client.github.test.mde;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.scava.crossflow.restmule.client.github.api.IGitHubApi;
import org.eclipse.scava.crossflow.restmule.client.github.model.SearchCode;
import org.eclipse.scava.crossflow.restmule.client.github.util.GitHubUtils;
import org.eclipse.scava.crossflow.restmule.core.data.IDataSet;
import org.junit.BeforeClass;
import org.junit.Test;

public class MDEAnalysis extends GitHubUtils {

	private static final Logger LOG = LogManager.getLogger(MDEAnalysis.class);

	private static IGitHubApi localApi;

	@BeforeClass
	public static void prepareClass() {

		// clearGitHubCache();

		localApi = GitHubUtils.getOAuthClient();

		LOG.info("DAEMON? " + Thread.currentThread().isDaemon());
		// Thread.currentThread().setDaemon(!Thread.currentThread().isDaemon());
	}

	@Test
	public void testMDETech() throws InterruptedException, IOException {

		// output data store
		// repo / file / author / commit
		HashMap<String, HashMap<String, HashMap<String, HashSet<String>>>> out = new HashMap<>();

		for (MDE mde : MDE.values()) {
			String query = mde.query();
			LOG.info(query);
			IDataSet<SearchCode> searchCode = localApi.getSearchCode("asc", query, null);

			// Queues
			FileToRepo f2r = new FileToRepo();
			RepoToFile r2f = new RepoToFile(mde);
			FileToCommits f2c = new FileToCommits();

			// Subscriptions
			searchCode.observe().subscribe(f2r);
			f2r.repos().subscribe(r2f);
			r2f.files().subscribe(f2c);

			// Logging
			RepoAndFileDataConsumer rfdc = new RepoAndFileDataConsumer(out);
			CommitDataConsumer cdc = new CommitDataConsumer(out);

			r2f.files().subscribe(rfdc);
			f2c.commits().subscribe(cdc);

			// searchCode.observe().blockingSubscribe();

			// keep thread alive without forcing blocking etc.
			// Thread.sleep(10000);
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			String line = "";

			while (line.equalsIgnoreCase("quit") == false && line.equalsIgnoreCase("exit") == false
					&& line.equalsIgnoreCase("q") == false && line.equalsIgnoreCase("e") == false) {
				line = in.readLine();

				// do something
			}
			in.close();

			rfdc.dumpData();

			System.exit(0);
			// cdc.dumpData();

			// Initializing
			// f2r.repos().doOnNext(out -> LOG.info("repo:
			// "+out.getFullName()));
			// r2f.files().doOnNext(out -> LOG.info("file: "+out.getName()));

		}

	}
}
