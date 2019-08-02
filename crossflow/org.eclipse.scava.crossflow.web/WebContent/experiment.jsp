<jsp:include page="header.jsp" />

<main role="main" id="app">
	<div class="container">
		<div class="row">
			<div class="col align-self-center" align="center">
				<h1 class="jumbotron-heading">{{ experiment.title }}</h1>
				<p class="lead text-muted">{{ experiment.summary }}</p>
				<div class="row">
					<div class="col-10">
						<!-- MODEL -->
						<div id="graphContainer" style="position: relative; overflow: scroll; width: 100%; height: 100%; cursor: default;"></div>
					</div>
					<div class="col-2">
						<button type='button' class="btn btn-started my-2 btn-sm btn-block" disabled>STARTED</button>
						<button type='button' class="btn btn-waiting my-2 btn-sm btn-block" disabled>WAITING</button>
						<button type='button' class="btn btn-inprogress my-2 btn-sm btn-block" disabled>IN PROGRESS</button>
						<button type='button' class="btn btn-blocked my-2 btn-sm btn-block" disabled>BLOCKED</button>
						<button type='button' class="btn btn-finished my-2 btn-sm btn-block" disabled>FINISHED</button>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col align-self-center" align="center">
				<form>
					<div class="btn-group" v-if="stopped">
						<button type="button" class="btn btn-success" v-on:click="startExperiment">Start</button>
						<button type="button" class="btn btn-success dropdown-toggle dropdown-toggle-split"
								data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							<span class="sr-only">Toggle Dropdown</span>
						</button>
						<div class="dropdown-menu">
							<a class="dropdown-item" href="#" v-on:click="startExperiment">Master and worker</a>
							<a class="dropdown-item" href="#" v-on:click="startExperimentMasterOnly">Master only</a>
						</div>
					</div>
					<button type='button' class="btn btn-danger my-2" v-if="running"
						v-on:click="stopExperiment" >Stop</button>
					<button type='button' class="btn btn-information my-2" v-if="reset"
						v-on:click="resetExperiment" >Reset</button>
				</form>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12 col-md-12 py-12">

				<!-- TAB SPECS -->
				<ul class="nav nav-tabs" id="myTab" role="tablist">

					<!-- CUSTOM FILE DESCRIPTOR TABS -->
					<li class="nav-item"
						v-for="(fileDescriptor,index) in experiment.fileDescriptors">
						<a class="nav-link" :id="'tab' + index" data-toggle="tab"
						:href="'#file' + index" role="tab" :aria-controls="'file'+index"
						aria-selected="true">{{ fileDescriptor.title }}</a>
					</li>

					<!-- ADVANCED TAB -->
					<li class="nav-item"><a class="nav-link active"
						id="advanced-tab" data-toggle="tab" href="#advanced" role="tab"
						aria-controls="advanced" aria-selected="false">Advanced</a></li>

					<!-- LOG TAB -->
					<li class="nav-item"><a class="nav-link" id="log-tab"
						data-toggle="tab" href="#log" role="tab" aria-controls="log"
						aria-selected="false">Log</a></li>

				</ul>

				<!-- TAB CONTENTS -->
				<div class="tab-content" id="myTabContent">

					<!-- CUSTOM FILE DESCRIPTORS -->
					<div class="tab-pane fade show"
						v-for="(fileDescriptor,index) in experiment.fileDescriptors"
						:id="'file'+index" role="tabpanel"
						:aria-labelledby="'file'+index+'-tab'">
						<p />
						<table class="table table-striped table-bordered"
							v-if="fileDescriptor.table">
							<thead v-if="fileDescriptor.table.header" class="thead-dark">
								<tr>
									<th width="50px"><span class="badge badge-secondary">#</span></th>
									<th v-for="cell in fileDescriptor.table.header.cells">{{
										cell }}</th>
								</tr>
							</thead>
							<tr v-for="(row,index) in fileDescriptor.table.rows">
								<td><span class="badge badge-secondary">{{ index+1
										}}</span></td>
								<td v-for="cell in row.cells">{{ cell }}</td>
							</tr>
						</table>
						<div class="alert alert-info" role="alert"
							v-if="(!fileDescriptor.table || !fileDescriptor.table.header) && !fileDescriptor.input && !experiment.executed">
							Not what you were hoping for? <a href="#"
								v-on:click="startExperiment">Start the experiment</a> and data
							will appear here soon.
						</div>
						<div class="alert alert-warning" role="alert"
							v-if="!fileDescriptor.table && !fileDescriptor.input && experiment.executed">
							The experiment produced no data here.</div>

					</div>

					<!-- ADVANCED -->
					<div class="tab-pane fade show active" id="advanced"
						role="tabpanel" aria-labelledby="advanced-tab">
						<p>
						<table class="table table-striped table-bordered">
							<tr>
								<td>Auto-refresh</td>
								<td><input type="checkbox" id="refresh" checked></td>
							</tr>
							<tr>
								<td>Broker</td>
								<td><span class="badge badge-success"
									v-if="diagnostics.brokerRunning">running</span> <span
									class="badge badge-danger" v-if="!diagnostics.brokerRunning">stopped</span>
								</td>
							</tr>
							<tr>
								<td>Executed</td>
								<td><span class="badge badge-success"
									v-if="experiment.executed">yes</span> <span
									class="badge badge-dark" v-if="!experiment.executed">no</span>
								</td>
							</tr>
							<tr>
								<td>Cached</td>
								<td><span class="badge badge-success"
									v-if="experiment.cached">yes</span> <span
									class="badge badge-dark" v-if="!experiment.cached">no</span></td>
							</tr>
							<tr>
								<td>Serving from</td>
								<td><code>{{ diagnostics.rootDirectory }}</code></td>
							</tr>
							<tr>
								<td>Workflow class</td>
								<td><code>{{ experiment.className }}</code></td>
							</tr>
							<tr>
								<td>Input/output directories</td>
								<td><code>{{ experiment.inputDirectory }} / {{
										experiment.outputDirectory }}</code></td>
							</tr>

						</table>
						</p>
					</div>

					<!-- LOG -->
					<div class="tab-pane fade show" id="log" role="tabpanel"
						aria-labelledby="log-tab">
						<!-- TODO: LOG TAB CONTENT -->
						<div id="log0" role="tabpanel" aria-labelledby="log0-tab" class="tab-pane fade active show">
						   <p></p>
						   <table class="table table-striped table-bordered" id="log-table">
						      <thead class="thead-dark">
						       		<tr>
							            <th>Creation</th>
							            <th><span class="badge badge-secondary">Severity</span></th>
							            <th>Message</th>
						       		</tr>
						      </thead>
						      <tbody id="log-table-body">
						      </tbody>
						   </table>
						   <!----> <!---->
						</div>


						<p></p>
					</div>

				</div>
			</div>
		</div>
	</div>
</main>




<script>
	const app = new Vue({
		el : '#app',
		data : {
			message : 'Hello Vue!',
			experimentId : null,
			stopped: true,
			running : false,
			reset: false,
			experiment : new Experiment(),
			diagnostics : new Diagnostics(),
			taskStatusCache : new Map(),
			queueTooltips : new Map()
		},
		methods : {
			startExperimentMasterOnly : function(event) {
				try {
					this.running = crossflow.startExperiment(this.experimentId, false);
					this.stopped = !this.running;
				}
				catch (e) {
					this.stopped = true;
					this.running = false;
				}
			},
			startExperiment : function(event) {
				try {
					this.stopped = crossflow.startExperiment(this.experimentId, true);
					this.stopped = !this.running;
				}
				catch (e) {
					this.stopped = true;
					this.running = false;
				}
			},
			stopExperiment : function(event) {
				try {
					this.stopped = crossflow.stopExperiment(this.experimentId);
					this.running = !this.stopped;
				}
				catch (e) {
					// pass
				}
			},
			resetExperiment : function(event) {
				crossflow.resetExperiment(this.experimentId);
			}
		}
	});

	const transport = new Thrift.TXHRTransport("/org.eclipse.scava.crossflow.web/crossflow");
	const protocol = new Thrift.TJSONProtocol(transport);
	const crossflow = new CrossflowClient(protocol);
	const experimentId = new URL(document.location).searchParams.get('id');

	// Start the web socket
	app.experimentId = experimentId;
	startWebSocket(app);

	// mxGraph
	const cellTooltips = {};
	loadStencils();
	createMxGraph();
	loadDiagram();

	// Query the server periodically
	getExperimentInfo();
	setInterval(function() {
			getExperimentInfo();
		}, 3000);


	function createMxGraph() {
		mxEvent.disableContextMenu(document.getElementById('graphContainer'));
		window.runtimeModelGraph = new mxGraph(document.getElementById('graphContainer'));
		window.runtimeModelGraph.getStylesheet().getDefaultEdgeStyle()['edgeStyle'] = 'orthogonalEdgeStyle';
		window.runtimeModelGraph.setTooltips(true);
		const oldGetPreferredSizeForCell = window.runtimeModelGraph.getPreferredSizeForCell;
		window.runtimeModelGraph.getPreferredSizeForCell = function(cell) {
				const result = oldGetPreferredSizeForCell.apply(this, arguments);
				if (result != null) {
					result.width = result.width + 20;
					result.height = 40;
				}
				return result;
			};
		window.runtimeModelGraph.getTooltipForCell = function(cell, evt) {
			if ( cell.id.includes('task_') ) {
				const taskId = cell.id.substr('task_'.length);
				console.log("Task tooltip", taskId);
				return "<table border=1><tr><td>" + app.taskStatusCache.get(taskId) + "</td></tr></table>";
			}
			else if ( cell.id.includes('stream_') ) {
				const queueId = cell.id.substr('stream_'.length);
				console.log("Stream tooltip", queueId);
				const cellTooltip = app.queueTooltips.get(queueId);
				if (cellTooltip === undefined) {
					return "";
				}
				return cellTooltip.getToolTip();
			}
			else {
				return "";
			}
		};
		//---------------
		// CONTEXT MENU
		window.runtimeModelGraph.popupMenuHandler.factoryMethod = function(menu, cell, evt) {
				return createPopupMenu(window.runtimeModelGraph, menu, cell, evt);
			};
	}


	function loadDiagram() {
		// ---------------
		// Load diagram data from workflow's model.json
		$.getJSON("${"experiments/"}${pageContext.request.getParameter("id")}${"/model.json"}", function( json ) {
				//console.log("loadDiagram", json);
				window.runtimeModelGraph.getModel().beginUpdate();
				if (readModel(json, window.runtimeModelGraph, window.runtimeModelGraph.getDefaultParent())) {
					//console.log("automatic layout");
					const layout = new mxCompactTreeLayout(window.runtimeModelGraph, true);
					layout.execute(window.runtimeModelGraph.getDefaultParent());
				}
				window.runtimeModelGraph.enabled = false; // de-activate graph editing
				window.runtimeModelGraph.getModel().endUpdate();
				//console.log("loaded model");
			});
	}

	function createPopupMenu(graph, menu, cell, evt) {
		if (cell != null)
		{
			if (cell.id.startsWith('stream_')) {
				menu.addItem('Clear cache of queue \"' + cell.id.substr('stream_'.length) + '\"?', 'src/images/warning.gif', function()
				{
					res = mxUtils.confirm('Are you sure about clearing the cache of queue \"'+ cell.id.substr('stream_'.length) + '\"?', false);
					if ( res ) {
						// trigger cache clearing of queue cell.id
						clearSuccess = crossflow.clearQueueCache(experimentId, cell.id);
						if ( clearSuccess ) {
							mxUtils.alert('Cache of queue \"' + cell.id.substr('stream_'.length) + '\" has been cleared.');
						} else {
							mxUtils.alert('Failed to clear cache of queue \"' + cell.id.substr('stream_'.length) + '\". Has the experiment been stopped or terminated yet?');
						}
					}
				})
			}
		} else {
			menu.addItem('Clear cache of all queues?', 'src/images/warning.gif', function()
			{
				res = mxUtils.confirm('Are you sure about clearing the cache of all queues?', false);
				if ( res ) {
					// trigger cache clearing of all queues
					clearSuccess = crossflow.clearQueueCache(experimentId, "");
					if ( clearSuccess ) {
						mxUtils.alert('Cache of all queues has been cleared.');
					} else {
						mxUtils.alert('Failed to clear cache of all queues. Has the experiment been stopped or terminated yet?');
					}
				}
			});
		}
	};// CONTEXT MENU


	function getExperimentInfo() {
		//console.log("getExperimentInfo");
		app.experiment = crossflow.getExperiment(app.experimentId);
		app.experiment.fileDescriptors.forEach(function(fileDescriptor) {
			fileDescriptor.table = crossflow.getContent(fileDescriptor);
		});
		app.diagnostics = crossflow.getDiagnostics();
		app.running = app.experiment.status === 'running';
		app.stopped = app.experiment.status === 'stopped';
		// "experiment.status != 'running' && (experiment.cached || experiment.executed)"
		if (app.experiment.status !== 'running' && (app.experiment.cached || app.experiment.executed)) {
			app.rest = true;
		}
	};



</script>

<jsp:include page="footer.jsp" />
