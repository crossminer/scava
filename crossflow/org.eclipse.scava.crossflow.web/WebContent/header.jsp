<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/crossflow.css" rel="stylesheet">

	<script src="js/thrift.js"></script>
    <script src="js/gen/Crossflow.js"></script>
    <script src="js/gen/crossflow_types.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <link href="css/album.css" rel="stylesheet">

	<!-- mxGraph JS imports -->
	<script type="text/javascript">
		mxBasePath = 'src';
	</script>

	<script type="text/javascript" src="src/js/mxClient.js"></script>
	<script type="text/javascript" src="src/js/diagram.js"></script>
	<script type="text/javascript" src="src/js/graphUpdate.js"></script>

	<!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>

    <!-- ActiveMQ AJAX ( http://activemq.apache.org/ajax.html ) -->
    <!-- <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script> -->
	<script type="text/javascript" src="js/amq_jquery_adapter.js"></script>
	<script type="text/javascript" src="js/amq.js"></script>
	<script type="text/javascript">
	  var amq = org.activemq.Amq;
	  amq.init({
	    uri: 'tcp://localhost:61616',
	    logging: true,
	    timeout: 20
	  });
	</script>

  </head>

  <!-- body onload="main(crossflow, document.getElementById('graphContainer'), new URL(document.location).searchParams.get('id'))" -->
  <!--body onload="startWebSocket(new URL(document.location).searchParams.get('id'))"-->
  <body>
    <header>
      <div class="collapse bg-dark" id="navbarHeader">
        <div class="container">
          <div class="row">
            <div class="col-sm-8 col-md-7 py-4">
              <h4 class="text-white">About</h4>
              <p class="text-muted">Crossflow is a distributed data processing framework that supports opinionated and episodical workers, developed in the context of the CROSSMINER H2020 project.</p>
            </div>
            <div class="col-sm-4 offset-md-1 py-4">
              <h4 class="text-white">Contact</h4>
              <ul class="list-unstyled">
                <li><a href="https://www.crossminer.org/" class="text-white">CROSSMINER</a></li>
                <li><a href="https://projects.eclipse.org/projects/technology.scava" class="text-white">Eclipse Scava</a></li>
              </ul>
            </div>
          </div>
        </div>
      </div>
      <div class="navbar navbar-dark bg-dark box-shadow">
        <div class="container d-flex justify-content-between">
          <a href="/org.eclipse.scava.crossflow.web" class="navbar-brand d-flex align-items-center">
            <div class="logo-header">Crossflow</div>
          </a>
          <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarHeader" aria-controls="navbarHeader" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
        </div>
      </div>
    </header>
