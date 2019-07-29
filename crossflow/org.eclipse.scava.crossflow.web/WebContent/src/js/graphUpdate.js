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

const STREAM_TOPIC_ROOT = "<StreamMetadataSnapshot>";
const TASK_TOPIC_ROOT = "<TaskStatus>";
const LOG_TOPIC_ROOT = "<LogMessage>";


/* (!) filePath must specify a location accessible by the web server */
function loadFile(filePath) {
	let result = null;
	const xmlhttp = new XMLHttpRequest();
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
	const parser = new DOMParser();

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
		processActiveMQMessage(e)


	};// onMessage

	/*
	 * TODO Animation: examples/animation.html / thread.html Markers:
	 * examples/control.html Orthogonal: examples/orthogonal.html Swimlanes:
	 * monitor.html
	 */

	function sleep(delay) {
	    let start = new Date().getTime();
	    while (new Date().getTime() < start + delay);
	}

	function processActiveMQMessage(message) {
		//console.log('onmessage: ' + message);
		if ( message.data.startsWith('MESSAGE') ) {
			//console.log("message.data="+message.data);
			if ( message.data.includes(STREAM_TOPIC_ROOT) ) {
				processStreamEvent(message);
			}
			else if ( message.data.includes(TASK_TOPIC_ROOT) ) {
				processTaskEvent(message)
			}
			else if ( message.data.includes(LOG_TOPIC_ROOT) ) {
				processLogEvent(message);
			}
		}
	}

	function processStreamEvent(message) {
		let text = message.data.substring(message.data.indexOf(STREAM_TOPIC_ROOT), message.data.length-1);
		// FIXME We can create the tooltip info here since we are already parsing the xml
		window.streamTopicXmlDoc = parser.parseFromString(text,"text/xml");
		let streamTopicXmlDoc = window.streamTopicXmlDoc;
		const streams = streamTopicXmlDoc.childNodes[0].children[0];
		let streamId;
		let vertexId;
		let size;
		if (streams != null) {
			for (let i = 0; i < streams.children.length; i++) {
				let streamName = streams.children[i].children[0];
				if (streamName.innerHTML != null) {
					let streamId = streamName.innerHTML.substring(0, streamName.innerHTML.indexOf('.'));
					if (streamId.includes('Post')) {
						// queue stream encountered
						streamId = streamId.substring(0, streamId.indexOf('Post'));
						// Find and update vertex
						if (window.runtimeModelGraph.getDefaultParent().children != null) {
							for (let j = 0, l = window.runtimeModelGraph.getDefaultParent().children.length; j < l; j++) {
								vertexId = window.runtimeModelGraph.getDefaultParent().children[j].id;
								if (vertexId.includes('stream_' + streamId)) {
									size = formatSize(streams.children[i].children[1]);
									try {
										const cell = window.runtimeModelGraph.getModel().getCell(vertexId);
										window.runtimeModelGraph.getModel().setValue(cell, size);
									} finally {
										window.runtimeModelGraph.refresh();
									}
								}
							}
						}
					}
				}
			}
		}
	}

	function processTaskEvent(message) {
		let text = message.data.substring(message.data.indexOf(TASK_TOPIC_ROOT), message.data.length-1);
		window.taskTopicXmlDoc = parser.parseFromString(text,"text/xml");
		let taskTopicXmlDoc = window.taskTopicXmlDoc;
		console.log("processTaskEvent")
		let taskStatus = window.taskTopicXmlDoc.childNodes[0];
		let caller = taskStatus.children[1];
		let taskId = caller.innerHTML.substring(0, caller.innerHTML.indexOf(':'));
		if ( window.runtimeModelGraph.getDefaultParent().children != null ) {
			for (let i = 0, l = window.runtimeModelGraph.getDefaultParent().children.length; i < l; i++) {
				if ( window.runtimeModelGraph.getDefaultParent().children[i].id == 'task_' + taskId ) {
					taskStatus = taskStatus.children[0].innerHTML;
					const id = window.runtimeModelGraph.getDefaultParent().children[i].id;
					const cell = window.runtimeModelGraph.model.getCell(id);

					// fontSize=16;labelBackgroundColor=#ffffff;fillColor=#ffffff;fontColor=black;strokeColor=black
					let cellStylePre = cell.getStyle();
					let cellStyle = cellStylePre.substring(cellStylePre.indexOf('fillColor='), cellStylePre.length - 1);
					cellStylePre = cellStylePre.substring(0, cellStylePre.indexOf('fillColor='));
					let fillcolor="";
					// STARTED, WAITING, INPROGRESS, BLOCKED, FINISHED
					if ( taskStatus === 'STARTED' ) {
						cellStyle = 'fillColor=lightcyan';
						fillcolor = 'lightcyan';
					}
					else if ( taskStatus === 'WAITING' ) {
						cellStyle = 'fillColor=skyblue';
						fillcolor = 'skyblue';
					}
					else if ( taskStatus === 'INPROGRESS' ){
						cellStyle = 'fillColor=palegreen';
						fillcolor = 'palegreen';
					}
					else if ( taskStatus === 'BLOCKED' ){
						cellStyle = 'fillColor=salmon';
						fillcolor = 'salmon';
					}
					else if ( taskStatus === 'FINISHED' ){
						cellStyle = 'fillColor=slategray';
						fillcolor = 'slategray';
					}
					console.log("new style " + fillcolor);
					try {
						window.runtimeModelGraph.model.beginUpdate();
						console.log("change style " + cellStylePre + cellStyle);
						window.runtimeModelGraph.getView().clear(cell)
						// window.runtimeModelGraph.getModel().setStyle(cell, cellStylePre + cellStyle);
						// window.runtimeModelGraph.setCellStyles(
						// 		mxConstants.STYLE_FILLCOLOR, fillcolor,
						// 		{cell});
						cell.setStyle(cellStylePre + cellStyle);
						window.runtimeModelGraph.getView().validate(cell)
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
	}

	function processLogEvent(message) {
		text = message.data.substring(message.data.indexOf(LOG_TOPIC_ROOT), message.data.length-1);
		window.logTopicXmlDoc = parser.parseFromString(text,"text/xml");
//			  console.log('received log message: ' + window.logTopicXmlDoc);

		timestamp = window.logTopicXmlDoc.children[0].children[1].innerHTML;
		msg = window.logTopicXmlDoc.children[0].children[2].innerHTML
		severity = window.logTopicXmlDoc.children[0].children[0].innerHTML;

		// unix timestamp to human readable
		const newTimestampDate = new Date();
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

	/**
	 * Format the size to use K, M, G, etc.
	 * @param sizeElement
	 * @returns {string}
	 */
	function formatSize(sizeElement) {
		let size = sizeElement.innerHTML;
		let sizeUnit = '';
		if (size >= 1000000) {
			sizeUnit = "M";
			size = size / 1000000;
		} else if (size >= 1000) {
			sizeUnit = "K";
			size = size / 1000;
		}
		return Math.round(size) + sizeUnit
	}

};// main
