# Running the Crossflow web app in Docker
- Run `org.eclipse.scava.crossflow.web/deploy-tests-run-thrift.xml`
- This should create a zipped Tomcat application (`org.eclipse.scava.crossflow.web.war`) in `org.eclipse.scava.crossflow.web.docker`
- Run the following commands to build and run a Docker image 
	- `docker build -t crossflow .` 
	- `docker run -it --rm -d -p 80:8080 -p 61616:61616 -p 8161:8161 -p 1099:1099 crossflow:latest`
- You should now be able to access the web app though http://localhost/org.eclipse.scava.crossflow.web
	