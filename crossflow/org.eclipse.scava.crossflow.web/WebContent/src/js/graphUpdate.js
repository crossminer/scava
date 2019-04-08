// constants
const INTERNAL_EXCEPTIONS_STREAM_ID = "InternalExceptions";
const IN_FLIGHT_COUNT = "InFlightCount";
const SUBSCRIBER_COUNT = "SubscriberCount";
const FAILED_JOBS_COUNT = "FailedJobs";
const INTERNAL_EXCEPTION_COUNT = "InternalExceptions";
const SIZE_COUNT = "Size";
const STREAM_POST = "Post";

const IN_FLIGHT_LABEL_PRE = "InFlight: ";
const SUBSCRIBER_LABEL_PRE = "Subscribers: ";
const FAILED_JOBS_LABEL_PRE = "Failed Jobs: ";
const INTERNAL_EXCEPTION_LABEL_PRE = "Exceptions: ";
const SIZE_LABEL_PRE = "Size: ";

const METADATA_STREAM_ID = "StreamMetadataBroadcaster";
const QUEUE_ID_PRE = "Q_";
const GRAPH_ID_PRE = "G_";

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
}// loadFile

function main(container, experimentId) {
	
	let ws = null;
	const wsUri = 'ws://' + window.location.hostname + ':61614';
	const protocol = 'stomp';
	const mdBroadcaster = '/topic/StreamMetadataBroadcaster';
	
	try {
		ws = new WebSocket(wsUri, protocol);
	} catch(err) {
		console.warn('failed to connect to ' + wsUri + ' over ' + protocol + ' protocol.');
	}
	
	if ( ws == null ) {
		console.warn('failed to connect to ' + wsUri + ' over ' + protocol + ' protocol.');
		console.warn('make sure ActiveMQ broker is running')
		return; // do not proceed 
	}
	
	ws.onerror = (evt) => {
	      if (ws.readyState == 1) {
	        console.warn('ws normal error: ' + evt.type);
	      } else {
	    	  console.warn('ws error');
	      }
	};
	
	ws.onopen = () => {
	  ws.send('CONNECT\n\n\0');
	  console.log('connected to ' + wsUri + ' over ' + protocol + ' protocol');
	 
	  ws.send('SUBSCRIBE\ndestination:' + mdBroadcaster + '.' + experimentId + '\n\nack:auto\n\n\0');
	  console.log('subscribed to ' + mdBroadcaster);
	 
	  //ws.send('DISCONNECT\n\n\0');
	  //console.log('disconnect');
	};
	 
	ws.onmessage = (e) => {
	 //console.log('onmessage: ' + e);
	  if (e.data.startsWith('MESSAGE')) {
		  //console.log("e.data="+e.data);
		  
		  // extract plain XML
		  text = e.data.substring(e.data.indexOf('<org.eclipse.scava.crossflow.runtime.utils.StreamMetadataSnapshot>'), e.data.length-1);
		  
		  // parse plain XML
		  parser = new DOMParser();
		  window.streamBroadcasterXmlDoc = parser.parseFromString(text,"text/xml");
		  
	  }
	 
	};
	
	/*
	 * TODO Animation: examples/animation.html / thread.html Markers:
	 * examples/control.html Orthogonal: examples/orthogonal.html Swimlanes:
	 * monitor.html
	 */
	window.runtimeModelGraph = new mxGraph(window.runtimeModelContainer);
	
};// main

