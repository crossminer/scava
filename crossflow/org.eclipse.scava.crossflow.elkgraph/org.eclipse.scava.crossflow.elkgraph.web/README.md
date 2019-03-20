# Running the Crossflow elkgraph web app in Docker

- Run the following commands to build and run a Docker image 
	- `docker build -t elkgraph .` 
	- `docker run -it --rm -d -p 9090:9090 elkgraph:latest`
- You should now be able to access the runtime model view in the experiments page accessible from the crossflow web app though http://localhost/org.eclipse.scava.crossflow.web
	
