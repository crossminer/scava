<jsp:include page="header.jsp" />

<main role="main" id="app">

<section class="jumbotron text-center">
	<div class="container">
		<h1 class="jumbotron-heading">{{ experiment.title }}</h1>
		<p class="lead text-muted">{{ experiment.summary }}</p>
		
		<div class="btn-group" v-if="experiment.status == 'stopped'">
		  <button type="button" class="btn btn-success" v-on:click="startExperiment">Start</button>
		  <button type="button" class="btn btn-success dropdown-toggle dropdown-toggle-split" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		    <span class="sr-only">Toggle Dropdown</span>
		  </button>
		  <div class="dropdown-menu">
		    <a class="dropdown-item" href="#" v-on:click="startExperiment">Master and worker</a>
		    <a class="dropdown-item" href="#" v-on:click="startExperimentMasterOnly">Master only</a>
		  </div>
		</div>

		<button v-on:click="stopExperiment"
			v-if="experiment.status == 'running'" class="btn btn-danger my-2">Stop</button>
		<button v-on:click="resetExperiment"
			v-if="experiment.status != 'running' && (experiment.cached || experiment.executed)" class="btn btn-information my-2">Reset</button>
	</div>
</section>

<section>
	<div class="container">
		<div class="row">
            <div class="col-sm-12 col-md-12 py-12">	
				<ul class="nav nav-tabs" id="myTab" role="tablist">
				  <li class="nav-item" v-for="(fileDescriptor,index) in experiment.fileDescriptors">
				    <a class="nav-link" :id="'tab' + index" data-toggle="tab" :href="'#file' + index" role="tab" :aria-controls="'file'+index" aria-selected="true">{{ fileDescriptor.title }}</a>
				  </li>
				  <li class="nav-item">
				    <a class="nav-link active" id="advanced-tab" data-toggle="tab" href="#advanced" role="tab" aria-controls="advanced" aria-selected="false">Advanced</a>
				  </li>
				</ul>
				<div class="tab-content" id="myTabContent">
				  <div class="tab-pane fade show" v-for="(fileDescriptor,index) in experiment.fileDescriptors" :id="'file'+index" role="tabpanel" :aria-labelledby="'file'+index+'-tab'">
				  	<p/>
				  	<table class="table table-striped table-bordered" v-if="fileDescriptor.table">
				  		<thead v-if="fileDescriptor.table.header" class="thead-dark">
				  			<tr>
				  				<th width="50px"><span class="badge badge-secondary">#</span></th>
				  				<th v-for="cell in fileDescriptor.table.header.cells"> {{ cell }}</th>
				  			</tr>
				  		</thead>
				  		<tr v-for="(row,index) in fileDescriptor.table.rows">
				  			<td><span class="badge badge-secondary">{{ index+1 }}</span></td>
				  			<td v-for="cell in row.cells"> {{ cell }}</td>
				  		</tr>
				  	</table>
				  	<div class="alert alert-info" role="alert" v-if="(!fileDescriptor.table || !fileDescriptor.table.header) && !fileDescriptor.input && !experiment.executed">
				  	Not what you were hoping for? <a href="#" v-on:click="startExperiment">Start the experiment</a> and data will appear here soon.
				  	</div>
				  	<div class="alert alert-warning" role="alert" v-if="!fileDescriptor.table && !fileDescriptor.input && experiment.executed">
				  	The experiment produced no data here.
				  	</div>
				  	
				  </div>
				  <div class="tab-pane fade show active" id="advanced" role="tabpanel" aria-labelledby="advanced-tab">
				  <p>
					  <table class="table table-striped table-bordered">
					  	<tr>
					  		<td>Broker</td>
					  		<td>
					  			<span class="badge badge-success" v-if="diagnostics.brokerRunning">running</span>
                    			<span class="badge badge-danger" v-if="!diagnostics.brokerRunning">stopped</span>
					  		</td>
					  	</tr>
					  	<tr>
					  		<td>Executed</td>
					  		<td>
					  			<span class="badge badge-success" v-if="experiment.executed">yes</span>
                    			<span class="badge badge-dark" v-if="!experiment.executed">no</span>
					  		</td>
					  	</tr>
					  	<tr>
					  		<td>Cached</td>
					  		<td>
					  			<span class="badge badge-success" v-if="experiment.cached">yes</span>
                    			<span class="badge badge-dark" v-if="!experiment.cached">no</span>
					  		</td>
					  	</tr>
					  	<tr><td>Serving from</td><td><code>{{ diagnostics.rootDirectory }}</code></td></tr>
					  	<tr><td>Workflow class</td><td><code>{{ experiment.className }}</code></td></tr>
					  	<tr><td>Input/output directories</td><td><code>{{ experiment.inputDirectory }} / {{ experiment.outputDirectory }}</code></td></tr>
					  	
					  </table>
				  </p>
				  </div>
				</div>
			</div>
		</div>
	</div>
</section>

</main>

<script>
	var app = new Vue({
		el : '#app',
		data : {
			message : 'Hello Vue!',
			experimentId : null,
			experiment : new Experiment(),
			diagnostics : new Diagnostics()
		},
		methods : {
			startExperimentMasterOnly : function(event) {
				crossflow.startExperiment(this.experimentId, false);
			},
			startExperiment : function(event) {
				crossflow.startExperiment(this.experimentId, true);
			},
			stopExperiment : function(event) {
				crossflow.stopExperiment(this.experimentId);
			},
			resetExperiment : function(event) {
				crossflow.resetExperiment(this.experimentId);
			}
		}
	})

	var transport = new Thrift.TXHRTransport("/org.eclipse.scava.crossflow.web/crossflow");
	var protocol = new Thrift.TJSONProtocol(transport);
	var crossflow = new CrossflowClient(protocol);

	var url = new URL(window.location.href);
	app.experimentId = url.searchParams.get("id");
	
	refresh();
	
	setInterval(function() {
		refresh()
	}, 2000);
	
	function refresh() {
		app.experiment = crossflow.getExperiment(app.experimentId);
		app.experiment.fileDescriptors.forEach(function(fileDescriptor){
			fileDescriptor.table = crossflow.getContent(fileDescriptor);
		});
		app.diagnostics = crossflow.getDiagnostics();
	}
	
</script>

<jsp:include page="footer.jsp" />
