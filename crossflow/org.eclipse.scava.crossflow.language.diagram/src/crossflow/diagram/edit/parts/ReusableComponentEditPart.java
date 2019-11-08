/*
 * 
 */
package crossflow.diagram.edit.parts;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.epsilon.flexmi.FlexmiResourceFactory;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.scava.crossflow.dt.GenerateImplementationDelegate;
import org.eclipse.swt.graphics.Color;
import org.osgi.framework.Bundle;

import crossflow.diagram.edit.policies.ReusableComponentItemSemanticEditPolicy;
import crossflow.diagram.part.CrossflowVisualIDRegistry;

/**
 * @generated
 */
public class ReusableComponentEditPart extends ShapeNodeEditPart {

	/**
	 * @generated NOT
	 */
	private static List<IConfigurationElement> getConfigurationElementsFor(final String extensionPointId) {
		ArrayList<IConfigurationElement> els = new ArrayList<IConfigurationElement>();
		if (Platform.isRunning()) {
			IConfigurationElement[] e = Platform.getExtensionRegistry().getConfigurationElementsFor(extensionPointId);

			els.addAll(Arrays.asList(e));
		}
		return els;
	}

	/**
	 * @generated NOT
	 */
	private void createInMemoryComponentModel() {

		// System.err.println("createInMemoryComponentModel");

		List<IConfigurationElement> reusableComponentsCEs = getConfigurationElementsFor(
				GenerateImplementationDelegate.REUSABLECOMPONENTEXTENSIONPOINT);

		ResourceSet resourceSet = getEditingDomain().getResourceSet();

		for (IConfigurationElement ice : reusableComponentsCEs) {
			Bundle bundle = Platform.getBundle(ice.getDeclaringExtension().getContributor().getName());
			URL model = bundle.getEntry(ice.getAttribute("definition"));
			// System.out.println(model);
			URL resolved = null;
			try {
				resolved = FileLocator.resolve(model);
			} catch (IOException e) {
				e.printStackTrace();
			}
			// System.out.println(resolved);
			// System.out.println(resolved.getFile());
			// File modelFile = new File(resolved.getFile());

			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("flexmi",
					new FlexmiResourceFactory());

			// System.out.println(URI.createFileURI(resolved.getFile()));

			//System.out.println(resourceSet.getResources());

			URI uri = URI.createFileURI(resolved.getFile());

			if (!resourceSet.getResources().stream().map(r -> r.getURI()).anyMatch(r -> r.equals(uri))) {

				Resource r = resourceSet.createResource(URI.createFileURI(resolved.getFile()));
				try {
					r.load(null);
				} catch (IOException e) {
					e.printStackTrace();
				}
				// System.out.println(r.getContents());

			}

		}

		//System.out.println(resourceSet.getResources());

	}

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 2017;

	/**
	 * @generated
	 */
	protected IFigure contentPane;

	/**
	 * @generated
	 */
	protected IFigure primaryShape;

	/**
	 * @generated NOT
	 */
	public ReusableComponentEditPart(View view) {
		super(view);
		// System.err.println("ReusableComponentEditPart constructor");
		createInMemoryComponentModel();
	}

	/**
	 * @generated
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new ReusableComponentItemSemanticEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
		// XXX need an SCR to runtime to have another abstract superclass that would let children add reasonable editpolicies
		// removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CONNECTION_HANDLES_ROLE);
	}

	/**
	 * @generated
	 */
	protected LayoutEditPolicy createLayoutEditPolicy() {
		org.eclipse.gmf.runtime.diagram.ui.editpolicies.LayoutEditPolicy lep = new org.eclipse.gmf.runtime.diagram.ui.editpolicies.LayoutEditPolicy() {

			protected EditPolicy createChildEditPolicy(EditPart child) {
				EditPolicy result = child.getEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE);
				if (result == null) {
					result = new NonResizableEditPolicy();
				}
				return result;
			}

			protected Command getMoveChildrenCommand(Request request) {
				return null;
			}

			protected Command getCreateCommand(CreateRequest request) {
				return null;
			}
		};
		return lep;
	}

	/**
	 * @generated
	 */
	protected IFigure createNodeShape() {
		return primaryShape = new ReusableComponentFigure();
	}

	/**
	 * @generated
	 */
	public ReusableComponentFigure getPrimaryShape() {
		return (ReusableComponentFigure) primaryShape;
	}

	/**
	 * @generated
	 */
	protected boolean addFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof ReusableComponentNameEditPart) {
			((ReusableComponentNameEditPart) childEditPart)
					.setLabel(getPrimaryShape().getFigureReusableComponentLabelFigure());
			return true;
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected boolean removeFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof ReusableComponentNameEditPart) {
			return true;
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected void addChildVisual(EditPart childEditPart, int index) {
		if (addFixedChild(childEditPart)) {
			return;
		}
		super.addChildVisual(childEditPart, -1);
	}

	/**
	 * @generated
	 */
	protected void removeChildVisual(EditPart childEditPart) {
		if (removeFixedChild(childEditPart)) {
			return;
		}
		super.removeChildVisual(childEditPart);
	}

	/**
	 * @generated
	 */
	protected IFigure getContentPaneFor(IGraphicalEditPart editPart) {
		return getContentPane();
	}

	/**
	 * @generated
	 */
	protected NodeFigure createNodePlate() {
		DefaultSizeNodeFigure result = new DefaultSizeNodeFigure(40, 40);
		return result;
	}

	/**
	 * Creates figure for this edit part.
	 * 
	 * Body of this method does not depend on settings in generation model so you
	 * may safely remove <i>generated</i> tag and modify it.
	 * 
	 * @generated
	 */
	protected NodeFigure createNodeFigure() {
		NodeFigure figure = createNodePlate();
		figure.setLayoutManager(new StackLayout());
		IFigure shape = createNodeShape();
		figure.add(shape);
		contentPane = setupContentPane(shape);
		return figure;
	}

	/**
	 * Default implementation treats passed figure as content pane. Respects layout
	 * one may have set for generated figure.
	 * 
	 * @param nodeShape instance of generated figure class
	 * @generated
	 */
	protected IFigure setupContentPane(IFigure nodeShape) {
		if (nodeShape.getLayoutManager() == null) {
			ConstrainedToolbarLayout layout = new ConstrainedToolbarLayout();
			layout.setSpacing(5);
			nodeShape.setLayoutManager(layout);
		}
		return nodeShape; // use nodeShape itself as contentPane
	}

	/**
	 * @generated
	 */
	public IFigure getContentPane() {
		if (contentPane != null) {
			return contentPane;
		}
		return super.getContentPane();
	}

	/**
	 * @generated
	 */
	protected void setForegroundColor(Color color) {
		if (primaryShape != null) {
			primaryShape.setForegroundColor(color);
		}
	}

	/**
	 * @generated
	 */
	protected void setBackgroundColor(Color color) {
		if (primaryShape != null) {
			primaryShape.setBackgroundColor(color);
		}
	}

	/**
	 * @generated
	 */
	protected void setLineWidth(int width) {
		if (primaryShape instanceof Shape) {
			((Shape) primaryShape).setLineWidth(width);
		}
	}

	/**
	 * @generated
	 */
	protected void setLineType(int style) {
		if (primaryShape instanceof Shape) {
			((Shape) primaryShape).setLineStyle(style);
		}
	}

	/**
	 * @generated
	 */
	public EditPart getPrimaryChildEditPart() {
		return getChildBySemanticHint(CrossflowVisualIDRegistry.getType(ReusableComponentNameEditPart.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	protected void handleNotificationEvent(Notification event) {
		if (event.getNotifier() == getModel()
				&& EcorePackage.eINSTANCE.getEModelElement_EAnnotations().equals(event.getFeature())) {
			handleMajorSemanticChange();
		} else {
			super.handleNotificationEvent(event);
		}
	}

	/**
	 * @generated
	 */
	public class ReusableComponentFigure extends RoundedRectangle {

		/**
		 * @generated
		 */
		private WrappingLabel fFigureReusableComponentLabelFigure;

		/**
		 * @generated
		 */
		public ReusableComponentFigure() {
			this.setCornerDimensions(new Dimension(getMapMode().DPtoLP(8), getMapMode().DPtoLP(8)));
			this.setBorder(new MarginBorder(getMapMode().DPtoLP(5), getMapMode().DPtoLP(5), getMapMode().DPtoLP(5),
					getMapMode().DPtoLP(5)));
			createContents();
		}

		/**
		 * @generated
		 */
		private void createContents() {

			fFigureReusableComponentLabelFigure = new WrappingLabel();

			fFigureReusableComponentLabelFigure.setText("ReusableComponent");

			this.add(fFigureReusableComponentLabelFigure);

		}

		/**
		 * @generated
		 */
		public WrappingLabel getFigureReusableComponentLabelFigure() {
			return fFigureReusableComponentLabelFigure;
		}

	}

}
