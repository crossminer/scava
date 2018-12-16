package org.eclipse.scava.crossflow.web;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.activemq.broker.BrokerService;
import org.apache.thrift.TException;
import org.eclipse.scava.crossflow.runtime.DirectoryCache;
import org.eclipse.scava.crossflow.runtime.Mode;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CrossflowHandler implements Crossflow.Iface {

	protected HashMap<String, Workflow> workflows = new HashMap<>();
	protected BrokerService brokerService;
	protected CrossflowServlet servlet;
	
	public CrossflowHandler(CrossflowServlet servlet) {
		this.servlet = servlet;
	}
	
	protected ClassLoader getClassLoader() throws Exception {
		return Thread.currentThread().getContextClassLoader();
	}
	
	@Override
	public String startExperiment(String experimentId) throws TException {
		Experiment experiment = getExperiment(experimentId);
		try {
			
			if (!isBrokerRunning()) {
				startBroker();
			}
			
			Workflow workflow = (Workflow) getClassLoader().loadClass(experiment.getClassName()).getConstructor(Mode.class).newInstance(Mode.MASTER);
			workflow.setInstanceId(experimentId);
			workflow.createBroker(false);
			workflow.setCache(new DirectoryCache(new File(servlet.getServletContext().getRealPath("experiments/" + experimentId + "/cache"))));
			workflow.setInputDirectory(new File(servlet.getServletContext().getRealPath("experiments/" + experimentId + "/" + experiment.getInput())));
			workflow.setOutputDirectory(new File(servlet.getServletContext().getRealPath("experiments/" + experimentId + "/" + experiment.getOutput())));
			
			workflow.run();
			workflows.put(experimentId, workflow);
			return workflow.getInstanceId();
		} catch (Exception e) {
			e.printStackTrace();
			throw new TException(e);
		}
	}
	
	@Override
	public void stopExperiment(String experimentId) throws TException {
		Workflow workflow = workflows.get(experimentId);
		if (workflow != null) {
			workflow.terminate();
			workflows.remove(experimentId);
		}
	}
	
	@Override
	public boolean isExperimentRunning(String experimentId) throws TException {
		Workflow workflow = workflows.get(experimentId);
		if (workflow != null) {
			return workflow.hasTerminated();
		}
		else {
			return false;
		}
	}
	
	@Override
	public Experiment getExperiment(String experimentId) throws TException {
		return getExperiments().stream().filter(e -> e.getId().equals(experimentId)).
				collect(Collectors.toList()).iterator().next();
	}
	
	@Override
	public List<Experiment> getExperiments() throws TException {
		File experimentsDirectory = new File(servlet.getServletContext().getRealPath("experiments"));
		if (experimentsDirectory.exists()) {
			List<Experiment> experiments = new ArrayList<>();
			for (File experimentDirectory : experimentsDirectory.listFiles()) {
				if (experimentDirectory.isDirectory()) {
					experiments.add(getExperiment(experimentDirectory));
				}
			}
			return experiments;
		}
		return Collections.emptyList();
	}
	
	@Override
	public void resetExperiment(String experimentId) throws TException {
		
		System.out.println("RESET!!!");
		
		Experiment experiment = getExperiment(experimentId);
		System.out.println(experiment.getOutput() == null);
		
		if (experiment.getOutput() != null) {
			File output = new File(servlet.getServletContext().getRealPath("experiments/" + experimentId + "/" + experiment.getOutput()));
			if (output != null && output.exists()) {
				delete(output);
			}
		}
		File cache = new File(servlet.getServletContext().getRealPath("experiments/" + experimentId + "/cache"));
		if (cache != null && cache.exists()) {
			delete(cache);
		}
		
	}
	
	public void delete(File file) {
		if (file.isDirectory()) {
			for (File child : file.listFiles()) {
				delete(child);
			}
		}
		file.delete();
	}
	
	protected Experiment getExperiment(File experimentDirectory) throws TException {
		try {
			File config = new File(experimentDirectory, "experiment.xml");
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new FileInputStream(config));
			Element experimentElement = document.getDocumentElement();
			Experiment experiment = new Experiment();
			experiment.setId(experimentDirectory.getName());
			experiment.setTitle(experimentElement.getAttribute("title"));
			experiment.setClassName(experimentElement.getAttribute("class"));
			experiment.setJar(experimentElement.getAttribute("jar"));
			experiment.setSummary(experimentElement.getAttribute("summary"));
			experiment.setInput(experimentElement.getAttribute("input"));
			experiment.setOutput(experimentElement.getAttribute("output"));
			experiment.setDescription(experimentElement.getTextContent());
			experiment.setCached(new File(experimentDirectory, "cache").exists());
			if (experiment.getOutput() != null) {
				experiment.setExecuted(new File(experimentDirectory, experiment.getOutput()).exists());
			}
			
			Workflow workflow = workflows.get(experiment.getId());
			if (workflow != null && !workflow.hasTerminated()) {
				experiment.status = "running";
			}
			else {
				experiment.status = "stopped";
			}
			
			return experiment;
		}
		catch (Exception ex) {
			throw new TException(ex);
		}
	}

	@Override
	public void startBroker() throws TException {
		if (brokerService != null) {
			stopBroker();
		}
		
		try {
			brokerService = new BrokerService();
			brokerService.setUseJmx(true);
			brokerService.addConnector("tcp://localhost:61616");
			brokerService.start();
		}
		catch (Exception ex) {
			throw new TException(ex);
		}
	}

	@Override
	public void stopBroker() throws TException {
		if (brokerService != null) {
			try {
				brokerService.deleteAllMessages();
				brokerService.stopGracefully("", "", 1000, 1000);
			} catch (Exception ex) {
				throw new TException(ex); 
			}
			brokerService = null;
		}
	}
	
	@Override
	public boolean isBrokerRunning() throws TException {
		return brokerService != null;
	}
	
	@Override
	public Diagnostics getDiagnostics() throws TException {
		Diagnostics diagnostics = new Diagnostics();
		diagnostics.setBrokerRunning(isBrokerRunning());
		diagnostics.setRootDirectory(servlet.getServletContext().getRealPath(""));
		return diagnostics;
	}

}
