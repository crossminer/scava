package crossflow.diagram.part;

import java.io.File;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.impl.DiagramImpl;
import org.eclipse.gmf.runtime.notation.impl.ShapeImpl;
import org.eclipse.jface.action.Action;
import org.eclipse.scava.crossflow.dt.GenerateBaseClassesFromModel;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;

import crossflow.Configuration;

/**
 * @generated NOT
 * @author kb
 *
 */
public class GenerateAction extends Action {

	//private static final Logger LOG = LogManager.getLogManager().getLogger(GenerateAction.class.toString());

	private IWorkbenchPage page = null;

	@Override
	public int getStyle() {
		return Action.AS_PUSH_BUTTON;
	}

	public GenerateAction(IWorkbenchPage page) {
		this.page = page;
	}

	@Override
	public void run() {

		System.err.println("Compiling Crossflow base Java code.");

		try {
			new GenerateBaseClassesFromModel().run(getProjectLocation(), 
					""
					//getPackageName()
					, getModelName());
		} catch (Exception e) {
			System.err.println("Error while compiling java code:");
			e.printStackTrace();
		}

		System.err.println("Compiled Crossflow base Java code.");

	}

	private String getPackageName() {

		String packageName = "";

		IWorkspace workspace = ResourcesPlugin.getWorkspace();

		// get location of workspace (java.io.File)
		File workspaceDirectory = workspace.getRoot().getLocation().toFile();

		IEditorPart ep = page.getActiveEditor();
		final CrossflowDiagramEditor wde = ((CrossflowDiagramEditor) ep);

		DiagramImpl n = (DiagramImpl) wde.getDiagramEditPart().getModel();

		TreeIterator<EObject> children = n.eAllContents();

		while (children.hasNext()) {

			EObject child = children.next();
			// System.err.println(child);
			if (child instanceof ShapeImpl) {
				ShapeImpl shape = (ShapeImpl) child;
				EObject element = shape.getElement();
				// System.err.println(element);
				if (element instanceof Configuration) {
					Configuration config = (Configuration) element;
					packageName = config.getRootPackageName();
					break;
				}
			}

		}
		return packageName;
	}

	private URI getModelName() {

		URI modelName;

		IEditorPart ep = page.getActiveEditor();
		final CrossflowDiagramEditor wde = ((CrossflowDiagramEditor) ep);

		DiagramImpl n = (DiagramImpl) wde.getDiagramEditPart().getModel();

		modelName = n.eResource().getURI();

		return modelName;
	}

	private String getProjectLocation() {

		String projectLocation = "";

		IWorkspace workspace = ResourcesPlugin.getWorkspace();

		// get location of workspace (java.io.File)
		File workspaceDirectory = workspace.getRoot().getLocation().toFile();

		IEditorPart ep = page.getActiveEditor();
		final CrossflowDiagramEditor wde = ((CrossflowDiagramEditor) ep);

		DiagramImpl n = (DiagramImpl) wde.getDiagramEditPart().getModel();

		TreeIterator<EObject> children = n.eAllContents();

		while (children.hasNext()) {

			EObject child = children.next();
			// System.err.println(child);
			if (child instanceof ShapeImpl) {
				ShapeImpl shape = (ShapeImpl) child;
				EObject element = shape.getElement();
				// System.err.println(element);
				if (element instanceof Configuration) {
					Configuration config = (Configuration) element;
					projectLocation = config.getProjectName();
					break;
				}
			}

		}
		System.err.println(workspaceDirectory.getPath() + File.separator + projectLocation);
		return workspaceDirectory.getPath() + File.separator + projectLocation;
	}

}
