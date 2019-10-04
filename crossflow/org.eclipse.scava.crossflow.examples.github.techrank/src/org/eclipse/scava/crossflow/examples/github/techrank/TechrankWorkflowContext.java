package org.eclipse.scava.crossflow.examples.github.techrank;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class TechrankWorkflowContext {
	
	protected TechrankWorkflow workflow;
	
	public TechrankWorkflowContext(TechrankWorkflow workflow) {
		this.workflow = workflow;
	}
	
	public Properties getProperties() throws Exception {
		File propertiesFile = new File(workflow.getInputDirectory(), "crossflow.properties").getAbsoluteFile();
		if (!propertiesFile.exists()) throw new FileNotFoundException(propertiesFile.getAbsolutePath());
		Properties properties = new Properties();
		properties.load(new FileInputStream(propertiesFile));
		return properties;
	}
	
}
