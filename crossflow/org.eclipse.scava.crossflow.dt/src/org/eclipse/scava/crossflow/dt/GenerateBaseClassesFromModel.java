package org.eclipse.scava.crossflow.dt;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.egl.EglFileGeneratingTemplateFactory;
import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;

public class GenerateBaseClassesFromModel extends EpsilonStandaloneExample {

	String projectLocation;
	String crossflowDiagramModelName;
	String packageName;

	public void run(String projectLocation, String packageName,
			org.eclipse.emf.common.util.URI crossflowDiagramModelURI) throws Exception {

		this.projectLocation = projectLocation;
		this.packageName = packageName;
		crossflowDiagramModelName = crossflowDiagramModelURI.lastSegment();

		execute();

	}

	@Override
	public IEolModule createModule() {
		try {
			EglFileGeneratingTemplateFactory templateFactory = new EglFileGeneratingTemplateFactory();
			templateFactory.setOutputRoot(new File(projectLocation + "/src/" + packageName).getAbsolutePath());
			return new EgxModule(templateFactory);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public List<IModel> getModels() throws Exception {
		List<IModel> models = new ArrayList<IModel>();
		models.add(createAndLoadAnEmfModel("http://org.eclipse.scava.crossflow",
				projectLocation + File.separator + "model" + File.separator + crossflowDiagramModelName, "Model",
				true, false, false));

		return models;
	}

	private EmfModel createAndLoadAnEmfModel(String metamodelURI, String modelFile, String modelName, boolean readOnLoad,
			boolean storeOnDisposal, boolean isCached) throws EolModelLoadingException {
		EmfModel theModel = new EmfModel();
		StringProperties properties = new StringProperties();
		properties.put(EmfModel.PROPERTY_METAMODEL_URI, metamodelURI);
		properties.put(EmfModel.PROPERTY_MODEL_FILE, modelFile);
		properties.put(EmfModel.PROPERTY_NAME, modelName);
		properties.put(EmfModel.PROPERTY_READONLOAD, readOnLoad);
		properties.put(EmfModel.PROPERTY_STOREONDISPOSAL, storeOnDisposal);
		properties.put(EmfModel.PROPERTY_CACHED, isCached);
		theModel.load(properties, (IRelativePathResolver) null);
		return theModel;
	}

	@Override
	public String getSource() throws Exception {
		return "epsilon/crossflowOrchestrator.egx";
	}

}
