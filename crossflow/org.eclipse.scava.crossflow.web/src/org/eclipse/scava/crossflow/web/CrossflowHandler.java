package org.eclipse.scava.crossflow.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.thrift.TException;
import org.eclipse.scava.crossflow.runtime.DirectoryCache;
import org.eclipse.scava.crossflow.runtime.Mode;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

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
	public String startExperiment(String experimentId, boolean worker) throws TException {
		Experiment experiment = getExperiment(experimentId);
		try {
			
			if (!isBrokerRunning()) {
				startBroker();
			}
			
			Mode mode = Mode.MASTER;
			if (!worker) mode = Mode.MASTER_BARE;
			
			ClassLoader classLoader = new URLClassLoader(new URL[] {
					new File(servlet.getServletContext().getRealPath("jars/" + experiment.getJar())).toURI().toURL()}, 
					Thread.currentThread().getContextClassLoader());
			
			Workflow workflow = (Workflow) classLoader.loadClass(experiment.getClassName()).getConstructor(Mode.class).newInstance(mode);
			workflow.setInstanceId(experimentId);
			workflow.getSerializer().setClassloader(classLoader);
			workflow.createBroker(false);
			workflow.setCache(new DirectoryCache(new File(servlet.getServletContext().getRealPath("experiments/" + experimentId + "/cache"))));
			workflow.setInputDirectory(new File(servlet.getServletContext().getRealPath("experiments/" + experimentId + "/" + experiment.getInputDirectory())));
			workflow.setOutputDirectory(new File(servlet.getServletContext().getRealPath("experiments/" + experimentId + "/" + experiment.getOutputDirectory())));
			workflow.setRuntimeModel(new File(servlet.getServletContext().getRealPath("experiments/" + experimentId + "/" + experiment.getRuntimeModel())));
			
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
		
		Experiment experiment = getExperiment(experimentId);
		System.out.println(experiment.getOutputDirectory() == null);
		
		if (experiment.getOutputDirectory() != null) {
			File output = new File(servlet.getServletContext().getRealPath("experiments/" + experimentId + "/" + experiment.getOutputDirectory()));
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
	
	@Override
	public Table getContent(FileDescriptor fileDescriptor) throws TException {
		
		File file = new File(servlet.getServletContext().getRealPath("experiments/" + fileDescriptor.getExperimentId() + "/" + fileDescriptor.getPath()));
		if (!file.exists()) return new Table();
		
		try {
			Table table = new Table();
			boolean header = true;
			for (CSVRecord record : CSVFormat.RFC4180.parse(new FileReader(file))) {
				Row row = new Row();
				for (int i=0;i<record.size();i++) {
					row.addToCells(record.get(i));
				}
				if (header) {
					table.setHeader(row);
					header = false;
				}
				else {
					table.addToRows(row);
				}
			}
			return table;
		}
		catch (Exception ex) {
			throw new TException(ex);
		}
		
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
			experiment.setInputDirectory(experimentElement.getAttribute("input"));
			experiment.setOutputDirectory(experimentElement.getAttribute("output"));
			experiment.setRuntimeModel(experimentElement.getAttribute("runtimeModel"));
			experiment.setCached(new File(experimentDirectory, "cache").exists());
			if (experiment.getOutputDirectory() != null) {
				experiment.setExecuted(new File(experimentDirectory, experiment.getOutputDirectory()).exists());
			}
			
			for (Element descriptionElement : toElementList(experimentElement.getElementsByTagName("description"))) {
				experiment.setDescription(descriptionElement.getTextContent());
			}

			for (Element inputElement : toElementList(experimentElement.getElementsByTagName("input"))) {
				FileDescriptor fileDescriptor = elementToFileDescriptor(inputElement);
				fileDescriptor.setExperimentId(experiment.getId());
				fileDescriptor.setInput(true);
				experiment.addToFileDescriptors(fileDescriptor);
			}
			
			for (Element outputElement : toElementList(experimentElement.getElementsByTagName("output"))) {
				FileDescriptor fileDescriptor = elementToFileDescriptor(outputElement);
				fileDescriptor.setExperimentId(experiment.getId());
				fileDescriptor.setInput(false);
				experiment.addToFileDescriptors(fileDescriptor);
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
		
		if (isBrokerRunning()) return;
		
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
		try {
			new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
			return true;
		}
		catch (Exception ex) {
			return false;
		}
	}
	
	@Override
	public Diagnostics getDiagnostics() throws TException {
		Diagnostics diagnostics = new Diagnostics();
		diagnostics.setBrokerRunning(isBrokerRunning());
		diagnostics.setRootDirectory(servlet.getServletContext().getRealPath(""));
		return diagnostics;
	}
	
	protected ArrayList<Element> toElementList(NodeList nodeList) {
		ArrayList<Element> list = new ArrayList<Element>();
		for (int i=0;i<nodeList.getLength();i++) {
			list.add((Element)nodeList.item(i));
		}
		return list;
	}
	
	protected FileDescriptor elementToFileDescriptor(Element element) {
		FileDescriptor fileDescriptor = new FileDescriptor();
		fileDescriptor.setPath(element.getAttribute("path"));
		fileDescriptor.setTitle(element.getAttribute("title"));
		return fileDescriptor;
	}
}
