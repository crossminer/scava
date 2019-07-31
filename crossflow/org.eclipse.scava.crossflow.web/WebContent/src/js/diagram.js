/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Konstantinos Barmpis - adaption for CROSSFLOW
 *     Jonathan Co - adaption for command line execution
 *     Horacio Hoyos Rodriguez - Improve UI
 ******************************************************************************/

/**
 * This file contains a set of functions to create elements in the mxGraph to
 * represent the elements from the crossflow process.
 * There are separate functions to create different type of graphs and a function to craete edges.
 */

// FIXME This file should be auto-generated from the metamodel. That way, if new constructs are added
// the web UI will need little maintenance. Further, we can use the eugenia annotations to use the
// correct images.

/**
  * Read the model.json and create the corresponding vertices and edges.
  */
 function readModel(model, graph, parent) {
	 loadStencils();
	 let vertexMap = new Map();
	 for (let i = 0; i < model.vertices.length; i++ ) {
		 const v = model.vertices[i];
		 switch(v.type) {
		 case "Task":
			 vertexMap.set(v.label, createTaskVertex(graph, parent, v.label, v.x, v.y, v.width, v.height));
			 break;
		 case "Queue":
			 vertexMap.set(v.label, createQueueVertex(graph, parent, v.label, v.x, v.y, v.width, v.height));
			 break;
		 case "Source":
			 vertexMap.set(v.label, createSourceVertex(graph, parent, v.label, v.x, v.y, v.width, v.height));
			 break;
		 case "Sink":
			 vertexMap.set(v.label, createSinkVertex(graph, parent, v.label, v.x, v.y, v.width, v.height));
			 break;
		 }
	 }
	 for (let i = 0; i < model.edges.length; i++ ) {
		 const e = model.edges[i];
		 // Remove if once we have all vertices!!!
		 if (vertexMap.has(e.source) && vertexMap.has(e.target)) {
			 const ge = graph.insertEdge(parent, null, '',
				 vertexMap.get(e.source),
				 vertexMap.get(e.target),
				 "strokeColor=black;" + "endArrow=" + e.endArrow + ";");
		 }
	 }
	 return model.auto;
 }

/**
 * Create a Task Vertex.
 * The vertex's id is "task_" + label
 *
 * @param graph						the graph that will contain the vertex
 * @param parent					mxCell that specifies the parent of the new vertex.
 * @param label						the label to use for the vertex
 * @param x							integer that defines the x coordinate of the vertex.
 * @param y							integer that defines the y coordinate of the vertex.
 * @param width						integer that defines the width of the vertex.
 * @param height					integer that defines the height of the vertex.
 * @returns the new vertex
 */
function createTaskVertex(graph, parent, label, x, y, width, height) {
	const id = 'task_' + label;
	//console.log("createTaskVertex " + id);
	const v = createVertex(
		graph,
		parent,
		id,
		label,
		x,
		y,
		width,
		height,
		'shape=Task;fontSize=12;fontColor=black;strokeColor=black;fillColor=#ffffff');
	if ((width === -1) || (height === -1)) {
		graph.autoSizeCell(v, true);
	}
	graph.updateCellSize(v, true);
	return v;
}

/**
 * Create a Queue Vertex.
 * The vertex's id is "stream_" + label
 *
 * @param graph						the graph that will contain the vertex
 * @param parent					mxCell that specifies the parent of the new vertex.
 * @param label						the label to use for the vertex
 * @param x							integer that defines the x coordinate of the vertex.
 * @param y							integer that defines the y coordinate of the vertex.
 * @param width						integer that defines the width of the vertex.
 * @param height					integer that defines the height of the vertex.
 * @returns the new vertex
 */
function createQueueVertex(graph, parent, label, x, y, width, height) {
	const id = 'stream_' + label;
	const trail = " ".repeat(label.length / 4);
	//console.log("createQueueVertex "+ id);
	const v = createVertex(
		graph,
		parent,
		id,
		label + trail,
		x,
		y,
		width,
		height,
		'shape=Direct Data;fontSize=12;fontColor=black;strokeColor=black;fillColor=#ffffff');
	if ((width === -1) || (height === -1)) {
		graph.autoSizeCell(v, true);
	}
	graph.updateCellSize(v, true);
	// TODO I htink we need to do this in an event after the graph has been drawn
//	console.log("Graph view", graph.getView());
//	var state = graph.getView().getCellStates(v);
//	console.log(state);
//	console.log(state.width, state.height);
	return v;
}


/**
 * Create a Source Vertex.
 * The vertex's id is "source_" + label
 *
 * @param graph						the graph that will contain the vertex
 * @param parent					mxCell that specifies the parent of the new vertex.
 * @param label						the label to use for the vertex
 * @param x							integer that defines the x coordinate of the vertex.
 * @param y							integer that defines the y coordinate of the vertex.
 * @param width						integer that defines the width of the vertex.
 * @param height					integer that defines the height of the vertex.
 * @returns the new vertex
 */
function createSourceVertex(graph, parent, label, x, y, width, height) {
	const id = 'source_' + label;
	//console.log("createSourceVertex "+ id);
	const v = createVertex(
		graph,
		parent,
		id,
		label,
		x,
		y,
		width,
		height,
		'shape=Source;fontSize=12;fontColor=black;strokeColor=black;fillColor=#ffffff');
	if ((width === -1) || (height === -1)) {
		graph.autoSizeCell(v, true);
	}
	graph.updateCellSize(v, true);
	return v;
}

/**
 * Create a Sink Vertex.
 * The vertex's id is "source_" + label
 *
 * @param graph						the graph that will contain the vertex
 * @param parent					mxCell that specifies the parent of the new vertex.
 * @param label						the label to use for the vertex
 * @param x							integer that defines the x coordinate of the vertex.
 * @param y							integer that defines the y coordinate of the vertex.
 * @param width						integer that defines the width of the vertex.
 * @param height					integer that defines the height of the vertex.
 * @returns the new vertex
 */
function createSinkVertex(graph, parent, label, x, y, width, height) {
	const id = 'sink_' + label;
	//console.log("createSinkVertex "+ id);
	const v = createVertex(
		graph,
		parent,
		id,
		label,
		x,
		y,
		width,
		height,
		'shape=Sink;fontSize=12;fontColor=black;strokeColor=black;fillColor=#ffffff');
	if ((width === -1) || (height === -1)) {
		graph.autoSizeCell(v, true);
	}
	graph.updateCellSize(v, true);
	return v;
}

/**
 * Create  Vertex
 * @param graph						the graph that will contain the vertex
 * @param parent					mxCell that specifies the parent of the new vertex.
 * @param id						the vertex id
 * @param value						the label to use for the vertex
 * @param x							integer that defines the x coordinate of the vertex.
 * @param y							integer that defines the y coordinate of the vertex.
 * @param width						integer that defines the width of the vertex.
 * @param height					integer that defines the height of the vertex.
 * @returns
 */
function createVertex(graph, parent, id, value, x, y, width, height, style) {
	const v = graph.insertVertex(parent, id, value, x, y, width, height);
	graph.model.setStyle(v, style);
    return v;
}


/**
 * Add an edge between two vertices.
 * @param source
 * @param target
 * @param graph
 * @param parent
 * @returns
 */
function link(source, target, graph, parent) {
	return graph.insertEdge(parent, null, '', source, target, "strokeColor=black");
}

/**
 * Load the stencils to use for drawing the crossflow diagram.
 * Currently it uses the flowchart stencil from mxGraph, but we need to create
 * our own stencils so Eugenia and Web UI look similar.
 */
function loadStencils() {
	const req = mxUtils.load('src/js/flowchart.xml');
	const root = req.getDocumentElement();
	let shape = root.firstChild;
	while (shape != null)
	{
		if (shape.nodeType == mxConstants.NODETYPE_ELEMENT)
		{
			mxStencilRegistry.addStencil(shape.getAttribute('name'), new mxStencil(shape));
			//graph.getStylesheet().putCellStyle(shape.getAttribute('name'), new mxStencil(shape));
//			console.log(shape.getAttribute('name'));
		}
		shape = shape.nextSibling;
	}
}

// Shapes.
// We need to create functions to draw the different shapes used in Eugenia.
// TODO Auto-generate this

function TaskShape() { }
TaskShape.prototype = new mxShape();
TaskShape.prototype.constructor = TaskShape;
TaskShape.prototype.paintVertexShape = function(c, x, y, w, h)
{
	// To make a round corner rectangle we need 8 points ?_min/_max represent the x and y
	// coordinates with the nip removed to allocate the round corner
	const nip = 0.05 * w;
	const x_min = x + nip;
	const x_max = x + w - nip;
	const y_min = y + nip;
	const y_max = y + h - nip;

	c.begin();
    c.moveTo(x_min, y);
    c.lineTo(x_max, y);
    c.moveTo(x_max, y);
    c.quadTo(x + w, y, x + w, y_min);
    c.moveTo(x + w, y_min);
    c.lineTo(x + w, y_max);
    c.moveTo(x + w, y_max);
    c.quadTo(x + w, y + h, x_max, y + h);
    c.moveTo(x_max, y + h);
    c.lineTo(x_min, y + h);
    c.moveTo(x_min, y + h);
    c.quadTo(x, y + h, x, y_max);
    c.moveTo(x, y_max);
    c.lineTo(x, y_min);
    c.moveTo(x, y_min);
    c.quadTo(x, y, x_min, y);
    c.moveTo(x_min, y);
    c.close();
    c.fillAndStroke();
};
mxCellRenderer.registerShape('Task', TaskShape);

function SourceShape() { }
SourceShape.prototype = new mxShape();
SourceShape.prototype.constructor = SourceShape;
SourceShape.prototype.paintVertexShape = function(c, x, y, w, h)
{
    c.begin();
    c.moveTo(x, y);
    c.lineTo(x + 0.8*w, y);
    c.moveTo(x + 0.8*w, y);
    c.lineTo(x + w, y + h/2);
    c.moveTo(x + w, y + h/2);
    c.lineTo(x + 0.8*w, y + h);
    c.moveTo(x + 0.8*w, y + h);
    c.lineTo(x, y + h);
    c.moveTo(x, y + h);
    c.lineTo(x, y);
    c.close();
    c.fillAndStroke();
};
mxCellRenderer.registerShape('Source', SourceShape);

function SinkShape() { }
SinkShape.prototype = new mxShape();
SinkShape.prototype.constructor = SinkShape;
SinkShape.prototype.paintVertexShape = function(c, x, y, w, h)
{
    c.begin();
    c.moveTo(x, y);
    c.lineTo(x + w, y);
    c.moveTo(x + w, y);
    c.lineTo(x + w, y + 0.8*h);
    c.moveTo(x + w, y + 0.8*h);
    c.lineTo(x + 0.5*w, y + h);
    c.moveTo(x + 0.5*w, y + h);
    c.lineTo(x, y + 0.8*h);
    c.moveTo(x, y + 0.8*h);
    c.lineTo(x, y);
    c.close();
    c.fillAndStroke();
};
mxCellRenderer.registerShape('Sink', SinkShape);
