/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.coderecommendation.results;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.scava.plugin.Activator;
import org.eclipse.scava.plugin.coderecommendation.CodeRecommendation;
import org.eclipse.scava.plugin.coderecommendation.CodeRecommendationRequest;
import org.eclipse.scava.plugin.coderecommendation.CodeRecommendationTarget;
import org.eclipse.scava.plugin.coderecommendation.ICodeRecommendationElement;
import org.eclipse.scava.plugin.mvc.view.CompositeView;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

public class CodeRecommendationResultsView extends CompositeView<ICodeRecommendationResultsViewEventListener> {
	private TreeContentProvider contentProvider;
	private TreeLabelProvider labelProvider;
	private TreeViewer treeViewer;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public CodeRecommendationResultsView() {
		super(SWT.NONE);
		setBackgroundMode(SWT.INHERIT_FORCE);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setLayout(new FillLayout(SWT.HORIZONTAL));

		treeViewer = new TreeViewer(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		treeViewer.setAutoExpandLevel(3);
		Tree tree = treeViewer.getTree();
		tree.setHeaderVisible(true);

		TreeViewerColumn treeViewerColumn = new TreeViewerColumn(treeViewer, SWT.NONE);
		TreeColumn trclmnCodeRecommendations = treeViewerColumn.getColumn();
		trclmnCodeRecommendations.setResizable(false);
		trclmnCodeRecommendations.setText("Code recommendations");

		treeViewer.setContentProvider(contentProvider = new TreeContentProvider());
		treeViewer.setLabelProvider(new DelegatingStyledCellLabelProvider(labelProvider = new TreeLabelProvider()));

		tree.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TreeItem item = (TreeItem) e.item;
				Object data = item.getData();

				if (data instanceof ICodeRecommendationElement) {
					ICodeRecommendationElement element = (ICodeRecommendationElement) data;
					eventManager.invoke(l -> l.onCodeRecommendationSelected(element));
				}
			}
		});

		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				TreeViewer viewer = (TreeViewer) event.getViewer();
				IStructuredSelection thisSelection = (IStructuredSelection) event.getSelection();
				Object selectedNode = thisSelection.getFirstElement();
				if (selectedNode instanceof CodeRecommendationTarget) {
					CodeRecommendationTarget target = (CodeRecommendationTarget) selectedNode;

					eventManager.invoke(l -> l.onTargetDoubleClicked(target));
				}
			}
		});

		createContextMenu(treeViewer);
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

		if (selected instanceof CodeRecommendation) {
			CodeRecommendation codeRecommendation = (CodeRecommendation) selected;

			contextMenu.add(new Action("Drop recommendation", ImageDescriptor.createFromImage(ResourceManager
					.getPluginImage("org.eclipse.scava.plugin", "icons/control/drop_recommendation_v2.png"))) {
				@Override
				public void run() {
					eventManager.invoke(l -> l.onDrop(codeRecommendation));
				}
			});
		}

		if (selected instanceof CodeRecommendationRequest) {
			CodeRecommendationRequest request = (CodeRecommendationRequest) selected;

			contextMenu.add(new Action("Drop recommendations for this request",
					ImageDescriptor.createFromImage(ResourceManager.getPluginImage("org.eclipse.scava.plugin",
							"icons/control/drop_recommendation_history.png"))) {
				@Override
				public void run() {
					eventManager.invoke(l -> l.onDrop(request));
				}
			});
		}

		if (selected instanceof CodeRecommendationTarget) {
			CodeRecommendationTarget target = (CodeRecommendationTarget) selected;

			contextMenu.add(new Action("Drop recommendations for this target",
					ImageDescriptor.createFromImage(ResourceManager.getPluginImage("org.eclipse.scava.plugin",
							"icons/control/drop_recommendation_target.png"))) {
				@Override
				public void run() {
					eventManager.invoke(l -> l.onDrop(target));
				}
			});
		}

		contextMenu.add(new Action("Drop all recommendations", ImageDescriptor.createFromImage(ResourceManager
				.getPluginImage("org.eclipse.scava.plugin", "icons/control/drop_all_recommendation.png"))) {
			@Override
			public void run() {
				eventManager.invoke(l -> l.onDropAll());
			}
		});
	}

	public void showRecommendations(Collection<CodeRecommendationTarget> recommendations) {
		showRecommendations(recommendations, null);
	}

	public void showRecommendations(Collection<CodeRecommendationTarget> recommendations,
			CodeRecommendationRequest select) {

		List<TreePath> alreadyExpandedTreePaths = Arrays.asList(treeViewer.getExpandedTreePaths());
		List<Object> alreadyExpandedElements = Arrays.asList(treeViewer.getExpandedElements());
		treeViewer.setInput(recommendations.toArray());

		List<TreePath> expandedTreePaths = new ArrayList<>(alreadyExpandedTreePaths);
		List<Object> expandedElements = new ArrayList<>(alreadyExpandedElements);

		if (select != null) {
			expandedTreePaths.add(new TreePath(new Object[] { select.getTarget(), select }));

			expandedElements.add(select.getTarget());
			expandedElements.add(select);
		}

		treeViewer.setExpandedElements(expandedElements.toArray());
		treeViewer.setExpandedTreePaths(expandedTreePaths.toArray(new TreePath[0]));

		if (select != null) {
			treeViewer.setSelection(new StructuredSelection(select));
			eventManager.invoke(l -> l.onCodeRecommendationSelected(select));
		}

		Display.getDefault().asyncExec(() -> {
			TreeColumn[] columns = treeViewer.getTree().getColumns();
			for (TreeColumn column : columns) {
				column.pack();
			}
		});
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	private class TreeContentProvider implements ITreeContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			return (Object[]) inputElement;
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof CodeRecommendationTarget) {
				CodeRecommendationTarget target = (CodeRecommendationTarget) parentElement;
				return target.getCodeRecommendationsRequests().toArray();
			}

			if (parentElement instanceof CodeRecommendationRequest) {
				CodeRecommendationRequest request = (CodeRecommendationRequest) parentElement;
				return request.getCodeRecommendations().toArray();
			}

			return null;
		}

		@Override
		public Object getParent(Object element) {
			if (element instanceof CodeRecommendation) {
				CodeRecommendation recommendation = (CodeRecommendation) element;
				return recommendation.getCodeRecommendationRequest();
			}

			if (element instanceof CodeRecommendationRequest) {
				CodeRecommendationRequest request = (CodeRecommendationRequest) element;
				return request.getTarget();
			}

			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			if (element instanceof CodeRecommendationTarget) {
				CodeRecommendationTarget target = (CodeRecommendationTarget) element;
				return !target.getCodeRecommendationsRequests().isEmpty();
			}

			if (element instanceof CodeRecommendationRequest) {
				CodeRecommendationRequest request = (CodeRecommendationRequest) element;
				return !request.getCodeRecommendations().isEmpty();
			}

			return false;
		}

	}

	private class TreeLabelProvider extends LabelProvider implements IStyledLabelProvider {

		@Override
		public StyledString getStyledText(Object element) {
			if (element instanceof CodeRecommendation) {
				CodeRecommendation recommendation = (CodeRecommendation) element;

				StyledString styledString = new StyledString(recommendation.getRecommendation().getPattern());
				return styledString;
			}

			if (element instanceof CodeRecommendationRequest) {
				CodeRecommendationRequest request = (CodeRecommendationRequest) element;

				StyledString styledString = new StyledString(request.toString());
				styledString.append(" : " + request.getCodeRecommendations().size() + " suggestion(s)",
						StyledString.COUNTER_STYLER);
				return styledString;
			}

			if (element instanceof CodeRecommendationTarget) {
				CodeRecommendationTarget target = (CodeRecommendationTarget) element;

				StyledString styledString = new StyledString(target.getFile().getFullPath().toOSString());
				styledString.append(" : " + target.getCodeRecommendationsRequests().size() + " request(s)",
						StyledString.COUNTER_STYLER);
				return styledString;
			}

			return null;
		}

		@Override
		public Image getImage(Object element) {
			if (element instanceof CodeRecommendation) {
				return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/control/pattern_2_16x16.png");
			}

			if (element instanceof CodeRecommendationRequest) {
				return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/control/pattern_3_16x16.png");
			}

			if (element instanceof CodeRecommendationTarget) {
				return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/control/java_file_16x16.png");
			}

			return super.getImage(element);
		}
	}
}
