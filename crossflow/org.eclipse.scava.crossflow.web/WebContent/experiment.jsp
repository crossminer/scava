<jsp:include page="header.jsp" />

<main role="main" id="app">

<section class="jumbotron text-center">
	<div class="container">
		<h1 class="jumbotron-heading">{{ experiment.title }}</h1>
		<p class="lead text-muted">{{ experiment.summary }}</p>
		<p>
			<button v-on:click="startWorkflow"
				v-if="experiment.status == 'stopped'" class="btn btn-success my-2">Start</button>
			<button v-on:click="stopWorkflow"
				v-if="experiment.status == 'running'" class="btn btn-danger my-2">Stop</button>
		</p>
	</div>
</section>

<!--div class="album py-5 bg-light">
	<div class="container">

		<div class="row">
			<div class="col-md-4" v-for="experiment in experiments">
				<div class="card mb-4 box-shadow">

					<div class="card-body">
						<h3>{{ experiment.title }}</h3>
						<p class="card-text">{{ experiment.summary }}</p>
						<div class="d-flex justify-content-between align-items-center">
							<a role="button" class="btn btn-sm btn-outline-secondary"
								:href="'experiment.jsp?id=' + experiment.id">Details</a> <span
								class="badge badge-success"
								v-if="experiment.status == 'running'">running</span> <span
								class="badge badge-danger" v-if="experiment.status == 'stopped'">stopped</span>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>
</div-->

</main>

<script>
	var app = new Vue({
		el : '#app',
		data : {
			message : 'Hello Vue!',
			experimentId : null,
			experiment : new Experiment()
		},
		methods : {
			startWorkflow : function(event) {
				crossflow.startExperiment(this.experimentId);
			},
			stopWorkflow : function(event) {
				crossflow.stopExperiment(this.experimentId);
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
	
</script>

<jsp:include page="footer.jsp" />
