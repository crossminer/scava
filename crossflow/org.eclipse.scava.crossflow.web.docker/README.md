![Logo](../crossflow_96dpi.png)

Crossflow is a distributed data processing framework that supports dispensation of work across multiple opinionated and low-commitment workers.

## Quick Start Instructions:

Pull container image from Docker Hub:

`docker pull crossminer/crossflow`

Startup container:

`docker run -it --rm -d --name crossflow -p 80:8080 -p 61616:61616 -p 61614:61614 -p 5672:5672 -p 61613:61613 -p 1883:1883 -p 8161:8161 -p 1099:1099 crossminer/crossflow:latest`

Access web application:
http://localhost/org.eclipse.scava.crossflow.web/

Kill container:

`docker kill crossflow`

## Running the Crossflow web app in Docker from Source
- Follow [these instructions](https://github.com/crossminer/scava/blob/crossflow/crossflow/README.md) to check out and build the Crossflow source code

- Run `org.eclipse.scava.crossflow.web/build-war`

- This should create a zipped Tomcat application (`org.eclipse.scava.crossflow.web.war`) in `org.eclipse.scava.crossflow.web.docker`

- Run the following commands to build and run a Docker image
	- `docker build -t crossflow .`
	- `docker run -it --rm -d --name crossflow -p 80:8080 -p 61616:61616 -p 61614:61614 -p 5672:5672 -p 61613:61613 -p 1883:1883 -p 8161:8161 -p 1099:1099 crossminer/crossflow:latest`
	
- You should now be able to access the web app though http://localhost/org.eclipse.scava.crossflow.web


### Connect to container:
`docker exec -it crossflow /bin/bash`


### Update Crossflow image on DockerHub:

- Ensure that you have the latest version of the 'o.e.s.crossflow.web.war' file, by running 'build-war.xml' from the o.e.s.crossflow.web project

- Navigate into the root directory of `org.eclipse.scava.crossflow.web.docker` in your command-line environment

- Build local Docker image of current Crossflow codebase:
`docker build -t crossflow .`

- List local Docker images and take a note of the image identifier of the Crossflow image:
`docker images`

- Tag local Docker image (replace `IMAGE_ID` with the image identifier noted earlier):
`docker tag IMAGE_ID crossminer/crossflow`

- Make sure you're logged in and your user is allowed to push to the Crossflow DockerHub repository (replace `USER_ID` with your DockerHub username):
`docker login --user=USER_ID`

- Push new Crossflow Docker image to DockerHub:
`docker push crossminer/crossflow:latest`

### Links:

[Scava framework source code repository (GitHub)](https://github.com/crossminer/scava/tree/crossflow/crossflow)

[Crossflow Web Docker project (GitHub)](https://github.com/crossminer/scava/tree/crossflow/crossflow/org.eclipse.scava.crossflow.web.docker)

[Crossminer project website](https://www.crossminer.org)

[Crossflow on Docker Cloud / Hub](https://cloud.docker.com/repository/docker/crossminer/crossflow)
