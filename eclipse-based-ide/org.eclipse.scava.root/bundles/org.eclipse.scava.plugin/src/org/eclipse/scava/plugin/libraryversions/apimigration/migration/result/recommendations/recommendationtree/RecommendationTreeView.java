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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.treelements.CodeSnippetTreeElement;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.treelements.CodeSnippetsTreeElement;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.treelements.DetectionOccurenceTreeElement;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.treelements.DetectionTreeElement;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.treelements.DetectionTypeTreeElement;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.treelements.PathPartTreeElement;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.treelements.TreeElement;
import org.eclipse.scava.plugin.mvc.view.CompositeView;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

public class RecommendationTreeView extends CompositeView<IRecommendationTreeViewEventListener> {
	private Tree tree;
	private TreeViewer treeViewer;
	private TreeContentProvider contentProvider;
	private TreeLabelProvider labelProvider;
	private Text textCodeSnippetPreview;
	private Composite previewComposite;
	private Composite previewDefaultComposite;
	private Composite previewCodeSnippetComposite;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public RecommendationTreeView() {
		super(SWT.None);
		setLayout(new FillLayout(SWT.HORIZONTAL));

		SashForm sashForm = new SashForm(this, SWT.NONE);

		treeViewer = new TreeViewer(sashForm, SWT.BORDER);
		tree = treeViewer.getTree();
		tree.setHeaderVisible(true);

		TreeViewerColumn treeViewerColumn = new TreeViewerColumn(treeViewer, SWT.NONE);
		TreeColumn trclmnDetections = treeViewerColumn.getColumn();
		trclmnDetections.setText("Recommendations");

		previewComposite = new Composite(sashForm, SWT.NONE);
		previewComposite.setBackgroundMode(SWT.INHERIT_FORCE);
		previewComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		previewComposite.setLayout(new StackLayout());

		previewDefaultComposite = new Composite(previewComposite, SWT.NONE);
		previewDefaultComposite.setLayout(new GridLayout(1, false));

		Label lblSelectARecommended = new Label(previewDefaultComposite, SWT.WRAP | SWT.CENTER);
		lblSelectARecommended.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		lblSelectARecommended.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblSelectARecommended.setText("Select a recommended code snippet to list here");

		previewCodeSnippetComposite = new Composite(previewComposite, SWT.NONE);
		previewCodeSnippetComposite.setLayout(new GridLayout(1, false));

		Label lblPreviewOfThe = new Label(previewCodeSnippetComposite, SWT.NONE);
		lblPreviewOfThe.setText("Preview of the selected code snippet");

		Label label = new Label(previewCodeSnippetComposite, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setText("New Label");

		textCodeSnippetPreview = new Text(previewCodeSnippetComposite,
				SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		textCodeSnippetPreview.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		textCodeSnippetPreview.setSize(224, 300);

		treeViewer.setContentProvider(contentProvider = new TreeContentProvider());
		treeViewer.setLabelProvider(new DelegatingStyledCellLabelProvider(labelProvider = new TreeLabelProvider()));

		tree.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TreeItem item = (TreeItem) e.item;
				Object data = item.getData();

				if (data instanceof TreeElement) {
					TreeElement element = (TreeElement) data;
					eventManager.invoke(l -> l.onTreeElementSelected(element));
				}
			}
		});

		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection thisSelection = (IStructuredSelection) event.getSelection();
				Object selectedNode = thisSelection.getFirstElement();
				if (selectedNode instanceof TreeElement) {
					TreeElement treeElement = (TreeElement) selectedNode;

					eventManager.invoke(l -> l.onTreeElementDoubleClicked(treeElement));
				}
			}
		});

		tree.addListener(SWT.Expand, new Listener() {

			@Override
			public void handleEvent(Event event) {
				Display.getDefault().asyncExec(() -> {

					TreeElement element = (TreeElement) event.item.getData();

					List<TreeElement> oneChildElements = new ArrayList<>();
					while (element.getChildren().length == 1) {
						oneChildElements.add(element);
						TreeElement firstChild = element.getChildren()[0];
						element = firstChild;
					}
					oneChildElements.add(element);

					List<TreePath> expandedTreePaths = new ArrayList<>(
							Arrays.asList(treeViewer.getExpandedTreePaths()));
					List<Object> expandedElements = new ArrayList<>(Arrays.asList(treeViewer.getExpandedElements()));

					List<TreeElement> pathElements = oneChildElements.get(oneChildElements.size() - 1).streamFromRoot()
							.collect(Collectors.toList());
					for (int i = 0; i < pathElements.size(); i++) {
						expandedTreePaths.add(new TreePath(pathElements.subList(0, i + 1).toArray()));
					}

					oneChildElements.forEach(expandedElements::add);

					treeViewer.setExpandedElements(expandedElements.toArray());
					treeViewer.setExpandedTreePaths(expandedTreePaths.toArray(new TreePath[0]));

					TreeColumn[] columns = treeViewer.getTree().getColumns();
					for (TreeColumn column : columns) {
						column.pack();
					}

					event.doit = false;
				});
			}
		});

		sashForm.setWeights(new int[] { 1, 1 });

		showDefaultPreview();

		createContextMenu(treeViewer);
	}

	public void show(Object[] input) {
		show(input, null);
	}

	public void show(Object[] input, TreeElement select) {

		List<TreePath> alreadyExpandedTreePaths = Arrays.asList(treeViewer.getExpandedTreePaths());
		List<Object> alreadyExpandedElements = Arrays.asList(treeViewer.getExpandedElements());
		treeViewer.setInput(input);

		List<TreePath> expandedTreePaths = new ArrayList<>(alreadyExpandedTreePaths);
		List<Object> expandedElements = new ArrayList<>(alreadyExpandedElements);

		if (select != null) {
			expandedTreePaths.add(new TreePath(select.streamFromRoot().toArray()));
			select.streamFromRoot().forEach(expandedElements::add);
		}

		treeViewer.setExpandedElements(expandedElements.toArray());
		treeViewer.setExpandedTreePaths(expandedTreePaths.toArray(new TreePath[0]));

		if (select != null) {
			treeViewer.setSelection(new StructuredSelection(select));
			eventManager.invoke(l -> l.onTreeElementSelected(select));
		}

		TreeColumn[] columns = treeViewer.getTree().getColumns();
		for (TreeColumn column : columns) {
			column.pack();
		}

		requestLayout();
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	private void createContextMenu(Viewer viewer) {
		MenuManager contextMenu = new MenuManager("#ViewerMenu"); //$NON-NLS-1$
		contextMenu.setRemoveAllWhenShown(true);
		contextMenu.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager mgr) {
				fillContextMenu(mgr);
			}
		});

		Menu menu = contextMenu.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
	}

	private void fillContextMenu(IMenuManager contextMenu) {
		contextMenu.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));

		Object selected = ((IStructuredSelection) treeViewer.getSelection()).getFirstElement();

		if (selected instanceof PathPartTreeElement) {
			PathPartTreeElement pathPartTreeElement = (PathPartTreeElement) selected;

			contextMenu.add(new Action("Ignore all migration issues under tree element",
					ImageDescriptor.createFromImage(ResourceManager.getPluginImage("org.eclipse.scava.plugin",
							"icons/control/drop_recommendation_v2.png"))) {
				@Override
				public void run() {
					eventManager.invoke(l -> l.onIgnoreAllMigrationUnder(pathPartTreeElement));
				}
			});

			contextMenu.add(new Action("Ignore all code snippets under tree element",
					ImageDescriptor.createFromImage(ResourceManager.getPluginImage("org.eclipse.scava.plugin",
							"icons/control/drop_recommendation_v2.png"))) {
				@Override
				public void run() {
					eventManager.invoke(l -> l.onIgnoreAllSnippetsUnder(pathPartTreeElement));
				}
			});
		}

		if (selected instanceof DetectionTypeTreeElement) {
			DetectionTypeTreeElement detectionTypeTreeElement = (DetectionTypeTreeElement) selected;
			contextMenu.add(new Action("Ignore migration issues under tree element",
					ImageDescriptor.createFromImage(ResourceManager.getPluginImage("org.eclipse.scava.plugin",
							"icons/control/drop_recommendation_v2.png"))) {
				@Override
				public void run() {
					eventManager.invoke(l -> l.onIgnoreAllMigrationUnder(detectionTypeTreeElement));
				}
			});

			contextMenu.add(new Action("Ignore all " + detectionTypeTreeElement.getType() + " type of migration issues",
					ImageDescriptor.createFromImage(ResourceManager.getPluginImage("org.eclipse.scava.plugin",
							"icons/control/drop_recommendation_v2.png"))) {
				@Override
				public void run() {
					eventManager.invoke(l -> l.onIgnoreAllMigrationsOfType(detectionTypeTreeElement));
				}
			});
		}

		if (selected instanceof DetectionTreeElement) {
			DetectionTreeElement detectionTreeElement = (DetectionTreeElement) selected;
			contextMenu.add(new Action("Ignore migration issue", ImageDescriptor.createFromImage(ResourceManager
					.getPluginImage("org.eclipse.scava.plugin", "icons/control/drop_recommendation_v2.png"))) {
				@Override
				public void run() {
					eventManager.invoke(l -> l.onIgnoreAllMigrationUnder(detectionTreeElement));
				}
			});
		}

		if (selected instanceof DetectionOccurenceTreeElement) {
			DetectionOccurenceTreeElement deteOccurenceTreeElement = (DetectionOccurenceTreeElement) selected;
			contextMenu.add(new Action("Ignore occurence", ImageDescriptor.createFromImage(ResourceManager
					.getPluginImage("org.eclipse.scava.plugin", "icons/control/drop_recommendation_v2.png"))) {
				@Override
				public void run() {
					eventManager.invoke(l -> l.onIgnore(deteOccurenceTreeElement));
				}
			});
		}

		if (selected instanceof CodeSnippetsTreeElement) {
			CodeSnippetsTreeElement codeSnippetsTreeElement = (CodeSnippetsTreeElement) selected;

			contextMenu.add(new Action("Ignore code snippets", ImageDescriptor.createFromImage(ResourceManager
					.getPluginImage("org.eclipse.scava.plugin", "icons/control/drop_recommendation_v2.png"))) {
				@Override
				public void run() {
					eventManager.invoke(l -> l.onIgnoreAllSnippetsUnder(codeSnippetsTreeElement));
				}
			});
		}

		if (selected instanceof CodeSnippetTreeElement) {
			CodeSnippetTreeElement codeSnippetTreeElement = (CodeSnippetTreeElement) selected;

			contextMenu.add(new Action("Ignore code snippet", ImageDescriptor.createFromImage(ResourceManager
					.getPluginImage("org.eclipse.scava.plugin", "icons/control/drop_recommendation_v2.png"))) {
				@Override
				public void run() {
					eventManager.invoke(l -> l.onIgnore(codeSnippetTreeElement));
				}
			});
		}

		contextMenu.add(new Action("Ignore all migration issues", ImageDescriptor.createFromImage(ResourceManager
				.getPluginImage("org.eclipse.scava.plugin", "icons/control/drop_all_recommendation.png"))) {
			@Override
			public void run() {
				eventManager.invoke(l -> l.onIgnoreAllMigration());
			}
		});

		contextMenu.add(new Action("Ignore all code snippets", ImageDescriptor.createFromImage(ResourceManager
				.getPluginImage("org.eclipse.scava.plugin", "icons/control/drop_all_recommendation.png"))) {
			@Override
			public void run() {
				eventManager.invoke(l -> l.onIgnoreAllSnippets());
			}
		});
	}

	private class TreeContentProvider implements ITreeContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			return (Object[]) inputElement;
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof TreeElement) {
				TreeElement treeElement = (TreeElement) parentElement;
				return treeElement.getChildren();
			}

			return null;
		}

		@Override
		public Object getParent(Object element) {
			if (element instanceof TreeElement) {
				TreeElement treeElement = (TreeElement) element;
				return treeElement.getParent();
			}

			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			if (element instanceof TreeElement) {
				TreeElement treeElement = (TreeElement) element;
				return treeElement.hasChildren();
			}

			return false;
		}

	}

	private class TreeLabelProvider extends LabelProvider implements IStyledLabelProvider {

		@Override
		public StyledString getStyledText(Object element) {
			if (element instanceof TreeElement) {
				TreeElement treeElement = (TreeElement) element;
				return treeElement.getStyledString();
			}

			return null;
		}

		@Override
		public Image getImage(Object element) {
			return super.getImage(element);
		}

	}

	public void select(ISelection selection) {
		treeViewer.setSelection(selection, true);
	}

	public void showCodeSnippetInPreview(String snippet) {
		StackLayout layout = (StackLayout) previewComposite.getLayout();
		layout.topControl = previewCodeSnippetComposite;

		textCodeSnippetPreview.setText(snippet);

		Display.getDefault().syncExec(() -> {
			previewComposite.pack();
			previewComposite.requestLayout();
		});
	}

	public void showDefaultPreview() {
		StackLayout layout = (StackLayout) previewComposite.getLayout();
		layout.topControl = previewDefaultComposite;

		Display.getDefault().syncExec(() -> {
			previewComposite.pack();
			previewComposite.requestLayout();
		});
	}
}
