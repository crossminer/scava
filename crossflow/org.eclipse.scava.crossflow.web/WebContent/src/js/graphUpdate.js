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

const STREAM_TOPIC = "/topic/StreamMetadataBroadcaster";
const TASK_TOPIC = "/topic/TaskStatusPublisher";
const LOG_TOPIC = "/topic/LogTopic";

const STREAM_TOPIC_ROOT = '<org.eclipse.scava.crossflow.runtime.utils.StreamMetadataSnapshot>';
const TASK_TOPIC_ROOT = '<org.eclipse.scava.crossflow.runtime.utils.TaskStatus>';
const LOG_TOPIC_ROOT = '<org.eclipse.scava.crossflow.runtime.utils.LogMessage>';


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
	parser = new DOMParser();
	
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
	 
	  ws.send('SUBSCRIBE\ndestination:' + STREAM_TOPIC + '.' + experimentId + '\n\nack:auto\n\n\0');
	  console.log('subscribed to ' + STREAM_TOPIC);
	  
	  ws.send('SUBSCRIBE\ndestination:' + TASK_TOPIC + '.' + experimentId + '\n\nack:auto\n\n\0');
	  console.log('subscribed to ' + TASK_TOPIC);
	  
	  ws.send('SUBSCRIBE\ndestination:' + LOG_TOPIC + '.' + experimentId + '\n\nack:auto\n\n\0');
	  console.log('subscribed to ' + LOG_TOPIC);
	 
	  //ws.send('DISCONNECT\n\n\0');
	  //console.log('disconnect');
	};
	 
	ws.onmessage = (e) => {
	 //console.log('onmessage: ' + e);
	  if (e.data.startsWith('MESSAGE')) {
		  //console.log("e.data="+e.data);
		  
		  if ( e.data.includes(STREAM_TOPIC_ROOT) ) {
			  text = e.data.substring(e.data.indexOf(STREAM_TOPIC_ROOT), e.data.length-1);
			  window.streamTopicXmlDoc = parser.parseFromString(text,"text/xml");
			  streamTopicXmlDoc = window.streamTopicXmlDoc;
			  // console.log('streamTopicXmlDoc: ' + text);
			  
			  
		  }// if STREAM_TOPIC_ROOT
		  
		  else if ( e.data.includes(TASK_TOPIC_ROOT) ) {
			  text = e.data.substring(e.data.indexOf(TASK_TOPIC_ROOT), e.data.length-1);
			  window.taskTopicXmlDoc = parser.parseFromString(text,"text/xml");
			  // console.log('taskTopicXmlDoc: ' + text);
			
			  
		  }// if TASK_TOPIC_ROOT
		  
		  else if ( e.data.includes(LOG_TOPIC_ROOT) ) {
			  text = e.data.substring(e.data.indexOf(LOG_TOPIC_ROOT), e.data.length-1);
			  window.logTopicXmlDoc = parser.parseFromString(text,"text/xml");
//			  console.log('received log message: ' + window.logTopicXmlDoc);
			  
			  timestamp = window.logTopicXmlDoc.children[0].children[1].innerHTML;
			  msg = window.logTopicXmlDoc.children[0].children[2].innerHTML
			  severity = window.logTopicXmlDoc.children[0].children[0].innerHTML;
			  
			  // unix timestamp to human readable
			  var newTimestampDate = new Date();
			  newTimestampDate.setTime(timestamp);
			  timestampHumanReadable = newTimestampDate.toUTCString();
			  
			  let row = document.getElementById('log-table-body').insertRow(-1);
			  
			  let spanElem = document.createElement('span');
			  spanElem.textContent = severity;
			  spanElem.classList.add('badge');
			  spanElem.classList.add('badge-secondary');
	
			  row.insertCell(0).appendChild(document.createTextNode(timestampHumanReadable));
			  row.insertCell(1).appendChild(spanElem);
			  row.insertCell(2).appendChild(document.createTextNode(msg));
			  
		  }
		  
		  
	  }// if MESSAGE
	 
	};
	
	/*
	 * TODO Animation: examples/animation.html / thread.html Markers:
	 * examples/control.html Orthogonal: examples/orthogonal.html Swimlanes:
	 * monitor.html
	 */
	window.runtimeModelGraph = new mxGraph(window.runtimeModelContainer);
	
};// main

