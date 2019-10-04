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

function startWebSocket(app) {
	console.log("startWebSocket", app.experimentId);
	let ws = null;
	const wsUri = 'ws://' + window.location.hostname + ':61614';
	const protocol = 'stomp';
	const parser = new DOMParser();

	const queueSizes = new Map();
	const taskOldStatusCache = app.taskStatusCache;
	const taskStatusCache = new Map();


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

	  ws.send('SUBSCRIBE\ndestination:' + STREAM_TOPIC + '.' + app.experimentId + '\n\nack:auto\n\n\0');
	  console.log('subscribed to ' + STREAM_TOPIC);

	  ws.send('SUBSCRIBE\ndestination:' + TASK_TOPIC + '.' + app.experimentId + '\n\nack:auto\n\n\0');
	  console.log('subscribed to ' + TASK_TOPIC);

	  ws.send('SUBSCRIBE\ndestination:' + LOG_TOPIC + '.' + app.experimentId + '\n\nack:auto\n\n\0');
	  console.log('subscribed to ' + LOG_TOPIC);

	  //ws.send('DISCONNECT\n\n\0');
	  //console.log('disconnect');
	};

	ws.onmessage = (e) => {
		// console.time('processActiveMQMessage');
		processActiveMQMessage(e)
		// console.timeEnd('processActiveMQMessage');

	};// onMessage

	setInterval(function() {
		updateGraph();
	}, 50);

	function updateGraph() {
		refreshQueuesValue();
		try {
			window.runtimeModelGraph.getModel().beginUpdate();
			refreshTaskColors();
		}
		finally {
			window.runtimeModelGraph.getModel().endUpdate();
		}
		window.runtimeModelGraph.refresh();

	}

	function processActiveMQMessage(message) {
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

	// Regex to match queue data
	const queueRegex = new RegExp('(.*)(Pre|Post|Destination$)');

	function processStreamEvent(message) {
		let text = message.data.substring(message.data.indexOf(STREAM_TOPIC_ROOT), message.data.length-1);
		let streamTopicXmlDoc = parser.parseFromString(text,"text/xml");
		//console.log("processStreamEvent", streamTopicXmlDoc)
		const streams = streamTopicXmlDoc.childNodes[0].children[0];
		if (streams != null) {
			for (let i = 0; i < streams.children.length; i++) {
				let streamMetadata = streams.children[i];
				if (streamMetadata.children[0].innerHTML != null) {
					const match = queueRegex.exec(streamMetadata.children[0].innerHTML.split(".")[0]);
					if (match !== null) {
						const streamId = match[1];
						const type = match[2];
						// console.log("queueId name", streamId);
						let tooltip = app.queueTooltips.get(streamId);
						if (tooltip === undefined) {
							tooltip = new QueueTooltip();
							app.queueTooltips.set(streamId, tooltip);
						}
						if (type === 'Pre') {
                            tooltip.addPre(streams.children[i])
                        }
                        else if (type === 'Post') {
                        	const size = formatSize(streams.children[i].children[1].innerHTML);
							queueSizes.set(streamId, size[0] + size[1]);
							tooltip.addPost(streams.children[i])
						}
						else if (type ==='Destination') {
                            tooltip.addDest(streams.children[i])
                        }
					}
				}
			}
		}
	}

	function processTaskEvent(message) {
		let text = message.data.substring(message.data.indexOf(TASK_TOPIC_ROOT), message.data.length - 1);
		let taskTopicXmlDoc = parser.parseFromString(text, "text/xml");
		let taskStatus = taskTopicXmlDoc.childNodes[0];
		let caller = taskStatus.children[1];
		taskStatusCache.set(
			caller.innerHTML.substring(0, caller.innerHTML.indexOf(':')),
			taskStatus.children[0].innerHTML);
	}

	function refreshTaskColors() {
		if ( window.runtimeModelGraph.getDefaultParent().children != null ) {
			for (let i = 0, l = window.runtimeModelGraph.getDefaultParent().children.length; i < l; i++) {
				let taskId = window.runtimeModelGraph.getDefaultParent().children[i].id.split("_")[1];
				if (taskStatusCache.has(taskId)) {
					const taskStatus = taskStatusCache.get(taskId);
					const oldStatus = taskOldStatusCache.get(taskId);
					// console.log("task status", taskStatus);
					// console.log("old task status", oldStatus);
					// console.log(oldStatus !== taskStatus);
					if (oldStatus !== taskStatus) {
						const id = window.runtimeModelGraph.getDefaultParent().children[i].id;
						const cell = window.runtimeModelGraph.model.getCell(id);
						let color = "";
						if ( taskStatus === 'STARTED' ) {
							color = 'lightcyan';
						}
						else if ( taskStatus === 'WAITING' ) {
							color = 'skyblue';
						}
						else if ( taskStatus === 'INPROGRESS' ){
							color = 'palegreen';
						}
						else if ( taskStatus === 'BLOCKED' ){
							color = 'salmon';
						}
						else if ( taskStatus === 'FINISHED' ){
							color = 'slategray';
						}
						// console.log("new style " + color);
						window.runtimeModelGraph.setCellStyles(mxConstants.STYLE_FILLCOLOR, color, [cell]);
						// console.log("change color to ", cell.getStyle())
						taskOldStatusCache.set(taskId, taskStatus);
					}
				}
			}
		}
	}

	function refreshQueuesValue() {
		if (window.runtimeModelGraph.getDefaultParent().children != null) {
			for (let [streamId, size] of queueSizes) {
				// Find and update vertex
				for (let j = 0, l = window.runtimeModelGraph.getDefaultParent().children.length; j < l; j++) {
					let vertexId = window.runtimeModelGraph.getDefaultParent().children[j].id;
					if (vertexId.includes('stream_' + streamId)) {
						const cell = window.runtimeModelGraph.getModel().getCell(vertexId);
						window.runtimeModelGraph.getModel().setValue(cell, size);
					}
				}
			}
		}
	}

	function processLogEvent(message) {
		const text = message.data.substring(message.data.indexOf(LOG_TOPIC_ROOT), message.data.length-1);
		window.logTopicXmlDoc = parser.parseFromString(text,"text/xml");
//			  console.log('received log message: ' + window.logTopicXmlDoc);

		const timestamp = window.logTopicXmlDoc.children[0].children[1].innerHTML;
		const msg = window.logTopicXmlDoc.children[0].children[2].innerHTML
		const severity = window.logTopicXmlDoc.children[0].children[0].innerHTML;

		// unix timestamp to human readable
		const newTimestampDate = new Date();
		newTimestampDate.setTime(timestamp);
		const timestampHumanReadable = newTimestampDate.toUTCString();

		let row = document.getElementById('log-table-body').insertRow(-1);

		let spanElem = document.createElement('span');
		spanElem.textContent = severity;
		spanElem.classList.add('badge');
		spanElem.classList.add('badge-secondary');

		row.insertCell(0).appendChild(document.createTextNode(timestampHumanReadable));
		row.insertCell(1).appendChild(spanElem);
		row.insertCell(2).appendChild(document.createTextNode(msg));
	}

	/*
 * TODO Animation: examples/animation.html / thread.html Markers:
 * examples/control.html Orthogonal: examples/orthogonal.html Swimlanes:
 * monitor.html
 */

	function sleep(delay) {
		let start = new Date().getTime();
		while (new Date().getTime() < start + delay);
	}

}

/**
 * Format the size to use K, M, G, etc.
 * @param sizeElement
 * @returns {any[]}
 */
function formatSize(sizeElement) {
	let size = sizeElement;
	let sizeUnit = '';
	if (size >= 1000000) {
		sizeUnit = "M";
		size = size / 1000000;
	} else if (size >= 1000) {
		sizeUnit = "K";
		size = size / 1000;
	}
	return [Math.round(size), sizeUnit];
}

function QueueTooltip () {
	this.preSize = null;
	this.preInFlightSize = null;
	this.postSize = null;
	this.postInFlightSize = null;
	this.subscribers = null;
	this.destSize = null;
	this.destInFlightSize = null;
	this.cellTooltip = null;
}

QueueTooltip.prototype.addPre = function(streamMetadata) {
	this.preSize = streamMetadata.children[1].innerHTML;
	this.preInFlightSize = streamMetadata.children[2].innerHTML;
	// console.log("addPre", this.preSize, this.preInFlightSize);
};

QueueTooltip.prototype.addPost = function(streamMetadata) {
	this.postSize = streamMetadata.children[1].innerHTML;
	this.postInFlightSize = streamMetadata.children[2].innerHTML;
	this.subscribers = streamMetadata.children[4].innerHTML;
	// console.log("addPost", this.postSize, this.postInFlightSize, this.subscribers);
};

QueueTooltip.prototype.addDest = function(streamMetadata) {
	this.destSize = streamMetadata.children[1].innerHTML;
	this.destInFlightSize = streamMetadata.children[2].innerHTML;
	// console.log("addDest", this.destSize, this.destInFlightSize);
};
QueueTooltip.prototype.getToolTip = function() {
	if (this.preSize === null) {
		if (this.cellTooltip !== null) {
			return this.cellTooltip;
		}
		return "";
	}
	let formatted = formatSize(this.getSize(this.preSize, this.postSize, this.destSize));
	const queueSize = formatted[0];
	const queueSizeUnit = formatted[1];

	formatted = formatSize(this.getSize(this.preInFlightSize, this.postInFlightSize, this.destInFlightSize));
	const inFlightSize = formatted[0];
	const inFlightSizeUnit = formatted[1];

	formatted = formatSize(this.preSize);
	this.preSize	= formatted[0];
	const preSizeUnit = formatted[1];

	formatted = formatSize(this.postSize);
	this.postSize = formatted[0];
	const postSizeUnit = formatted[1];

	formatted = formatSize(this.destSize);
	this.destSize = formatted[0];
	const destSizeUnit = formatted[1];

	formatted = formatSize(this.preInFlightSize);
	this.preInFlightSize = formatted[0];
	const preInFlightSizeUnit = formatted[1];

	formatted = formatSize(this.postInFlightSize);
	this.postInFlightSize = formatted[0];
	const postInFlightSizeUnit = formatted[1];

	formatted = formatSize(this.destInFlightSize);
	this.destInFlightSize = formatted[0];
	const destInFlightSizeUnit = formatted[1];
	this.cellTooltip =
		"<table class='tg'> " +
		"<tr>" +
		"<th></th>" +
		"<th style='text-align: center;'>In-Flight |</th>" +
		"<th style='text-align: center;'>| Queue</th>" +
		"</tr>" +
		"<tr>" +
		"<td style='font-style: italic'>Pre</td>" +
		"<td style='text-align: center;'>" + this.preInFlightSize + preInFlightSizeUnit + "</td>" +
		"<td style='text-align: center;'>" + this.preSize + preSizeUnit + "</td>" +
		"</tr>" +
		"<tr>" +
		"<td style='font-style: italic'>Dest</td>" +
		"<td style='text-align: center;'>" + this.destInFlightSize + destInFlightSizeUnit + "</td>" +
		"<td style='text-align: center;'>" + this.destSize + destSizeUnit + "</td>" +
		"</tr>" +
		"<tr>" +
		"<td style='font-style: italic'>Post</td>" +
		"<td style='text-align: center;'>" + this.postInFlightSize + postInFlightSizeUnit + "</td>" +
		"<td style='text-align: center;'>" + this.postSize + postSizeUnit + "</td>" +
		"</tr>" +
		"<tr>" +
		"<th style='font-style: italic'>Total</th>" +
		"<td style='text-align: center;'>" + inFlightSize + inFlightSizeUnit+ "</td>" +
		"<td style='text-align: center;'>" + queueSize + queueSizeUnit + "</td>" +
		"</tr>" +
		"<tr>" +
		"<td style='font-style: italic'>Subs</td>" +
		"<td  style='text-align: center;' colspan='2'>" + this.subscribers + "</td>" +
		"</tr>" +
		"</table>";
	return this.cellTooltip;
}
/**
 * Sum up queue size of pre-queue, post-queue, and destination-queue
 */
QueueTooltip.prototype.getSize = function (pre, post, dest) {
	let result = 0;
	if (pre !== null) {
		result += parseInt(pre, 10);
	}
	if (post !== null) {
		result += parseInt(post, 10);
	}
	if (dest !== null) {
		result += parseInt(dest, 10);
	}
	return result;
}
