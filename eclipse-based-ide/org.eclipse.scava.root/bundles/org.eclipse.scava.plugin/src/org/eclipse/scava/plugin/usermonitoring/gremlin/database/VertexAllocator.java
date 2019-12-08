/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*    Zsolt János Szamosvölgyi
*    Endre Tamás Váradi
*    Gergõ Balogh
**********************************************************************/
package org.eclipse.scava.plugin.usermonitoring.gremlin.database;

import java.io.IOException;
import java.util.List;

import org.apache.tinkerpop.gremlin.process.traversal.Order;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.scava.plugin.properties.Properties;
import org.eclipse.scava.plugin.usermonitoring.descriptors.EdgeDescriptor;
import org.eclipse.scava.plugin.usermonitoring.descriptors.FindVertexDescriptor;
import org.eclipse.scava.plugin.usermonitoring.descriptors.InsertVertexDescriptor;
import org.eclipse.scava.plugin.usermonitoring.filters.HasLabelFilter;
import org.eclipse.scava.plugin.usermonitoring.filters.IVertexFilter;
import org.eclipse.scava.plugin.usermonitoring.filters.OutEdgeFilter;
import org.eclipse.scava.plugin.usermonitoring.filters.PropertyFilter;

public class VertexAllocator {

	private GraphTraversalSource graphTraversalSource;

	public VertexAllocator(GraphTraversalSource graphTraversalSource) {
		super();
		this.graphTraversalSource = graphTraversalSource;
	}

	public Vertex findNode(Vertex parent, IVertexFilter... filters) {

		GraphTraversal<Vertex, Vertex> v = graphTraversalSource.V(parent);

		for (IVertexFilter iNodeFilter : filters) {
			v = iNodeFilter.doFilter(v);
		}

		List<Vertex> list = v.toList();

		return list.isEmpty() ? null : list.get(0);
	}

	public Vertex findNode(IVertexFilter... filters) {

		GraphTraversal<Vertex, Vertex> v = graphTraversalSource.V();

		for (IVertexFilter iNodeFilter : filters) {
			v = iNodeFilter.doFilter(v);
		}

		List<Vertex> list = v.toList();

		return list.isEmpty() ? null : list.get(0);
	}

	public Vertex insertVertex(String label, VertexProperty... properties) {
		Vertex vertex = graphTraversalSource.addV(label).next();

		for (VertexProperty vertexProperty : properties) {
			vertex.property(vertexProperty.getProperty(), vertexProperty.getValue());
		}

		return vertex;
	}

	public void connectVertex(Vertex vertex, EdgeDescriptor... descriptors) {

		for (EdgeDescriptor edgeDescriptor : descriptors) {
			if (!edgeDescriptor.isReverse()) {
				vertex.addEdge(edgeDescriptor.getLabel(), edgeDescriptor.getConnectTo());
			} else {
				edgeDescriptor.getConnectTo().addEdge(edgeDescriptor.getLabel(), vertex);
			}
		}
	}

	public Vertex findOrCreateNode(FindVertexDescriptor findVertexDescriptor, InsertVertexDescriptor insertVertexDescriptor) {

		Vertex result = null;

		if (findVertexDescriptor.getParent() != null) {
			result = findNode(findVertexDescriptor.getParent(), findVertexDescriptor.getFilters());
		} else {
			result = findNode(findVertexDescriptor.getFilters());
		}
		if (result != null) {
			return result;
		}
		result = insertVertex(insertVertexDescriptor.getLabel(), insertVertexDescriptor.getProperties());
		connectVertex(result, insertVertexDescriptor.getEdges());
		return result;
	}

	public Vertex findFileVertex(ICompilationUnit compilationUnit) throws IOException, CoreException {
		FindVertexDescriptor findVertexDescriptor;
		InsertVertexDescriptor insertVertexDescriptor;

		Vertex vertex = findProjectVertex(compilationUnit);

		String[] packageNames = getPackages(compilationUnit);

		for (String packageName : packageNames) {

			findVertexDescriptor = new FindVertexDescriptor(vertex, new OutEdgeFilter(EdgeType.CONTAIN), new PropertyFilter("PackageName", packageName));
			insertVertexDescriptor = new InsertVertexDescriptor("resource",
					new VertexProperty[] { new VertexProperty("VertexType", VertexType.PACKAGE), new VertexProperty("PackageName", packageName) },
					new EdgeDescriptor(EdgeType.CONTAIN, vertex, true));
			vertex = findOrCreateNode(findVertexDescriptor, insertVertexDescriptor);
		}

		String packageDeclaration = getPackageDeclaration(compilationUnit);
		String fullQualifiedName = packageDeclaration + "." + compilationUnit.getElementName();

		findVertexDescriptor = new FindVertexDescriptor(vertex, new OutEdgeFilter(EdgeType.CONTAIN), new HasLabelFilter("resource"),
				new PropertyFilter("VertexType", VertexType.FILE), new PropertyFilter("Title", fullQualifiedName));
		insertVertexDescriptor = new InsertVertexDescriptor("resource",
				new VertexProperty[] { new VertexProperty("VertexType", VertexType.FILE), new VertexProperty("Title", fullQualifiedName) },
				new EdgeDescriptor(EdgeType.CONTAIN, vertex, true));

		vertex = findOrCreateNode(findVertexDescriptor, insertVertexDescriptor);

		return vertex;
	}

	public Vertex findProjectVertex(ICompilationUnit compilationUnit) throws IOException, CoreException {

		String projectId = compilationUnit.getJavaProject().getProject().getPersistentProperty(Properties.PROJECT_GITHUB_URL);
		String projectName = compilationUnit.getJavaProject().getProject().getName();

		FindVertexDescriptor findVertexDescriptor = new FindVertexDescriptor(new HasLabelFilter("resource"), new PropertyFilter("VertexType", VertexType.PROJECT),
				new PropertyFilter("ProjectId", projectId));
		InsertVertexDescriptor insertVertexDescriptor = new InsertVertexDescriptor("resource", new VertexProperty("VertexType", VertexType.PROJECT),
				new VertexProperty("ProjectName", projectName), new VertexProperty("ProjectId", projectId));
		Vertex vertex = findOrCreateNode(findVertexDescriptor, insertVertexDescriptor);
		return vertex;
	}
	
	public Vertex findSearchStringVertex(String searchString) {
		FindVertexDescriptor findVertexDescriptor = new FindVertexDescriptor(new HasLabelFilter("resource"), new PropertyFilter("VertexType", VertexType.SEARCH_STRING),new PropertyFilter("SearchString", searchString));
		InsertVertexDescriptor insertVertexDescriptor = new InsertVertexDescriptor("resource", new VertexProperty("VertexType", VertexType.SEARCH_STRING), new VertexProperty("SearchString", searchString));
		Vertex vertex = findOrCreateNode(findVertexDescriptor, insertVertexDescriptor);
		return vertex;
		
	}

	public Vertex findProjectVertex(IJavaProject project) throws IOException, CoreException {
		String projectId = project.getProject().getPersistentProperty(Properties.PROJECT_GITHUB_URL);
		String projectName = project.getJavaProject().getProject().getName();

		FindVertexDescriptor findVertexDescriptor = new FindVertexDescriptor(new HasLabelFilter("resource"), new PropertyFilter("VertexType", VertexType.PROJECT),
				new PropertyFilter("ProjectId", projectId));
		InsertVertexDescriptor insertVertexDescriptor = new InsertVertexDescriptor("resource", new VertexProperty("VertexType", VertexType.PROJECT),
				new VertexProperty("ProjectName", projectName), new VertexProperty("ProjectId", projectId));
		Vertex vertex = findOrCreateNode(findVertexDescriptor, insertVertexDescriptor);
		return vertex;
	}

	public Vertex findPartVertex(String partName) throws IOException {

		FindVertexDescriptor findVertexDescriptor = new FindVertexDescriptor(new HasLabelFilter("resource"), new PropertyFilter("VertexType", VertexType.PART),
				new PropertyFilter("Title", partName));
		InsertVertexDescriptor insertVertexDescriptor = new InsertVertexDescriptor("resource", new VertexProperty("VertexType", VertexType.PART),
				new VertexProperty("Title", partName));
		Vertex vertex = findOrCreateNode(findVertexDescriptor, insertVertexDescriptor);
		return vertex;
	}

	public Vertex findWindowVertex(String windowName) throws IOException {

		FindVertexDescriptor findVertexDescriptor = new FindVertexDescriptor(new HasLabelFilter("resource"), new PropertyFilter("VertexType", VertexType.WINDOW),
				new PropertyFilter("Title", windowName));
		InsertVertexDescriptor insertVertexDescriptor = new InsertVertexDescriptor("resource", new VertexProperty("VertexType", VertexType.WINDOW),
				new VertexProperty("Title", windowName));
		Vertex vertex = findOrCreateNode(findVertexDescriptor, insertVertexDescriptor);
		return vertex;
	}

	private String[] getPackages(ICompilationUnit compilationUnit) throws JavaModelException {
		IPackageDeclaration[] packageDeclarations = compilationUnit.getPackageDeclarations();
		String packageDeclaration = "";
		for (IPackageDeclaration iPackageDeclaration : packageDeclarations) {
			packageDeclaration = iPackageDeclaration.getElementName();
		}
		String[] packageNames = packageDeclaration.split("\\.");
		return packageNames;
	}

	private String getPackageDeclaration(ICompilationUnit compilationUnit) throws JavaModelException {
		IPackageDeclaration[] packageDeclarations = compilationUnit.getPackageDeclarations();
		String packageDeclaration = "";
		for (IPackageDeclaration iPackageDeclaration : packageDeclarations) {
			packageDeclaration = iPackageDeclaration.getElementName();
		}

		return packageDeclaration;
	}

	public Vertex getFirstEvent() {

		List<Vertex> list = graphTraversalSource.V().hasLabel("event").order().by("TimeStamp", Order.incr).limit(1).toList();

		return list.isEmpty() ? null : list.get(0);
	}

	public Vertex getLastEvent() {
		List<Vertex> list = graphTraversalSource.V().hasLabel("event").order().by("TimeStamp", Order.decr).limit(1).toList();

		return list.isEmpty() ? null : list.get(0);
	}

}
