<jsp:include page="header.jsp"/>

    <main role="main" id="app">

      <section class="jumbotron text-center">
        <div class="container">
          <h1 class="jumbotron-heading">Crossflow</h1>
          <p class="lead text-muted">
          Crossflow is a distributed data processing framework 
          that supports opinionated and episodical workers. 
          Below are a few data processing workflows you can try out and contribute to.
          </p>
          <!--p>
            <a href="#" class="btn btn-primary my-2">Main call to action</a>
            <a href="#" class="btn btn-secondary my-2">Secondary action</a>
          </p-->
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
	  		data: {
		    	message: 'Hello Vue!',
		    	experiments: null
	    	}
		})
		
		var transport = new Thrift.TXHRTransport("/org.eclipse.scava.crossflow.web/crossflow");
		var protocol = new Thrift.TJSONProtocol(transport);
		var crossflow = new CrossflowClient(protocol);

		setInterval(function() {
			app.experiments = crossflow.getExperiments();
		}, 1000);
	</script>
	
<jsp:include page="footer.jsp"/>
