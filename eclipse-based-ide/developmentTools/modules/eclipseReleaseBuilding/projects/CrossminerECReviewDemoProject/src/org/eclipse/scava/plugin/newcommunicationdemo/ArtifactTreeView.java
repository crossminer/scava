package org.eclipse.scava.plugin.newcommunicationdemo;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;

import net.apispark.webapi.representation.Artifact;
import net.apispark.webapi.representation.ArtifactList;
import net.apispark.webapi.representation.Stargazers;

public class ArtifactTreeView extends TreeViewer {

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public ArtifactTreeView(Composite parent, int style) {
		super(parent, style);
		setContentProvider(new ArtifactTreeContentProvider());
		setLabelProvider(new ArtifactTreeLabelProvider());
	}

	private class ArtifactTreeContentProvider implements ITreeContentProvider {

		@Override
		public boolean hasChildren(Object element) {
			if (element instanceof Artifact) {
				return true;
			}
			if (element instanceof TreeCollection) {
				return ((TreeCollection) element).items.length > 0;
			}
			return false;
		}

		@Override
		public Object getParent(Object element) {

			return null;
		}

		@Override
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof ArtifactList) {
				return ((ArtifactList) inputElement).toArray();
			}
			if( inputElement instanceof Artifact ) {
				return getChildren(inputElement);
			}
			return null;
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			List<Object> children = new ArrayList<>();

			if (parentElement instanceof Artifact) {
				try {
					BeanInfo beanInfo = Introspector.getBeanInfo(parentElement.getClass());
					for (PropertyDescriptor property : beanInfo.getPropertyDescriptors()) {

						if (property.getDisplayName().equals("class")) {
							continue;
						} else if (Collection.class.isAssignableFrom(property.getPropertyType())) {
							TreeCollection treeCollection = new TreeCollection();
							treeCollection.label = property.getDisplayName();
							Object items = property.getReadMethod().invoke(parentElement);
							if (items != null) {
								if (items.getClass().isArray()) {
									treeCollection.items = (Object[]) items;
								} else {
									treeCollection.items = ((Collection<?>) items).toArray();
								}
							} else {
								treeCollection.items = new Object[0];
							}
							children.add(treeCollection);
						} else if (property.getDisplayName().equals("readmeText")
								|| property.getDisplayName().equals("description")) {
							TreeCollection readMe = new TreeCollection();
							readMe.label = property.getDisplayName();
							readMe.items = ((String) property.getReadMethod().invoke(parentElement)).split("\n");
							children.add(readMe);
						} else {
							String text = property.getDisplayName() + ": "
									+ property.getReadMethod().invoke(parentElement);
							children.add(text);
						}
					}
				} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					e.printStackTrace();
				}

				return children.toArray();
			}

			if (parentElement instanceof TreeCollection) {
				return ((TreeCollection) parentElement).items;
			}

			return null;
		}
	}

	private class ArtifactTreeLabelProvider extends LabelProvider {

		@Override
		public String getText(Object element) {
			if (element instanceof Artifact) {
				return ((Artifact) element).getFullName();
			}
			if (element instanceof TreeCollection) {
				TreeCollection treeCollection = (TreeCollection) element;
				return treeCollection.label + " [" + treeCollection.items.length + "]";
			}
			if (element instanceof Stargazers) {
				Stargazers stargazer = (Stargazers) element;
				return stargazer.getLogin() + " (" + stargazer.getDatestamp() + ")";
			}
			return super.getText(element);
		}
	}

	private class TreeCollection {
		public String label;
		public Object[] items;
	}

}
