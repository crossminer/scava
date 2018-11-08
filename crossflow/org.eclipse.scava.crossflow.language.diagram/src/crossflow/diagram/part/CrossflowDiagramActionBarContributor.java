/*
 * 
 */
package crossflow.diagram.part;

import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramActionBarContributor;
import org.eclipse.gmf.runtime.diagram.ui.printing.render.actions.EnhancedPrintActionHelper;
import org.eclipse.gmf.runtime.diagram.ui.printing.render.actions.RenderedPrintPreviewAction;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * @generated
 */
public class CrossflowDiagramActionBarContributor extends DiagramActionBarContributor {

	/**
	 * @generated
	 */
	protected Class getEditorClass() {
		return CrossflowDiagramEditor.class;
	}

	/**
	 * @generated
	 */
	protected String getEditorId() {
		return CrossflowDiagramEditor.ID;
	}

	/**
	 * @generated NOT
	 */
	public void init(IActionBars bars, IWorkbenchPage page) {

		//create generation button
		IAction a = new GenerateAction(page);

		a.setId("crossflow-generation");
		a.setEnabled(true);
		a.setImageDescriptor(ExtendedImageRegistry.getInstance().getImageDescriptor(AbstractUIPlugin
				.imageDescriptorFromPlugin("org.eclipse.scava.crossflow.diagram", "icons/Comp-small.png")));
		a.setText("Generate");
		a.setDescription("Generate the workflow java code.");

		addAction(a);
		bars.getToolBarManager().add(a);
		//

		//create run button
		a = new RunAction(page);

		a.setId("crossflow-execution");
		a.setEnabled(true);
		a.setImageDescriptor(ExtendedImageRegistry.getInstance().getImageDescriptor(AbstractUIPlugin
				.imageDescriptorFromPlugin("org.eclipse.scava.crossflow.diagram", "icons/Go-small.png")));
		a.setText("Execute");
		a.setDescription("Execute the workflow.");

		addAction(a);
		bars.getToolBarManager().add(a);
		//

		super.init(bars, page);
		// print preview
		IMenuManager fileMenu = bars.getMenuManager().findMenuUsingPath(IWorkbenchActionConstants.M_FILE);
		assert fileMenu != null;
		IAction printPreviewAction = new RenderedPrintPreviewAction(new EnhancedPrintActionHelper());
		fileMenu.insertBefore("print", printPreviewAction); //$NON-NLS-1$
		IMenuManager editMenu = bars.getMenuManager().findMenuUsingPath(IWorkbenchActionConstants.M_EDIT);
		assert editMenu != null;
		if (editMenu.find("validationGroup") == null) { //$NON-NLS-1$
			editMenu.add(new GroupMarker("validationGroup")); //$NON-NLS-1$
		}
		IAction validateAction = new ValidateAction(page);
		editMenu.appendToGroup("validationGroup", validateAction); //$NON-NLS-1$

	}
}
