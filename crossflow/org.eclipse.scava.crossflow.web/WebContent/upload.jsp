<jsp:include page="header.jsp" />

<main role="main" id="app">

<section class="jumbotron text-center">
	<div class="container">
		<h1 class="jumbotron-heading">Upload New Workflow</h1>
		<p class="lead text-muted">Crossflow is a distributed data
			processing framework that supports dispensation of work across
			multiple opinionated and low-commitment workers. On this page, select
			a new workflow file for upload or return to the available workflows
			page.</p>
		<p>
			<a href="index.jsp" class="btn btn-primary my-2">Available Workflows</a>
		</p>
	</div>
</section>

<div class="album py-5 bg-light">
	<div class="container">
	<div class="row justify-content-center">
	<div class="col-sm-6 col-md-6 py-6">
		<form action="uploadExperiment" method="post" enctype="multipart/form-data">
			<div class="form-group">
				<input type="text" class="form-control" id="inputName" name="inputName"
					aria-describedby="textHelp" placeholder="Enter experiment name (unique internal identifier)" required/>
			</div>
			<div class="custom-file">
				<input type="file" accept=".zip" class="custom-file-input" id="experimentZip" name="experimentZip" @change="updateExperimentZipState"/>
				<label class="custom-file-label" for="experimentZip" id="experimentZipLabel">
				Choose experiment ZIP file
				</label>
			</div>
			<br /> <br />

			<div class="form-check">
				<input type="checkbox" class="form-check-input" id="inputCheck" name="inputCheck">
				<!--for="exampleCheck1"-->
				<label class="form-check-label" >Launch experiment immediately.</label>
			</div>
			<br />
			<div class="row justify-content-center">
				<div class="col-4">
					<button action="action" type="reset" class="btn btn-danger" onclick="window.history.go(-1); return false;">Cancel</button>
				</div>
				<div class="col-4">
					<button type="submit" id="upload" class="btn btn-success" disabled="true">Upload</button>
				</div>
			</div>

		</form>
	</div>
	</div>
	</div>
</div>

</main>

<script>
var app = new Vue({
	el: '#app',
	methods: {
		updateExperimentZipState : function(event) {
			let experimentZipName = document.getElementById("experimentZip").value;
			document.getElementById("experimentZipLabel").innerHTML = experimentZipName.replace(/^.*\\/, "");
			document.getElementById("upload").disabled = false;
		},

		uploadSelectedFile : function(event) {
			let formData = new FormData();
			formData.append('file', this.file);


			//console.log("uploadSelectedFile: " + this.$refs.file.files[0]);
			/* crossflow.uploadFile(this.$refs.file.files[0]); */
			crossflow.uploadFile("blah");

		}
	}
})

var transport = new Thrift.TXHRTransport("/org.eclipse.scava.crossflow.web/crossflow");
var protocol = new Thrift.TJSONProtocol(transport);
var crossflow = new CrossflowClient(protocol);

var url = new URL(window.location.href);


</script>

<jsp:include page="footer.jsp" />
