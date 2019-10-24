/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.treelements;

import java.util.List;
import java.util.stream.Stream;

import org.eclipse.jface.viewers.StyledString;
import org.eclipse.scava.plugin.collections.AutoSortedArrayList;

public abstract class TreeElement implements Comparable<TreeElement> {
	private TreeElement parent;
	private final List<TreeElement> children = new AutoSortedArrayList<>();
	private boolean hasDetection;
	private int orderBias;

	public TreeElement() {
		super();
	}

	public <TreeElementType extends TreeElement> TreeElementType GetEqualChildOrAdd(TreeElementType element) {
		TreeElementType foundChild = findChildByLabel(element.getLabel());

		if (foundChild == null) {
			((TreeElement) element).setParent(this);
			children.add(element);

			if (element instanceof DetectionTypeTreeElement) {
				hasDetection = true;
			}

			return element;
		}

		return foundChild;
	}

	protected void setOrderBias(int orderBias) {
		this.orderBias = orderBias;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		return result;
	}

	public TreeElement getParent() {
		return parent;
	}

	public TreeElement[] getChildren() {
		return children.toArray(new TreeElement[0]);
	}

	public boolean hasChildren() {
		return !children.isEmpty();
	}

	public final String getLabel() {
		return getStyledString().getString();
	}

	@SuppressWarnings("unchecked")
	private <TreeElementType extends TreeElement> TreeElementType findChildByLabel(String label) {
		for (TreeElement child : children) {
			if (child.getLabel().equals(label)) {
				return (TreeElementType) child;
			}
		}

		return null;
	}

	public Stream<TreeElement> streamFromRoot() {
		if (parent != null) {
			return Stream.concat(parent.streamFromRoot(), Stream.of(this));
		}
		return Stream.empty();
	}

	public Stream<TreeElement> streamFlattenChildren() {
		Stream<TreeElement> stream = Stream.empty();

		for (TreeElement child : children) {
			stream = Stream.concat(stream, child.streamFlattenChildren());
		}

		return Stream.concat(stream, Stream.of(this));
	}

	public abstract StyledString getStyledString();

	public void removeAllChildren() {
		children.forEach(c -> c.setParent(null));
		children.clear();
	}

	public void removeChild(TreeElement element) {
		element.setParent(null);
		children.remove(element);
	}

	protected void setParent(TreeElement element) {
		parent = element;
	}

	public void removeChildRecursivelyIfEmpty(TreeElement element) {
		removeChild(element);

		if (children.isEmpty() && parent != null) {
			parent.removeChildRecursivelyIfEmpty(this);
		}
	}

	public void removeFromParentRecursivelyIfEmpty() {
		if (parent != null) {
			parent.removeChildRecursivelyIfEmpty(this);
		}
	}

	public boolean hasDetection() {
		return hasDetection;
	}

	@Override
	public int compareTo(TreeElement o) {
		return (int) Math.signum(getLabel().compareTo(o.getLabel()) + orderBias - o.orderBias);
	}

	@Override
	public String toString() {
		return getLabel();
	}
}
