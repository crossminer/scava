package org.eclipse.scava.crossflow.runtime;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class DirectoryCache implements Cache {

	boolean verbose = false;

	protected HashMap<String, File> jobFolderMap = new HashMap<>();
	protected HashMap<String, Job> jobMap = new HashMap<>();

	protected File directory;
	protected Workflow<?> workflow = null;

	public DirectoryCache() {
		try {
			init(Files.createTempDirectory("crossflow").toFile());
		} catch (Exception ex) {
			workflow.reportInternalException(ex);
		}
	}

	public DirectoryCache(File directory) {
		init(directory);
	}

	protected void init(File directory) {
		this.directory = directory;
		if (!directory.exists())
			return;
		for (File streamFolder : directory.listFiles()) {
			if (!streamFolder.isDirectory())
				continue;
			for (File jobFolder : streamFolder.listFiles()) {
				if (!jobFolder.isDirectory())
					continue;
				jobFolderMap.put(jobFolder.getName(), jobFolder);
			}
		}
	}

	@Override
	public List<Job> getCachedOutputs(Job input) {
		if (hasCachedOutputs(input)) {
			ArrayList<Job> outputs = new ArrayList<>();
			File inputFolder = jobFolderMap.get(input.getHash());
			for (File outputFile : inputFolder.listFiles()) {
				Job output = (Job) workflow.getSerializer().toObject(outputFile);
				output.setJobId(UUID.randomUUID().toString());
				output.setCorrelationId(input.getJobId());
				output.setCached(true);
				outputs.add(output);
			}
			return outputs;
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public boolean hasCachedOutputs(Job input) {
		if (input == null)
			return !jobFolderMap.isEmpty();
		return jobFolderMap.containsKey(input.getHash());
	}

	@Override
	public synchronized void cache(Job output) {

		if (!output.isCacheable())
			return;

		jobMap.put(output.getJobId(), output);
		Job input = jobMap.get(output.getCorrelationId());

		if (input != null) {
			File streamFolder = new File(directory, input.getDestination());
			try {
				File inputFolder = new File(streamFolder, input.getHash());
				inputFolder.mkdirs();
				// File inputFile = new File(inputFolder, "job.xml");
				// if (!inputFile.exists()) save(input, inputFile);
				File outputFile = new File(inputFolder, output.getHash());
				jobFolderMap.put(input.getHash(), inputFolder);
				save(output, outputFile);
			} catch (Exception e) {
				workflow.reportInternalException(e);
			}
		}
	}

	public File getDirectory() {
		return directory;
	}

	protected void save(Job job, File file) throws Exception {
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(job.getXML().getBytes());
		fos.flush();
		fos.close();
	}

	@Override
	public void setWorkflow(Workflow<?> workflow) {
		this.workflow = workflow;
	}

	HashMap<String, LinkedList<Job>> pendingTransactions = new HashMap<>();

	@Override
	public synchronized void cacheTransactionally(Job output) {

		if (output.isTransactionSuccessMessage()) {
			cachePendingTransactions(output);
			return;
		}

		if (!output.isCacheable())
			return;

		// even though the task producing this job may have failed, this job itself is
		// complete so should be indexed in the job map regardless
		jobMap.put(output.getJobId(), output);
		//

		if (output.getCorrelationId() == null)
			return;

		LinkedList<Job> currentPending;
		if ((currentPending = pendingTransactions.get(output.getCorrelationId())) == null)
			currentPending = new LinkedList<>();
		currentPending.add(output);

		pendingTransactions.put(output.getCorrelationId(), currentPending);
		//

	}

	private HashMap<String, Integer> confirmations = new HashMap<>();

	private void cachePendingTransactions(Job output) {

		String corr = output.getCorrelationId();
		int total = output.getTotalOutputs();

		if (total == 1) {
			if (verbose)
				System.err
						.println("trying to commit transaction for " + corr + " and only 1 confirmation is necessary!");
			// continue, as only one confirmation is necessary
		} else if (total > 1) {
			if (!confirmations.containsKey(corr)) {
				if (verbose)
					System.err.println("trying to commit transaction for " + corr + " but only had 1 out of " + total
							+ " confirmations so far...");
				confirmations.put(corr, 1);
				return;
			} else if (confirmations.get(corr) != total) {
				int current = confirmations.get(corr);
				confirmations.put(corr, current + 1);
				if (confirmations.get(corr) != total) {
					if (verbose)
						System.err.println("trying to commit transaction for " + corr + " but only had " + (current + 1)
								+ " out of " + total + " confirmations so far...");
					return;
				} else {
					if (verbose)
						System.err.println("trying to commit transaction for " + corr + " and all " + total
								+ " confirmations have arrived!");
				}
			}
		} else {
			if (verbose)
				System.err.println("warning: cachePendingTransactions called with 0 output queues for :" + corr + "!");
			return;
		}

		if (verbose)
			System.err.println("committing transaction for " + corr);

		Job input = jobMap.get(corr);

		if (input != null) {
			File streamFolder = new File(directory, input.getDestination());
			try {
				LinkedList<Job> pending = pendingTransactions.get(corr);
				if (pending != null) {
					File inputFolder = new File(streamFolder, input.getHash());
					inputFolder.mkdirs();
					for (Job out : pending) {
						File outputFile = new File(inputFolder, out.getHash());
						jobFolderMap.put(input.getHash(), inputFolder);
						save(out, outputFile);
					}
				}
			} catch (Exception e) {
				System.out.println(corr);
				System.out.println(pendingTransactions);
				workflow.reportInternalException(e);
			}
		}

	}

	/**
	 * Clears the entire cache
	 */
	@Override
	public boolean clear() {
		return clear("");
	}

	/**
	 * Clears the cache for a specific queue (use the empty string for a global
	 * cache clear, or use the 0 parameter method clear())
	 */
	@Override
	public boolean clear(String stream) {
		// System.out.println(jobFolderMap);
		File streamFolder = stream.trim().length() == 0 ? directory : new File(directory, stream);
		if (!streamFolder.exists())
			return false;
		try {
			// clear cache either for a specific queue or globally
			if (stream.trim().length() == 0 && streamFolder.list().length > 0) {
				deleteDirectoryStream(streamFolder.toPath());
				jobFolderMap.clear();
			} else {
				File[] children = streamFolder.listFiles();
				List<File> childrenList = Arrays.asList(children);
				//
				deleteDirectoryStream(streamFolder.toPath());
				//
				jobFolderMap.values().removeAll(childrenList);
			}
			// re-make root directory if a global cache clear was called
			if (stream.trim().length() == 0)
				directory.mkdirs();
			//
			// System.out.println(jobFolderMap);
			return true;
		} catch (IOException e) {
			// e.printStackTrace();
			return false;
		}
	}

	void deleteDirectoryStream(Path path) throws IOException {
		Files.walk(path).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
	}

}
