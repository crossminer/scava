/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Konstantinos Barmpis - adaption for CROSSFLOW
 *     Jonathan Co - adaption for command line execution
 *     Horacio Hoyos Rodriguez - UI
 ******************************************************************************/
package org.eclipse.scava.crossflow;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.egl.EglFileGeneratingTemplateFactory;
import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;
import org.eclipse.epsilon.eol.types.EolPrimitiveType;
import org.eclipse.epsilon.flexmi.FlexmiResourceFactory;

/**
 * The class responsible for executing the required EGX code generators.
 * 
 * @author Konstantinos Barmpis
 * @author Horacio Hoyos Rodriguez
 *
 */
public class GenerateImplementations {

	public static final String RESOURCES_FOLDER_NAME = "resources";
	public static final String WORKFLOW_DESCRIPTION_NAME = "experiment";

	//protected IEolModule module;
	protected Object result;
	private final String modelRelativePath;
	private final File projectFolder;
	protected List<Variable> parameters = new ArrayList<>();

	/**
	 * Construct a new generator that uses the given project location and model path.
	 * @param projectLocation		the location of the project
	 * @param modelRelativePath		the relative path to the model
	 */
	public GenerateImplementations(File projectFolder, String modelRelativePath) {
		this.projectFolder = projectFolder;
		this.modelRelativePath = modelRelativePath;
	}

	public Map<String, String[]> run() throws Exception {

		EmfModel model = getModel();
		model.setStoredOnDisposal(false);
		Map<String, String[]> languages = findLanguages(model.getResource());
		createParameters();
		for (String language : languages.keySet()) {
			generateLanguageCode(model, language);
		}
		generateDescriptor(model);
		model.dispose();
		return languages;
	}
	
	/**
	 * Run the EGX script that generates the code generators for the 
	 * @param model
	 * @param language
	 * @throws Exception
	 * @throws URISyntaxException
	 * @throws EolRuntimeException
	 */
	private void generateLanguageCode(EmfModel model, String language)
			throws Exception, URISyntaxException, EolRuntimeException {
		IEolModule module = createModule();
		module.getContext().getModelRepository().addModel(model);
		module.getContext().getFrameStack().put(parameters);	
		module.parse(getFileURI(language + "/crossflow.egx"));
		if (module.getParseProblems().size() > 0) {
			System.err.println("Parse errors occured...");
			for (ParseProblem problem : module.getParseProblems()) {
				System.err.println(problem.toString());
			}
			throw new IllegalStateException("Error parsing generator script. See console for errors.");
		}
		result = execute(module);
		// TODO What do we need to do for other languages?
		if ("java".equals(language)) {
			updateJavaProject();
		}
		module.getContext().dispose();
	}
	
	/**
	 * Create the parameters required by the different scripts
	 */
	private void createParameters() {
		createResourcesFolderParameter();		
		createXmlFileNameParameter();
	}
	
	/**
	 * Create a parameter for the xmlFileName value
	 */
	private void createXmlFileNameParameter() {
		Variable xmlFileName = new Variable();
		xmlFileName.setName("xmlFileName");
		xmlFileName.setType(EolPrimitiveType.String);
		xmlFileName.setValueBruteForce(WORKFLOW_DESCRIPTION_NAME);
		parameters.add(xmlFileName);
	}
	
	/**
	 * Create a parameter for the resourcesFolder value
	 */
	private void createResourcesFolderParameter() {
		Variable resourcesFolder = new Variable();
		resourcesFolder.setName("resourcesFolder");
		resourcesFolder.setType(EolPrimitiveType.String);
		resourcesFolder.setValueBruteForce(RESOURCES_FOLDER_NAME);
		parameters.add(resourcesFolder);
	}

	private void updateJavaProject() throws Exception {
		// update classpath
		File classpath = getClasspathFile(projectFolder);
		if (classpath != null && classpath.exists()) {
			new UpdateClasspath().run(classpath);
		}

		// update manifest with dependencies
		File manifest = getManifestFile(projectFolder);
		if (manifest != null && manifest.exists()) {
			updateManifest(manifest);
		}
	}

	/**
	 * Located the target languages in the model.
	 * <p>
	 * Finds all instances of type Language and then queries for the name and output folder.
	 * @param r
	 * @return
	 */
    // FIXME Languages should be an enumeration at the meta-metamodel level so we can control
    // what languages we support and is less error prone
	private Map<String, String[]> findLanguages(Resource r) {

		EClass languageClass = (EClass) r.getContents().get(0).eClass().getEPackage().getEClassifier("Language");
		EAttribute nameAttr = languageClass.getEAllAttributes().stream().filter(a -> a.getName().equals("name"))
				.findFirst().get();
		EAttribute outfolderAttr = languageClass.getEAllAttributes().stream().filter(a -> a.getName().equals("outputFolder"))
				.findFirst().get();
		EAttribute genOutfolderAttr = languageClass.getEAllAttributes().stream().filter(a -> a.getName().equals("genOutputFolder"))
				.findFirst().get();

		Map<String, String[]> ret = new HashMap<>();

		for (Iterator<EObject> it = r.getAllContents(); it.hasNext();) {
			EObject o;
			if ((o = it.next()).eClass().equals(languageClass)) {
				String[] value = new String[2];
				value[0] = ((String) o.eGet(outfolderAttr)).toLowerCase();
				value[1] = ((String) o.eGet(genOutfolderAttr)).toLowerCase();
				ret.put(
					((String) o.eGet(nameAttr)).toLowerCase(),
					value
					);
			}
		}
		// add java regardless of whether it exists in the model
		// FIXME really?
		if (!ret.containsKey("java")) {
			ret.put("java", new String[]{"src", "src-gen"});
		}
		return ret;
	}

	protected Object execute(IEolModule module) throws EolRuntimeException {
		return module.execute();
	}

	protected URI getFileURI(String fileName) throws URISyntaxException {
		URI binUri = GenerateImplementations.class.getResource(fileName).toURI();
		return new URI(binUri.toString().replace("bin", "src"));
	}

	public IEolModule createModule() {
		try {
			EglFileGeneratingTemplateFactory templateFactory = new EglFileGeneratingTemplateFactory();
			templateFactory.setOutputRoot(new File(projectFolder.getPath()).getAbsolutePath());
			return new EgxModule(templateFactory);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public EmfModel getModel() throws Exception {
		EmfModel model = createAndLoadAnEmfModel(
				"org.eclipse.scava.crossflow, http://www.eclipse.org/gmf/runtime/1.0.2/notation",
				modelRelativePath,
				"Model",
				true,
				false,
				false);

		return model;
	}

	private EmfModel createAndLoadAnEmfModel(String metamodelURI, String modelFile, String modelName,
			boolean readOnLoad, boolean storeOnDisposal, boolean isCached) throws EolModelLoadingException {
		final EmfModel theModel = new EmfModel() {
			@Override
			protected ResourceSet createResourceSet() {
				final ResourceSet resourceSet = super.createResourceSet();
				resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("flexmi",
						new FlexmiResourceFactory());
				return resourceSet;
			}
		};
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
	
	private File getClasspathFile(File directory) {
		if (containsFile(directory, ".classpath")) {
			return new File(directory.getAbsolutePath() + "/.classpath");
		}
		return null;
	}

	private File getManifestFile(File directory) {
		File mff = new File(directory.getPath() + "/META-INF/");
		System.out.println(mff);
		if (containsFile(mff, "MANIFEST.MF")) {
			return new File(mff.getAbsolutePath() + "/MANIFEST.MF");
		}
		return null;
	}
	
	private boolean containsFile(File directory, String file) {
		for (String f : directory.list()) {
			if (f.equals(file))
				return true;
		}
		return false;
	}
	
	private void updateManifest(File manifest) throws Exception {

		BufferedReader r = new BufferedReader(new FileReader(manifest));

		List<String> contents = new LinkedList<String>();
		String line;
		while ((line = r.readLine()) != null) {
			contents.add(line);
		}
		r.close();

		// TODO make this smarter for manifests with multiple dependencies
		if (!contents.stream().anyMatch(s -> s.contains("Require-Bundle: org.eclipse.scava.crossflow.runtime"))) {

			String dependencies = "Require-Bundle: org.eclipse.scava.crossflow.runtime";

			BufferedWriter w = new BufferedWriter(new FileWriter(manifest, true));
			w.append(dependencies + "\n");
			w.close();

		}

		if (!contents.stream().anyMatch(s -> s.contains("Bundle-RequiredExecutionEnvironment"))) {

			String jre = "Bundle-RequiredExecutionEnvironment: JavaSE-1.8";

			BufferedWriter w = new BufferedWriter(new FileWriter(manifest, true));
			w.append(jre + "\n");
			w.close();

		}

	}
	
	/**
	 * Runs the EGX script that generates the workflow descriptor
	 * @param model
	 * @throws Exception
	 * @throws URISyntaxException
	 * @throws EolRuntimeException
	 */
	private void generateDescriptor(EmfModel model) throws Exception, URISyntaxException, EolRuntimeException {
		IEolModule module = createModule();
		module.getContext().getModelRepository().addModel(model);
		module.getContext().getFrameStack().put(parameters);	
		module.parse(getFileURI("general/generateDescriptor.egx"));
		if (module.getParseProblems().size() > 0) {
			System.err.println("Parse errors occured...");
			for (ParseProblem problem : module.getParseProblems()) {
				System.err.println(problem.toString());
			}
			throw new IllegalStateException("Error parsing generator script. See console for errors.");
		}
		execute(module);
		module.getContext().dispose();
	}

}
