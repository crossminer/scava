function loadStencils() {
	var req = mxUtils.load('flowchart.xml');
	var root = req.getDocumentElement();
	var shape = root.firstChild;
	
	while (shape != null)
	{
		if (shape.nodeType == mxConstants.NODETYPE_ELEMENT)
		{
			mxStencilRegistry.addStencil(shape.getAttribute('name'), new mxStencil(shape));
			//graph.getStylesheet().putCellStyle(shape.getAttribute('name'), new mxStencil(shape));
			//console.log(shape.getAttribute('name'));
		}
		
		shape = shape.nextSibling;
	}
}

function createTask(name, graph, parent) {
	var v = graph.insertVertex(parent, null, name, 20, 20, 80, 30);
	graph.updateCellSize(v, true);
	return v;
}

function createStream(name, graph, parent) {
	var v = graph.insertVertex(parent, null, "", 20, 20, 30, 20);
	v.style = "shape=Direct Data";
	v.setAttribute("tooltip", name);
	//graph.updateCellSize(v, true);
	return v;
}

function link(source, target, graph, parent) {
	return graph.insertEdge(parent, null, '', source, target);
}