/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTRequestor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.scava.plugin.codehighlighter.CodeHighlighterController;
import org.eclipse.scava.plugin.codehighlighter.CodeHighlighterModel;
import org.eclipse.scava.plugin.codehighlighter.IHighlightDefinition;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.ApiMigrationParameters;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.ast.ASTProcessor;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.ast.ProcessedAST;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.ast.ProcessedASTNode;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.highlight.DetectionHighlightData;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.highlight.DetectionHighlightDefinition;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.location.ASTLocation;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.location.PathPartLocation;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.location.RascalLocation;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.treelements.CodeSnippetTreeElement;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.treelements.CodeSnippetsTreeElement;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.treelements.DetectionOccurenceTreeElement;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.treelements.DetectionTreeElement;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.treelements.DetectionTypeTreeElement;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.treelements.PathPartTreeElement;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.treelements.TreeElement;
import org.eclipse.scava.plugin.main.page.OpenFileInEditorRequestEvent;
import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.controller.ModelViewController;
import org.eclipse.scava.plugin.mvc.event.routed.IRoutedEvent;
import org.eclipse.swt.widgets.Display;

import io.swagger.client.model.Detection;

public class RecommendationTreeController extends ModelViewController<RecommendationTreeModel, RecommendationTreeView>
		implements IRecommendationTreeViewEventListener {

	public RecommendationTreeController(Controller parent, RecommendationTreeModel model, RecommendationTreeView view) {
		super(parent, model, view);
	}

	@Override
	public void init() {
		super.init();
	}

	public void add(Map<String, List<String>> codeSnippets) {
		for (Map.Entry<String, List<String>> recommendation : codeSnippets.entrySet()) {
			String clientLocation = recommendation.getKey();
			RascalLocation rascalClientLocation = new RascalLocation(clientLocation);

			TreeElement clientLocationTreeElement = getModel().getTreeElementOf(rascalClientLocation);

			CodeSnippetsTreeElement codeSnippetsTreeElement = clientLocationTreeElement
					.GetEqualChildOrAdd(new CodeSnippetsTreeElement());

			for (String codeSnippet : recommendation.getValue()) {
				codeSnippetsTreeElement.GetEqualChildOrAdd(new CodeSnippetTreeElement(codeSnippet));
			}
		}

		Display.getDefault().asyncExec(() -> {
			getView().show(getModel().getTreeElements());
		});
	}

	public void add(Collection<Detection> detections, ApiMigrationParameters apiMigrationParameters) {
		ProcessedAST processedAST = getProcessedAST();
		Set<ProcessedASTNode> ASTLocations = processedAST.flattenChildren().parallel().collect(Collectors.toSet());

		Map<DetectionTreeElement, Collection<IHighlightDefinition>> highlightDefinitionsOfDetections = new HashMap<>();

		for (Detection detection : detections) {
			String clientLocation = detection.getClientLocation();
			RascalLocation rascalClientLocation = new RascalLocation(clientLocation);

			TreeElement clientLocationTreeElement = getModel().getTreeElementOf(rascalClientLocation);

			String detectionType = detection.getType().toString();
			TreeElement detectionTypeTreeElement = clientLocationTreeElement
					.GetEqualChildOrAdd(new DetectionTypeTreeElement(detectionType));
			DetectionTreeElement detectionTreeElement = detectionTypeTreeElement.GetEqualChildOrAdd(
					new DetectionTreeElement(detection.getOldLibraryLocation(), detection.getNewLibraryLocation()));

			ProcessedASTNode clientLocationProcessedASTNode = rascalClientLocation.findMostJaccardSimilar(ASTLocations);
			Collection<ProcessedASTNode> mostSimilarDetectionLocations = detectionTreeElement.getOldLibraryLocation()
					.findMostJaccardSimilars(
							clientLocationProcessedASTNode.flattenChildren().collect(Collectors.toSet()));

			for (ProcessedASTNode mostSimilarDetectionLocation : mostSimilarDetectionLocations) {
				DetectionHighlightData detectionHighlightData = new DetectionHighlightData(
						detectionTreeElement.getOldLibraryLocation(), detectionType, detectionTreeElement,
						apiMigrationParameters, mostSimilarDetectionLocation.getLocation().getNode());

				Collection<IHighlightDefinition> highlightDefinitions = highlightDefinitionsOfDetections
						.getOrDefault(detectionTreeElement, new HashSet<>());
				highlightDefinitions.add(new DetectionHighlightDefinition(detectionHighlightData));
				highlightDefinitionsOfDetections.putIfAbsent(detectionTreeElement, highlightDefinitions);
			}
		}

		Display.getDefault().asyncExec(() -> {
			CodeHighlighterModel codeHighlighterModel = new CodeHighlighterModel(DetectionHighlightDefinition.markerID);
			CodeHighlighterController codeHighlighterController = new CodeHighlighterController(this,
					codeHighlighterModel);
			codeHighlighterController.init();

			try {
				for (Entry<DetectionTreeElement, Collection<IHighlightDefinition>> entry : highlightDefinitionsOfDetections
						.entrySet()) {
					DetectionTreeElement detectionTreeElement = entry.getKey();
					Collection<IHighlightDefinition> highlightDefinitions = entry.getValue();

					for (IHighlightDefinition highlightDefinition : highlightDefinitions) {
						IMarker marker = codeHighlighterController.highlight(highlightDefinition);

						detectionTreeElement.GetEqualChildOrAdd(new DetectionOccurenceTreeElement(marker));
					}
				}

			} catch (CoreException e) {
				e.printStackTrace();
			}

			getView().show(getModel().getTreeElements());
		});

	}

	public void clear() {
		getSubControllers(CodeHighlighterController.class).forEach(Controller::dispose);
		getModel().clear();
	}

	private ProcessedAST getProcessedAST() {
		Collection<ICompilationUnit> compilationUnits = getCompilationUnits();
		ASTProcessor astProcessor = new ASTProcessor();

		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setProject(getModel().getJavaProject());
		parser.setResolveBindings(true);
		parser.createASTs(compilationUnits.toArray(new ICompilationUnit[0]), new String[0], new ASTRequestor() {

			@Override
			public void acceptAST(ICompilationUnit source, CompilationUnit ast) {
				ast.accept(astProcessor);
			}

		}, null);

		return astProcessor.getProcessedAST();
	}

	private Collection<ICompilationUnit> getCompilationUnits() {
		IJavaProject javaProject = getModel().getJavaProject();

		Collection<ICompilationUnit> compilationUnits = new ArrayList<>();
		try {
			for (IPackageFragment packageFragment : javaProject.getPackageFragments()) {
				for (ICompilationUnit compilationUnit : packageFragment.getCompilationUnits()) {
					compilationUnits.add(compilationUnit);
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}

		return compilationUnits;
	}

	@Override
	public void requestViewClose() {
		dispose();
	}

	@Override
	public void onTreeElementSelected(TreeElement treeElement) {
		if (treeElement instanceof CodeSnippetTreeElement) {
			CodeSnippetTreeElement codeSnippetTreeElement = (CodeSnippetTreeElement) treeElement;

			getView().showCodeSnippetInPreview(codeSnippetTreeElement.getCodeSnippet());
			return;
		}

		getView().showDefaultPreview();
	}

	@Override
	public void onTreeElementDoubleClicked(TreeElement treeElement) {
		if (treeElement instanceof PathPartTreeElement) {
			PathPartTreeElement pathPartTreeElement = (PathPartTreeElement) treeElement;

			PathPartLocation referenceLocation = pathPartTreeElement.getLocation();
			ASTLocation mostSimilar = referenceLocation
					.findMostJaccardSimilar(getProcessedAST().flattenChildren().parallel()).getLocation();
			routeEventToParentController(new OpenFileInEditorRequestEvent(this, mostSimilar.getNode()));
		}

		if (treeElement instanceof DetectionTreeElement) {
			DetectionTreeElement detectionTreeElement = (DetectionTreeElement) treeElement;

			TreeElement detectionTypeTreeElement = detectionTreeElement.getParent();
			PathPartTreeElement clientLocationPathPartTreeElement = (PathPartTreeElement) detectionTypeTreeElement
					.getParent();

			PathPartLocation clientLocation = clientLocationPathPartTreeElement.getLocation();
			ProcessedASTNode clientLocationProcessedASTNode = clientLocation
					.findMostJaccardSimilar(getProcessedAST().flattenChildren().parallel());

			ASTLocation mostSimilar = detectionTreeElement.getOldLibraryLocation()
					.findMostJaccardSimilar(clientLocationProcessedASTNode.flattenChildren().parallel()).getLocation();
			routeEventToParentController(new OpenFileInEditorRequestEvent(this, mostSimilar.getNode()));
		}

		if (treeElement instanceof DetectionOccurenceTreeElement) {
			DetectionOccurenceTreeElement detectionOccurenceTreeElement = (DetectionOccurenceTreeElement) treeElement;

			IMarker marker = detectionOccurenceTreeElement.getMarker();

			if (marker.exists()) {
				try {
					DetectionHighlightData detectionHighlightData = DetectionHighlightDefinition
							.getDetectionHighlightData(marker);

					ASTNode astNode = detectionHighlightData.getAstNode();

					ASTNode a = astNode;
					List<ASTNode> b = new ArrayList<>();
					while (a != null) {
						b.add(a);
						a = a.getParent();
					}

					routeEventToParentController(new OpenFileInEditorRequestEvent(this, astNode));
				} catch (CoreException e) {
					e.printStackTrace();
				}
			} else {
				MessageDialog.openError(getView().getShell(), "Error",
						"The marker for the selected occurence of a detection is no longer available.");
			}

		}
	}

	@Override
	protected void onReceiveRoutedEventFromParentController(IRoutedEvent routedEvent) {
		if (routedEvent instanceof JumpToMigrationIssueRequestEvent) {
			JumpToMigrationIssueRequestEvent event = (JumpToMigrationIssueRequestEvent) routedEvent;
			getView().select(new TreeSelection(
					new TreePath(event.getDetectionHighlightData().getTreeElement().streamFromRoot().toArray())));
			return;
		}

		if (routedEvent instanceof IgnoreOccurenceRequestEvent) {
			IgnoreOccurenceRequestEvent event = (IgnoreOccurenceRequestEvent) routedEvent;

			getModel().getRoot().streamFlattenChildren().filter(DetectionOccurenceTreeElement.class::isInstance)
					.map(DetectionOccurenceTreeElement.class::cast).filter(o -> o.getMarker().equals(event.getMarker()))
					.forEach(this::onIgnore);

			return;
		}

		super.onReceiveRoutedEventFromParentController(routedEvent);
	}

	@Override
	public void onIgnoreAllMigration() {
		removeElementsUnder(getModel().getRoot(), DetectionOccurenceTreeElement.class);
		getView().show(getModel().getTreeElements());
	}

	@Override
	public void onIgnoreAllMigrationsOfType(DetectionTypeTreeElement detectionTypeTreeElement) {
		Set<TreeElement> toBeRemoved = getModel().getRoot().streamFlattenChildren()
				.filter(DetectionTypeTreeElement.class::isInstance).map(DetectionTypeTreeElement.class::cast)
				.filter(d -> d.getType().equals(detectionTypeTreeElement.getType()))
				.flatMap(TreeElement::streamFlattenChildren).collect(Collectors.toSet());
		toBeRemoved.forEach(TreeElement::removeFromParentRecursivelyIfEmpty);
		getView().show(getModel().getTreeElements());
	}

	@Override
	public void onIgnoreAllMigrationUnder(PathPartTreeElement pathPartTreeElement) {
		removeElementsUnder(pathPartTreeElement, DetectionOccurenceTreeElement.class);
		getView().show(getModel().getTreeElements());
	}

	@Override
	public void onIgnoreAllMigrationUnder(DetectionTreeElement detectionTreeElement) {
		removeElementsUnder(detectionTreeElement, DetectionOccurenceTreeElement.class);
		getView().show(getModel().getTreeElements());
	}

	@Override
	public void onIgnoreAllMigrationUnder(DetectionTypeTreeElement detectionTypeTreeElement) {
		removeElementsUnder(detectionTypeTreeElement, DetectionOccurenceTreeElement.class);
		getView().show(getModel().getTreeElements());
	}

	@Override
	public void onIgnore(DetectionOccurenceTreeElement detectionOccurenceTreeElement) {
		removeElementsUnder(detectionOccurenceTreeElement, DetectionOccurenceTreeElement.class);
		getView().show(getModel().getTreeElements());
	}

	@Override
	public void onIgnoreAllSnippets() {
		removeElementsUnder(getModel().getRoot(), CodeSnippetTreeElement.class);
		getView().show(getModel().getTreeElements());
		getView().showDefaultPreview();
	}

	@Override
	public void onIgnoreAllSnippetsUnder(PathPartTreeElement pathPartTreeElement) {
		removeElementsUnder(pathPartTreeElement, CodeSnippetTreeElement.class);
		getView().show(getModel().getTreeElements());
		getView().showDefaultPreview();
	}

	@Override
	public void onIgnoreAllSnippetsUnder(CodeSnippetsTreeElement codeSnippetsTreeElement) {
		removeElementsUnder(codeSnippetsTreeElement, CodeSnippetTreeElement.class);
		getView().show(getModel().getTreeElements());
		getView().showDefaultPreview();
	}

	@Override
	public void onIgnore(CodeSnippetTreeElement codeSnippetTreeElement) {
		removeElementsUnder(codeSnippetTreeElement, CodeSnippetTreeElement.class);
		getView().show(getModel().getTreeElements());
		getView().showDefaultPreview();
	}

	private void removeElementsUnder(TreeElement parent, Class<?> elementClass) {
		List<TreeElement> children = parent.streamFlattenChildren().filter(elementClass::isInstance)
				.collect(Collectors.toList());
		children.forEach(TreeElement::removeFromParentRecursivelyIfEmpty);
	}

}
