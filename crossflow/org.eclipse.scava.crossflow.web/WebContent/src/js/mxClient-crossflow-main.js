function main(container) {

	/*
	 * TODO Animation: examples/animation.html / thread.html Markers:
	 * examples/control.html Orthogonal: examples/orthogonal.html Swimlanes:
	 * monitor.html
	 */

	mxEvent.disableContextMenu(container);

	var graph = new mxGraph(container);
	graph.getStylesheet().getDefaultEdgeStyle()['edgeStyle'] = 'orthogonalEdgeStyle';
	graph.setTooltips(true);
	graph.getTooltipForCell = function(cell) {
		return "<table border=1><tr><td>Stuff</td><td>More stuff</td></tr></table>";
	}

	var parent = graph.getDefaultParent();
	graph.enabled = false;

	graph.getModel().beginUpdate();
	try {

		loadStencils();

		var v1 = createTask("TechnologySource", graph, parent);
		var v2 = createStream("Technologies", graph, parent);
		var v3 = createTask("GitHubCodeSearcher", graph, parent);
		var v4 = createStream("Repositories", graph, parent);
		var v5 = createTask("RepositorySearchDispatcher", graph, parent);
		var v6 = createStream("RepositorySearches", graph, parent);
		var v7 = createTask("RepositorySearcher", graph, parent);
		var v8 = createStream("RepositorySearchResults", graph, parent);
		var v9 = createTask("ResultsSink", graph, parent);

		link(v1, v2, graph, parent);
		link(v2, v3, graph, parent);
		link(v3, v4, graph, parent);
		link(v4, v5, graph, parent);
		link(v5, v6, graph, parent);
		link(v6, v7, graph, parent);
		link(v7, v6, graph, parent);
		link(v7, v8, graph, parent);
		link(v8, v9, graph, parent);

		var layout = new mxCompactTreeLayout(graph, true);
		layout.execute(parent, v1);

	} finally {
		// Updates the display
		graph.getModel().endUpdate();
	}

};