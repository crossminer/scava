<jsp:include page="header.jsp"/>

    <main role="main" id="app">

      <section class="jumbotron text-center">
        <div class="container">
          <h1 class="logo jumbotron-heading">Crossflow</h1>
          <p class="lead text-muted">
          Crossflow is a distributed data processing framework 
          that supports dispensation of work across multiple opinionated and low-commitment workers. 
          You can try out available data processing workflows displayed below or upload a new workflow.
          </p>
          <p>
            <a href="upload.jsp" class="btn btn-primary my-2">Upload New Workflow</a>
            <!-- a href="#" class="btn btn-secondary my-2">Secondary action</a-->
          </p>
        </div>
      </section>

      <div class="album py-5 bg-light">
        <div class="container">

          <div class="row">
            <div class="col-md-4" v-for="experiment in experiments">
              <div class="card mb-4 box-shadow">
             
                <div class="card-body">
                  <h3> {{ experiment.title }} </h3>
                  <p class="card-text">{{ experiment.summary }}</p>
                  <div class="d-flex justify-content-between align-items-center">
                  	<a role="button" class="btn btn-sm btn-outline-secondary" :href="'experiment.jsp?id=' + experiment.id">Details</a>
                    <span class="badge badge-success" v-if="experiment.status == 'running'">running</span>
                    <span class="badge badge-danger" v-if="experiment.status == 'stopped'">stopped</span>
                  </div>
                </div>
              </div>
            </div>

          </div>
        </div>
      </div>

    </main>

	<script>
		var app = new Vue({
			el: '#app',
			data : {
				message : 'Hello Vue!',
				experiments: null
			}
		})
		
		var transport = new Thrift.TXHRTransport("/org.eclipse.scava.crossflow.web/crossflow");
		var protocol = new Thrift.TJSONProtocol(transport);
		var crossflow = new CrossflowClient(protocol);
		
	 	var url = new URL(window.location.href);
		this.experimentId = url.searchParams.get("id");
		app.launchExperiment = url.searchParams.get("launchExperiment");
		
		if ( this.experimentId != null && this.experimentId != "" && app.launchExperiment != null && app.launchExperiment != "" && app.launchExperiment == "true" ) {
		    console.log("Waiting a bit for the Java classloader to finish loading recently uploaded executables ...");
			setTimeout(function(){					    
				try {
					if ( crossflow.getExperiment(this.experimentId) != null && url.searchParams.get("launched") == null ) {
						// redirect after waiting period (required for classloader to load recently uploaded executables)
						window.location.replace(url+"&launched=true");
					    
					} else if ( crossflow.getExperiment(this.experimentId) != null ) {
						console.log('Launching experiment with id = ' + this.experimentId);
						crossflow.startExperiment(this.experimentId, true);
						console.log('Completed launching experiment with id = ' + this.experimentId);	
						
						// remove parameters and redirect
						window.location.replace(url.href.substring(0, url.href.indexOf("index.jsp")+"index.jsp".length));
					}
				} catch (err) {
					console.log("No experiment with id '" + this.experimentId + "' available to launch.");
				}
			}, 3000);
		}
		
		setInterval(function() {
			app.experiments = crossflow.getExperiments();
		}, 1000);

		
	</script>
	
<jsp:include page="footer.jsp"/>
