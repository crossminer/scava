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

		<form action="uploadExperiment" method="post" enctype="multipart/form-data">
			<div class="form-group">
				<input type="text" class="form-control" id="inputName" name="inputName"
					aria-describedby="textHelp" placeholder="Enter experiment name (unique internal identifier)"/> 
			</div>
		
			<div class="custom-file">
				<input type="file" class="custom-file-input" id="experimentZip" name="experimentZip" @change="updateExperimentZipState"/>
				<label class="custom-file-label" for="experimentZip" id="experimentZipLabel">
				Choose experiment ZIP file
				</label>
			</div>
			<br /> <br />
			
			<div class="form-group">
				<input type="email" class="form-control" id="inputEmail" name="inputEmail"
					aria-describedby="emailHelp" placeholder="Enter email"/> <small
					id="emailHelp" class="form-text text-muted">We'll never
					share your email with anyone else.</small>
			</div>
			<div class="form-check">
				<input type="checkbox" class="form-check-input" id="inputCheck" name="inputCheck">
				<label class="form-check-label" for="exampleCheck1">I accept
					to receive e-mails from this service.</label>
			</div>
			<br />
			<button type="submit" class="btn btn-success">Upload</button>
		</form>

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
