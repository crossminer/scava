package org.eclipse.scava.crossflow;
/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Konstantinos Barmpis - adaption for CROSSFLOW
 ******************************************************************************/

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.egl.EglFileGeneratingTemplateFactory;
import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;
import org.eclipse.epsilon.eol.types.EolPrimitiveType;

public class GenerateExecutables {

	protected IEolModule module;
	protected List<Variable> parameters = new ArrayList<>();

	protected Object result;

	String projectLocation;
	String modelRelativePath;
	String dependenciesLocation;
	String packageName;

	public void run(String projectLocation, String modelRelativePath, String dependenciesLocation) throws Exception {

		this.projectLocation = projectLocation;
		this.modelRelativePath = modelRelativePath;
		this.dependenciesLocation = dependenciesLocation;

		execute();
	}

	public void execute() throws Exception {

		IEolContext context = (module = createModule()).getContext();
		module.parse(getFileURI("java/generateExecutables.egx"));

		Variable dependenciesPath = new Variable();
		dependenciesPath.setName("dependenciesPath");
		dependenciesPath.setType(EolPrimitiveType.String);
		dependenciesPath.setValue(dependenciesLocation, module.getContext());
		parameters.add(dependenciesPath);

		List<ParseProblem> parseProblems = module.getParseProblems();
		
		if (parseProblems.size() > 0) {
			System.err.println("Parse errors occured...");
			for (ParseProblem problem : parseProblems) {
				System.err.println(problem);
			}
			return;
		}

		IModel[] modelsArr = getModels().toArray(new IModel[0]);
		
		context.getModelRepository().addModels(modelsArr);
		context.getFrameStack().put(parameters);

		result = execute(module);

		module.getContext().getModelRepository().dispose();
	}

	protected Object execute(IEolModule module) throws EolRuntimeException {
		return module.execute();
	}

	protected URI getFileURI(String fileName) throws URISyntaxException {

		URI binUri = GenerateExecutables.class.getResource(fileName).toURI();
		URI uri = null;

		if (binUri.toString().indexOf("bin") > -1) {
			uri = new URI(binUri.toString().replaceAll("bin", "src"));
		} else {
			uri = binUri;
		}

		return uri;
	}

	public IEolModule createModule() {
		try {
			EglFileGeneratingTemplateFactory templateFactory = new EglFileGeneratingTemplateFactory();
			templateFactory.setOutputRoot(new File(projectLocation).getAbsolutePath());
			return new EgxModule(templateFactory);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public List<IModel> getModels() throws Exception {
		List<IModel> models = new ArrayList<>();
		models.add(
				createAndLoadAnEmfModel("org.eclipse.scava.crossflow", modelRelativePath, "Model", true, false, false));

		return models;
	}

	private EmfModel createAndLoadAnEmfModel(String metamodelURI, String modelFile, String modelName,
			boolean readOnLoad, boolean storeOnDisposal, boolean isCached) throws EolModelLoadingException {
		EmfModel theModel = new EmfModel();
		StringProperties properties = new StringProperties();
		properties.put(EmfModel.PROPERTY_METAMODEL_URI, metamodelURI);
		properties.put(EmfModel.PROPERTY_MODEL_FILE, modelFile);
		properties.put(EmfModel.PROPERTY_NAME, modelName);
		properties.put(EmfModel.PROPERTY_READONLOAD, readOnLoad + "");
		properties.put(EmfModel.PROPERTY_STOREONDISPOSAL, storeOnDisposal + "");
		properties.put(EmfModel.PROPERTY_CACHED, isCached + "");
		theModel.load(properties, (IRelativePathResolver) null);
		return theModel;
	}

}
