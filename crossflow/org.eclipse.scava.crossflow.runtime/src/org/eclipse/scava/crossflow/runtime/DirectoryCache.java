package org.eclipse.scava.crossflow.runtime;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class DirectoryCache implements Cache {
	
	protected HashMap<String, File> jobFolderMap = new HashMap<String, File>();
	protected HashMap<String, Job> jobMap = new HashMap<String, Job>();
	
	protected File directory;
	protected Workflow workflow = null;
	protected XStream xstream = new XStream(new DomDriver());
	
	public DirectoryCache() {
		try {
			init(Files.createTempDirectory("crossflow").toFile());
		}
		catch (Exception ex) {
			workflow.reportInternalException(ex);
		}
	}
	
	public DirectoryCache(File directory) {
		init(directory);
	}
	
	protected void init(File directory) {
		this.directory = directory;
		if (!directory.exists()) return;
		for (File streamFolder : directory.listFiles()) {
			if (!streamFolder.isDirectory()) continue;
			for (File jobFolder : streamFolder.listFiles()) {
				if (!jobFolder.isDirectory()) continue;
				jobFolderMap.put(jobFolder.getName(), jobFolder);
			}
		}
	}
	
	public List<Job> getCachedOutputs(Job input) {
		if (hasCachedOutputs(input)) {
			ArrayList<Job> outputs = new ArrayList<Job>();
			File inputFolder = jobFolderMap.get(input.getHash());
			for (File outputFile : inputFolder.listFiles()) {
				Job output = (Job) xstream.fromXML(outputFile); 
				output.setId(UUID.randomUUID().toString());
				output.setCorrelationId(input.getId());
				output.setCached(true);
				outputs.add(output);
			}
			return outputs;
		}
		else {
			return Collections.emptyList();
		}
	}
	
	public boolean hasCachedOutputs(Job input) {
		return jobFolderMap.containsKey(input.getHash());
	}
	
	public synchronized void cache(Job output) {
		
		if (!output.isCacheable()) return;
		
		jobMap.put(output.getId(), output);
		Job input = jobMap.get(output.getCorrelationId());
		
		if (input != null) {
			File streamFolder = new File(directory, input.getDestination());
			try {
				File inputFolder = new File(streamFolder, input.getHash());
				inputFolder.mkdirs();
				//File inputFile = new File(inputFolder, "job.xml");
				//if (!inputFile.exists()) save(input, inputFile);
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
	
}
