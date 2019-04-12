function loadStencils() {
	var req = mxUtils.load('src/js/flowchart.xml');
		
	var root = req.getDocumentElement();
	var shape = root.firstChild;
	
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

function createTask(name, graph, parent) {
	var id = 'task_' + name;
    var v = graph.insertVertex(parent, id, name, 20, 20, 80, 30, 'fontSize=16;labelBackgroundColor=#ffffff;fillColor=#ffffff;fontColor=black;strokeColor=black');	graph.updateCellSize(v, true);
	return v;
}

function createStream(name, graph, parent) {
	var id = 'stream_' + name;
	var v = graph.insertVertex(parent, id, "", 30, 40, 80, 40);
	v.style = "shape=Direct Data;fillColor=#ffffff;fontColor=black;strokeColor=black";
	v.setAttribute("tooltip", name);
	//graph.updateCellSize(v, true);
	return v;
}

function link(source, target, graph, parent) {
	return graph.insertEdge(parent, null, '', source, target, "strokeColor=black");
}