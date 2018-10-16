package crossflow.diagram.part;

import java.io.File;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.impl.DiagramImpl;
import org.eclipse.gmf.runtime.notation.impl.ShapeImpl;
import org.eclipse.jface.action.Action;
import org.eclipse.scava.crossflow.dt.views.CrossflowExecutionView;
import org.eclipse.scava.crossflow.execution.subscription.WorkflowGraphicalChangeSubscription;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.Workbench;

import crossflow.Field;
import crossflow.Sink;
import crossflow.Task;
import crossflow.Type;
import crossflow.impl.CrossflowPackageImpl;
import crossflow.impl.WorkflowImpl;

/**
 * @generated NOT
 * @author kb
 *
 */
@SuppressWarnings("unused")
public class RunAction extends Action {

	// private static final Logger LOG =
	// LogManager.getLogManager().getLogger(RunAction.class.toString());

	private IWorkbenchPage page = null;

	@Override
	public int getStyle() {
		return Action.AS_PUSH_BUTTON;
	}

	public RunAction(IWorkbenchPage page) {
		this.page = page;
	}

	private String getProjectLocation(String workspaceDirectory, String eclipseProjectName) {
		return null; // TODO: implement this!
	}

	@Override
	public void run() {

		WorkflowGraphicalChangeSubscription wgcs = WorkflowGraphicalChangeSubscription.getinstance();

		// TODO: generate JAR from "generated" project --> move this later to
		// generate()
		// find Eclipse Workspace
		// get object which represents the workspace
		IWorkspace workspace = ResourcesPlugin.getWorkspace();

		// get location of workspace (java.io.File)
		File workspaceDirectory = workspace.getRoot().getLocation().toFile();
		// TODO: project path will be defined in the "generated" model
		System.out.println("workspaceDirectory= " + workspaceDirectory.getAbsolutePath());

		IEditorPart ep = page.getActiveEditor();
		final CrossflowDiagramEditor wde = ((CrossflowDiagramEditor) ep);

		DiagramImpl n = (DiagramImpl) wde.getDiagramEditPart().getModel();

		WorkflowImpl wfi = (WorkflowImpl) n.basicGetElement();

		String projectName = wfi.getConfiguration().getProjectName();

		// FIXME only looks at the first sink for output and its first input stream
		// (which should be fine as all input streams should have the same type)
		String[] resultStructure = null;
		for (Task t : wfi.getTasks())
			if (t instanceof Sink) {
				Type type = t.getInput().get(0).getType();
				resultStructure = new String[type.getFields().size()];
				for (int i = 0; i < resultStructure.length; i++)
					resultStructure[i] = type.getFields().get(i).getName();
				break;
			}

		IViewPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.findView(CrossflowExecutionView.ID);
		if (part instanceof CrossflowExecutionView) {
			CrossflowExecutionView view = (CrossflowExecutionView) part;
			view.initializeColumnNames(resultStructure);
		}

		// String pomPath = workspaceDirectory + File.separator + projectName +
		// File.separator + "pom.xml";

		// System.out.println(pomPath);

		//

		// CrossflowExecutablePackageAssembler a = new
		// CrossflowExecutablePackageAssembler(pomPath);
		// a.run();
		//

	}

	private void executionStub() {

		IEditorPart ep = page.getActiveEditor();
		final CrossflowDiagramEditor wde = ((CrossflowDiagramEditor) ep);

		WorkflowGraphicalChangeSubscription wgcs = WorkflowGraphicalChangeSubscription.getinstance();

		DiagramImpl n = (DiagramImpl) wde.getDiagramEditPart().getModel();

		TreeIterator<EObject> children = n.eAllContents();

		while (children.hasNext()) {

			EObject child = children.next();

			if (child instanceof ShapeImpl) {

				ShapeImpl i = (ShapeImpl) child;

				EClass jtt = CrossflowPackageImpl.eINSTANCE.getTask();

				if (isOfKind(i, jtt)) {
					Task elem = (Task) i.getElement();

					elem.eResource().getURI();

					// DUMMY:
					EObject eelem = i.getElement();
					double random = Math.random();
					if (random < 0.33)
						wgcs.elementInProgress(eelem);
					else if (random < 0.66)
						wgcs.elementBlocked(eelem);
					else
						wgcs.elementComplete(eelem);

				}
			}
		}

	}

	private boolean isOfKind(ShapeImpl c, EClassifier eClass) {

		System.out.println("ActualClass= " + c.getElement().eClass().getName());
		System.out.println("ActualClass.superTypes= " + c.getElement().eClass().getEAllSuperTypes());
		System.out.println("ExpectedClass= " + eClass);

		if (eClass == null)
			return false;
		if (c.getElement().eClass().equals(eClass))
			return true;
		for (EClass e : c.getElement().eClass().getEAllSuperTypes()) {
			if (e.equals(eClass)) {
				return true;
			}
		}
		return false;
	}
}
