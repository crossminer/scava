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
package org.eclipse.scava.crossflow;

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
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;
import org.eclipse.epsilon.eol.types.EolPrimitiveType;

public class GenerateDeploymentArtifacts {

	public static final String ARTIFACTS_FOLDER_NAME = "artifacts";
	protected List<Variable> parameters = new ArrayList<>();

	protected Object result;

	String projectLocation;
	String modelRelativePath;
	String dependenciesLocation;
	String packageName;

	public String run(String projectLocation, String modelRelativePath, String dependenciesLocation) throws Exception {

		this.projectLocation = projectLocation;
		this.modelRelativePath = modelRelativePath;
		this.dependenciesLocation = dependenciesLocation;

		return execute();
	}

	public String execute() throws Exception {

		EmfModel model = getModel();
		try {
			model.setStoredOnDisposal(false);
			createParameters();
			String name = getWorkflowName(model);
			generateExecutables(model);
			genereateWxGraphModel(model);
			return name;
		} finally {
			model.dispose();
		}
		
	}

	private void generateExecutables(EmfModel model) throws Exception, URISyntaxException, EolRuntimeException {
		IEolModule module = createModule();
		try {
			IEolContext context = module.getContext();
			module.parse(getFileURI("general/generateExecutables.egx"));
			List<ParseProblem> parseProblems = module.getParseProblems();
			if (parseProblems.size() > 0) {
				System.err.println("Parse errors occured...");
				for (ParseProblem problem : parseProblems) {
					System.err.println(problem);
				}
				throw new IllegalStateException("Generation script should not have errors. Corrupted?");
			}
			context.getModelRepository().addModel(model);
			context.getFrameStack().put(parameters);
			try {
				result = execute(module);
			} catch (EolRuntimeException ex) {
				// FIXME Give better info
				throw ex;
			}
		} finally {
			module.getContext().dispose();
		}
	}

	private void createParameters() throws EolRuntimeException {
		createDependenciesPathParameter();
		createDestinationFolderParameter();
		createResourcesFolderParameter();
	}

	private void createResourcesFolderParameter() {
		Variable descriptorFolder = new Variable();
		descriptorFolder.setName("resourcesFolder");
		descriptorFolder.setType(EolPrimitiveType.String);
		descriptorFolder.setValueBruteForce(GenerateImplementations.RESOURCES_FOLDER_NAME);
		parameters.add(descriptorFolder);
	}

	private void createDestinationFolderParameter() {
		Variable destFolder = new Variable();
		destFolder.setName("destFolder");
		destFolder.setType(EolPrimitiveType.String);
		destFolder.setValueBruteForce(ARTIFACTS_FOLDER_NAME);
		parameters.add(destFolder);
	}

	private void createDependenciesPathParameter() throws EolRuntimeException {
		Variable dependenciesPath = new Variable();
		dependenciesPath.setName("dependenciesPath");
		dependenciesPath.setType(EolPrimitiveType.String);
		dependenciesPath.setValueBruteForce(dependenciesLocation);
		parameters.add(dependenciesPath);
	}

	private Object execute(IEolModule module) throws EolRuntimeException {
		return module.execute();
	}

	private static URI getFileURI(String fileName) throws URISyntaxException {
		URI binUri = GenerateDeploymentArtifacts.class.getResource(fileName).toURI();
		return binUri.toString().contains("bin") ? new URI(binUri.toString().replaceAll("bin", "src")) : binUri;
	}

	private IEolModule createModule() {
		try {
			EglFileGeneratingTemplateFactory templateFactory = new EglFileGeneratingTemplateFactory();
			templateFactory.setOutputRoot(new File(projectLocation).getAbsolutePath());
			return new EgxModule(templateFactory);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private EmfModel getModel() throws Exception {
		return createAndLoadAnEmfModel("org.eclipse.scava.crossflow, http://www.eclipse.org/gmf/runtime/1.0.2/notation",
				modelRelativePath, "CrossflowLanguageModel", true, false, false);
	}

	private EmfModel createAndLoadAnEmfModel(String metamodelURI, String modelFile, String modelName,
			boolean readOnLoad, boolean storeOnDisposal, boolean isCached) throws EolModelLoadingException {
		EmfModel theModel = new EmfModel();
		StringProperties properties = new StringProperties();
		properties.put(EmfModel.PROPERTY_METAMODEL_URI, metamodelURI);
		properties.put(EmfModel.PROPERTY_MODEL_URI, EmfUtil.createFileBasedURI(modelFile) + "");
		properties.put(EmfModel.PROPERTY_NAME, modelName);
		properties.put(EmfModel.PROPERTY_READONLOAD, readOnLoad + "");
		properties.put(EmfModel.PROPERTY_STOREONDISPOSAL, storeOnDisposal + "");
		properties.put(EmfModel.PROPERTY_CACHED, isCached + "");
		theModel.load(properties, (IRelativePathResolver) null);
		return theModel;
	}

	private static String getWorkflowName(IModel model) {
		Object wf = null;
		try {
			wf = model.getAllOfType("Workflow").iterator().next();
		} catch (EolModelElementTypeNotFoundException e) {
			throw new IllegalStateException(e);
		}
		try {
			return (String) model.getPropertyGetter().invoke(wf, "name");
		} catch (EolRuntimeException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * Runs the EGX script that genreates the model.json that describes the workflow
	 * diagram
	 * 
	 * @param model
	 * @throws Exception
	 * @throws URISyntaxException
	 * @throws EolRuntimeException
	 */
	private void genereateWxGraphModel(EmfModel model) throws Exception, URISyntaxException, EolRuntimeException {
		IEolModule module = createModule();
		module.getContext().getModelRepository().addModel(model);
		module.getContext().getFrameStack().put(parameters);

		module.parse(getFileURI("external/crossflowExternalTools.egx"));
		if (module.getParseProblems().size() > 0) {
			System.err.println("Parse errors occured...");
			for (ParseProblem problem : module.getParseProblems()) {
				System.err.println(problem.toString());
			}
			throw new IllegalStateException("Error parsing generator script. See console for errors.");
		}

		result = execute(module);
	}
}
