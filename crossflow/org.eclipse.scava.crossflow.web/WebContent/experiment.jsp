<jsp:include page="header.jsp" />

<main role="main" id="app">

<section class="jumbotron text-center">
	<div class="container">
		<h1 class="jumbotron-heading">{{ experiment.title }}</h1>
		<p class="lead text-muted">{{ experiment.summary }}</p>
		<button v-on:click="startWorkflow"
			v-if="experiment.status == 'stopped'" class="btn btn-success my-2">Start</button>
		<button v-on:click="stopWorkflow"
			v-if="experiment.status == 'running'" class="btn btn-danger my-2">Stop</button>
		<button v-on:click="resetWorkflow"
			v-if="experiment.status != 'running' && (experiment.cached || experiment.executed)" class="btn btn-information my-2">Reset</button>
	</div>
</section>

<section>
	<div class="container">
		<div class="row">
            <div class="col-sm-12 col-md-12 py-12">	
				<ul class="nav nav-tabs" id="myTab" role="tablist">
				  <li class="nav-item">
				    <a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab" aria-controls="home" aria-selected="true">Home</a>
				  </li>
				  <li class="nav-item">
				    <a class="nav-link" id="profile-tab" data-toggle="tab" href="#profile" role="tab" aria-controls="profile" aria-selected="false">Profile</a>
				  </li>
				  <li class="nav-item">
				    <a class="nav-link" id="diagnostics-tab" data-toggle="tab" href="#diagnostics" role="tab" aria-controls="diagnostics" aria-selected="false">Diagnostics</a>
				  </li>
				</ul>
				<div class="tab-content" id="myTabContent">
				  <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">...</div>
				  <div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">...</div>
				  <div class="tab-pane fade" id="diagnostics" role="tabpanel" aria-labelledby="diagnostics-tab">
				  <p>
					  <table class="table table-striped">
					  	<tr><td>Broker running</td><td>{{ diagnostics.brokerRunning }}</td></tr>
					  	<tr><td>Serving from</td><td>{{ diagnostics.rootDirectory }}</td></tr>
					  	<tr><td>Executed</td><td>{{ experiment.executed }}</td></tr>
					  	<tr><td>Cached</td><td>{{ experiment.cached }}</td></tr>
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
			startWorkflow : function(event) {
				crossflow.startExperiment(this.experimentId);
			},
			stopWorkflow : function(event) {
				crossflow.stopExperiment(this.experimentId);
			},
			resetWorkflow : function(event) {
				crossflow.resetExperiment(this.experimentId);
			}
		}
	})

	var transport = new Thrift.TXHRTransport("/org.eclipse.scava.crossflow.web/crossflow");
	var protocol = new Thrift.TJSONProtocol(transport);
	var crossflow = new CrossflowClient(protocol);

	var url = new URL(window.location.href);
	app.experimentId = url.searchParams.get("id");

	setInterval(function() {
		app.experiment = crossflow.getExperiment(app.experimentId);
	}, 1000);
	
	setInterval(function() {
		app.diagnostics = crossflow.getDiagnostics();
	}, 1000);
	
</script>

<jsp:include page="footer.jsp" />
