<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link href="css/bootstrap.min.css" rel="stylesheet">

<link rel="stylesheet" href="sprotty/sprotty.css">
<link rel="stylesheet" href="diagram.css">

<script src="js/thrift.js"></script>
<script src="js/gen/Crossflow.js"></script>
<script src="js/gen/crossflow_types.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<link href="css/album.css" rel="stylesheet">

<script src="elkgraph.bundle.js"></script>
<script src="jquery/jquery.slim.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>

<!-- FIX: inconsistency between Microsoft Monaco Editor Loader, Bootstrap, and Vue.
				Removing vs/loader.js restores working Crossflow web behaviour but disables runtime model viewer. -->
<script src="vs/loader.js"></script>

<style>
.content-widget {
	border: 1px solid #888;
}

svg {
	overflow: hidden;
	position: relative;
	width: 100%;
	height: 100%;
	min-height: 500px;
	min-width: 1140px;
}
</style>

</head>

<body>

	<header>
		<div class="collapse bg-dark" id="navbarHeader">
			<div class="container">
				<div class="row">
					<div class="col-sm-8 col-md-7 py-4">
						<h4 class="text-white">About</h4>
						<p class="text-muted">Crossflow is a distributed data
							processing framework that supports opinionated and episodical
							workers, developed in the context of the CROSSMINER H2020
							project.</p>
					</div>
					<div class="col-sm-4 offset-md-1 py-4">
						<h4 class="text-white">Contact</h4>
						<ul class="list-unstyled">
							<li><a href="https://www.crossminer.org/" class="text-white">CROSSMINER</a></li>
							<li><a
								href="https://projects.eclipse.org/projects/technology.scava"
								class="text-white">Eclipse Scava</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="navbar navbar-dark bg-dark box-shadow">
			<div class="container d-flex justify-content-between">
				<a href="/org.eclipse.scava.crossflow.web"
					class="navbar-brand d-flex align-items-center"> <strong>Crossflow</strong>
				</a>
				<button class="navbar-toggler" type="button" data-toggle="collapse"
					data-target="#navbarHeader" aria-controls="navbarHeader"
					aria-expanded="false" aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
			</div>
		</div>
	</header>