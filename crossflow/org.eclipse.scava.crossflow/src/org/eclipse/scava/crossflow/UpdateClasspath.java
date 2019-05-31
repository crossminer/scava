package org.eclipse.scava.crossflow;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.epsilon.emc.plainxml.PlainXmlModel;
import org.eclipse.epsilon.eol.EolModule;

public class UpdateClasspath {

	public void run(File classpath) throws Exception {
		EolModule module = new EolModule();
		PlainXmlModel model = new PlainXmlModel();
		model.setName("Classpath");
		model.setReadOnLoad(true);
		model.setStoredOnDisposal(true);
		model.setFile(classpath);
		model.load();
		module.getContext().getModelRepository().addModel(model);
		module.parse(getFileURI("updateClasspath.eol"));
		//
		module.execute();
		module.getContext().getModelRepository().dispose();
	}

	protected URI getFileURI(String fileName) throws URISyntaxException {
		URI binUri = UpdateClasspath.class.getResource(fileName).toURI();
		return new URI(binUri.toString().replace("bin", "src"));
	}

}
