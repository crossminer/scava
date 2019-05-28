// constants
const INTERNAL_EXCEPTIONS_STREAM_ID = "InternalExceptions";
const IN_FLIGHT_COUNT = "InFlightCount";
const SUBSCRIBER_COUNT = "SubscriberCount";
const FAILED_JOBS_COUNT = "FailedJobs";
const INTERNAL_EXCEPTION_COUNT = "InternalExceptions";
const SIZE_COUNT = "Size";
const STREAM_POST = "Post";

const IN_FLIGHT_LABEL_PRE = "InFlight";
const SUBSCRIBER_LABEL_PRE = "Subscribers";
const FAILED_JOBS_LABEL_PRE = "Failed Jobs: ";
const INTERNAL_EXCEPTION_LABEL_PRE = "Exceptions: ";
const SIZE_LABEL_PRE = "Size (total)";
const PRE_SIZE_LABEL_PRE = "Size (pre)";
const POST_SIZE_LABEL_PRE = "Size (post)";
const DEST_SIZE_LABEL_PRE = "Size (dest)";

const STREAM_TOPIC = "/topic/StreamMetadataBroadcaster";
const TASK_TOPIC = "/topic/TaskMetadataBroadcaster";
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

function main(crossflow, container, experimentId) {
	
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
	  if ( e.data.startsWith('MESSAGE') ) {
		  //console.log("e.data="+e.data);
		  
		  if ( e.data.includes(STREAM_TOPIC_ROOT) ) {
			  text = e.data.substring(e.data.indexOf(STREAM_TOPIC_ROOT), e.data.length-1);
			  window.streamTopicXmlDoc = parser.parseFromString(text,"text/xml");
			  streamTopicXmlDoc = window.streamTopicXmlDoc;
			  // console.log('streamTopicXmlDoc: ' + text);
			  
				if ( streamTopicXmlDoc.childNodes[0].children[0] != null ) {
					//console.log("streams encountered");
					for ( i=0; i < streamTopicXmlDoc.childNodes[0].children[0].children.length; i++ )
						if ( streamTopicXmlDoc.childNodes[0].children[0].children[i] != null &&
								streamTopicXmlDoc.childNodes[0].children[0].children[i].children[0].innerHTML != null ) {
					
							streamId = streamTopicXmlDoc.childNodes[0].children[0].children[i].children[0].innerHTML.substring(0, streamTopicXmlDoc.childNodes[0].children[0].children[i].children[0].innerHTML.indexOf('.'));
					
							if ( !streamId.includes('Post') ) {
								return; // not a queue stream
								
							} else {
								// queue stream encountered
								streamId = streamId.substring(0, streamId.indexOf('Post'));
								//console.log('streamId='+streamId);
								
								if ( window.runtimeModelGraph.getDefaultParent().children != null ) { 
								
									for (var j = 0, l = window.runtimeModelGraph.getDefaultParent().children.length; j < l; j++) {
										modelElement = window.runtimeModelGraph.getDefaultParent().children[j].id;
										//console.log('modelElement='+modelElement);
										if ( modelElement.includes('stream_' + streamId) ) {
											//console.log("i="+i+";  j="+j);
											name = streamTopicXmlDoc.childNodes[0].children[0].children[i].children[0].innerHTML;
											//console.log('name='+name);
											
											// size
											size = streamTopicXmlDoc.childNodes[0].children[0].children[i].children[1].innerHTML;
											sizeUnit = '';
											//console.log('size='+size);
											if ( size >= 1000 && size <= 999999 ) {
												sizeUnit = "K";
												size = size / 1000;
											} else if ( size >= 1000000 ) {
												sizeUnit = "M";
												size = size / 1000000;
											}
											// rounding size
											size = Math.round(size);
											
											//console.log('size='+size);
											
											//window.runtimeModelGraph.getModel().beginUpdate();
											try {
												
										    	var id = window.runtimeModelGraph.getDefaultParent().children[j].id;
										    	var cell = window.runtimeModelGraph.model.getCell(id);
										    	var cellValue = size + sizeUnit;
										    	cell.setValue(cellValue);
										    	//console.log('cellValue = ' + cellValue);
												
										 	} finally {
												// Updates the display	
												//window.runtimeModelGraph.getModel().endUpdate();
												window.runtimeModelGraph.refresh();
												//console.log('STREAM_TOPIC: graphUpdate.main.onMessage.endUpdate');
											}
											 	
										}
									}// for window.runtimeModelGraph.getDefaultParent().children
									
								}
							}	
						}		
				}
			  
		  }// if STREAM_TOPIC_ROOT
		  
		  else if ( e.data.includes(TASK_TOPIC_ROOT) ) {
			  text = e.data.substring(e.data.indexOf(TASK_TOPIC_ROOT), e.data.length-1);
			  window.taskTopicXmlDoc = parser.parseFromString(text,"text/xml");
			  // console.log('taskTopicXmlDoc: ' + text);
			
			  //console.log('beginUpdate(B)');
			  	  taskId = window.taskTopicXmlDoc.childNodes[0].children[1].innerHTML.substring(0, window.taskTopicXmlDoc.childNodes[0].children[1].innerHTML.indexOf(':'));
				  if ( window.runtimeModelGraph.getDefaultParent().children != null ) {
					  for (var i = 0, l = window.runtimeModelGraph.getDefaultParent().children.length; i < l; i++) {
						    if ( window.runtimeModelGraph.getDefaultParent().children[i].id == 'task_' + taskId ) {
						    	taskStatus = window.taskTopicXmlDoc.childNodes[0].children[0].innerHTML;
						    	
						    	var id = window.runtimeModelGraph.getDefaultParent().children[i].id;
						    	var cell = window.runtimeModelGraph.model.getCell(id);
						    	// fontSize=16;labelBackgroundColor=#ffffff;fillColor=#ffffff;fontColor=black;strokeColor=black
						    	var cellStylePre = cell.getStyle();
						    	var cellStyle = cellStylePre.substring(cellStylePre.indexOf('fillColor='), cellStylePre.length-1);
						    	cellStylePre = cellStylePre.substring(0, cellStylePre.indexOf('fillColor='));
						    	
						    	// STARTED, WAITING, INPROGRESS, BLOCKED, FINISHED
						    	if ( taskStatus == 'STARTED' )
						    		cellStyle = 'fillColor=lightcyan';
						    		
						    	else if ( taskStatus == 'WAITING' )
						    		cellStyle = 'fillColor=skyblue';
						    		
						    	else if ( taskStatus == 'INPROGRESS' )
						    		cellStyle = 'fillColor=palegreen';
						    		
						    	else if ( taskStatus == 'BLOCKED' )
						    		cellStyle = 'fillColor=salmon';
						    		
						    	else if ( taskStatus == 'FINISHED' )
						    		cellStyle = 'fillColor=slategray';
					
						    	try {
									window.runtimeModelGraph.model.beginUpdate();
							    	cell.setStyle(cellStylePre + cellStyle);
							       	//console.log(taskId + ' (' + taskStatus + ')');
						    	} finally {
									// Updates the display
									window.runtimeModelGraph.model.endUpdate();
									//console.log("endUpdate(B)");
									window.runtimeModelGraph.refresh();
									//console.log('TASK_TOPIC: graphUpdate.main.onMessage.endUpdate');
								}
						    	
						    }
						}// for window.runtimeModelGraph.getDefaultParent().children
				  }
			  	
		  	  
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
		  
	  }// startsWith MESSAGE
	  
	};// onMessage
	
	/*
	 * TODO Animation: examples/animation.html / thread.html Markers:
	 * examples/control.html Orthogonal: examples/orthogonal.html Swimlanes:
	 * monitor.html
	 */
	
	function sleep(delay) {
	    var start = new Date().getTime();
	    while (new Date().getTime() < start + delay);
	}
	
};// main
