package org.eclipse.scava.crossflow.runtime;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Cache {
	
	protected HashMap<String, File> jobFolderMap = new HashMap<String, File>();
	protected HashMap<String, Job> jobMap = new HashMap<String, Job>();
	
	protected File root = new File("crossflow-cache");
	protected Workflow workflow = null;
	protected XStream xstream = new XStream(new DomDriver());
	
	public Cache(Workflow workflow) {
		this.workflow = workflow;
		File workflowFolder = new File(root, workflow.getName());
		if (!workflowFolder.exists()) return;
		for (File streamFolder : workflowFolder.listFiles()) {
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
	
	public void cache(Job output) {
		File workflowFolder = new File(root, workflow.getName());
		jobMap.put(output.getId(), output);
		Job input = jobMap.get(output.getCorrelationId());
		
		if (input != null) {
			File streamFolder = new File(workflowFolder, input.getDestination());
			try {
				File inputFolder = new File(streamFolder, input.getHash());
				inputFolder.mkdirs();
				File outputFile = new File(inputFolder, output.getHash());
				jobFolderMap.put(input.getHash(), inputFolder);
				FileOutputStream fos = new FileOutputStream(outputFile);
				fos.write(output.getXML().getBytes());
				fos.flush();
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
}
