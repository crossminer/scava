/* (!) filePath must specify a location accessible by the web server */
function loadModel(filePath) {
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

		var experimentName = experimentId; 
		var xmlModel = loadModel("experiments/" + experimentName + "/graph.xml")
		var doc = mxUtils.parseXml(xmlModel);
		var codec = new mxCodec(doc);
		codec.decode(doc.documentElement, graph.getModel());
		
		var layout = new mxCompactTreeLayout(graph, true);
		layout.execute(parent, graph.getModel());
		
	} finally {
		// Updates the display
		graph.getModel().endUpdate();
	}

};

