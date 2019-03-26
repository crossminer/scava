/* (!) filePath must specify a location accessible by the web server */
function loadFile(filePath) {
	  var result = null;
	  var xmlhttp = new XMLHttpRequest();
	  xmlhttp.open("GET", filePath, false);
	  xmlhttp.send();
	  if (xmlhttp.status==200) {
	    result = xmlhttp.responseText;
	  }
	  return result;
}

function main(container, experimentId) {

	/*
	 * TODO Animation: examples/animation.html / thread.html Markers:
	 * examples/control.html Orthogonal: examples/orthogonal.html Swimlanes:
	 * monitor.html
	 */
	var codec = new mxCodec();

	mxEvent.disableContextMenu(container);
	window.runtimeModelContainer = container;

	window.runtimeModelGraph = new mxGraph(window.runtimeModelContainer);
	window.runtimeModelGraph.getStylesheet().getDefaultEdgeStyle()['edgeStyle'] = 'orthogonalEdgeStyle';
	window.runtimeModelGraph.setTooltips(true);
	window.runtimeModelGraph.getTooltipForCell = function(cell) {
		return "<table border=1><tr><td>Stuff</td><td>More stuff</td></tr></table>";
	}

	window.runtimeModelParent = window.runtimeModelGraph.getDefaultParent();
	window.runtimeModelGraph.enabled = false;

	window.runtimeModelGraph.getModel().beginUpdate();
	try {

		loadStencils();
		
		$.getScript('experiments/' + experimentId + '/graph.abstract', function()
		{
			var model = window.runtimeModelGraph.getModel();
			
			var layout = new mxCompactTreeLayout(window.runtimeModelGraph, true);
			layout.execute(window.runtimeModelParent, model.cells[2]);
			
			var nodeEnc = codec.encode(model);
			var xml = mxUtils.getXml(nodeEnc);
		});
	
	} finally {
		// Updates the display
		window.runtimeModelGraph.getModel().endUpdate();
	}

};

