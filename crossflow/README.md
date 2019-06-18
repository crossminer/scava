# Workflow Engine
![Logo](https://github.com/crossminer/scava/raw/crossflow/crossflow/crossflow_96dpi.png)

Crossflow is a distributed data processing framework that supports dispensation of work across multiple opinionated and low-commitment workers.

## Docker Quick Start

Pull container image from Docker Hub:

`docker pull crossminer/crossflow`

Startup container:

`docker run -it --rm -d --name crossflow -p 80:8080 -p 61616:61616 -p 61614:61614 -p 5672:5672 -p 61613:61613 -p 1883:1883 -p 8161:8161 -p 1099:1099 crossminer/crossflow:latest`

Access Crossflow web application:
http://localhost/org.eclipse.scava.crossflow.web/

More details on running Crossflow with Docker are available [here](https://github.com/crossminer/scava/tree/crossflow/crossflow/org.eclipse.scava.crossflow.web.docker/README.md).

## Running from source
To run Crossflow from source you will need Eclipse, Apache Tomcat and Apache Thrift. Brief instructions are provided below.

### Eclipse
- Start with a J2EE distribution from https://www.eclipse.org/downloads/packages/release/2018-09/r/eclipse-ide-java-ee-developers
- Install Emfatic from http://download.eclipse.org/emfatic/update/ (Untick the "Group items by category" check box)
- Install the Graphical Modelling Framework (GMF) Tooling SDK from http://download.eclipse.org/modeling/gmp/gmf-tooling/updates/releases/
- Install the following features from http://download.eclipse.org/epsilon/interim/
	- Epsilon Core
	- Epsilon Core Develoment Tools
	- Epsilon EMF Integration
	- Epsilon GMF Integration
- Install Web Tools Platform SDK (WTP SDK) from http://download.eclipse.org/webtools/repository/photon

### Tomcat
- Download a copy of Tomcat from http://archive.apache.org/dist/tomcat/tomcat-9/v9.0.14/bin/apache-tomcat-9.0.14.zip
- Set up Tomcat in your Eclipse through the Servers view

### Thrift
- Install Apache Thrift (http://thrift.apache.org/)
	- Standalone executable for Windows
	- Homebrew for Mac

### Git
- Clone the https://github.com/crossminer/scava/ repository
- Switch to the crossflow branch
- Import all projects from the crossflow and the restmule folders

### Ivy
We're using Apache Ivy for dependency management (i.e. so that we don't need to store jars in the repo)
- Install the Ivy Eclipse plugin: http://www.apache.org/dist/ant/ivyde/updatesite
- If Ivy doesn't run automatically, look for any projects that contain an ivy.xml, right-click and select Ivy -> Resolve

### Generating stuff
You will need to run the ANT build-files below to generate stuff after you import all the crossflow and restmule projects.

- org.eclipse.scava.crossflow.tests/generate-all-tests.xml runs the Crossflow code generator against all models under /org.eclipse.scava.crossflow.tests/models
- org.eclipse.scava.crossflow.web/run-thrift.xml runs the Thrift code generator against crossflow.thrift to produce Java and JavaScript source code
- org.eclipse.scava.crossflow.web/build-war.xml builds a Tomcat WAR file from org.eclipse.scava.crossflow.web
- org.eclipse.scava.crossflow.examples/generate-all-examples.xml runs the Crossflow code generator against all models under /org.eclipse.scava.crossflow.examples/models

### Tests
- JUnit tests can be ran through the CrossflowTests class in org.eclipse.scava.crossflow.tests

### Web application
- To run the web application (port: 8080) right-click on org.eclipse.scava.crossflow.web and select Run as -> Run on Server
- The web app should be running on http://localhost:8080/org.eclipse.scava.crossflow.web/

### Screenshots

![Screenshot](https://github.com/crossminer/scava/raw/crossflow/crossflow/images/index.png)
**Figure**: Main page listing available workflows and *Upload New Workflow* button.

![Screenshot](https://github.com/crossminer/scava/raw/crossflow/crossflow/images/calculator-advanced.png)
**Figure**: Calculator experiment page *Advanced* tab listing Calculator workflow configuration.

![Screenshot](https://github.com/crossminer/scava/raw/crossflow/crossflow/images/calculator-calculations.png)
**Figure**: Calculator experiment page *Calculations* tab listing Calculator workflow input calculations obtained from CSV source.

![Screenshot](https://github.com/crossminer/scava/raw/crossflow/crossflow/images/calculator-model.png)
**Figure**: Calculator experiment page *Model* tab listing Calculator workflow model.

![Screenshot](https://github.com/crossminer/scava/raw/crossflow/crossflow/images/calculator-log.png)
**Figure**: Calculator experiment page *Log* tab listing Calculator workflow log after experiment completion.

![Screenshot](https://github.com/crossminer/scava/raw/crossflow/crossflow/images/wordcount-model.png)
**Figure**: Word Count experiment page *Model* tab listing Word Count workflow model before execution.

![Screenshot](https://github.com/crossminer/scava/raw/crossflow/crossflow/images/wordcount-model-running.png)
**Figure**: Word Count experiment page *Model* tab listing Word Count workflow model during execution visualizing task status and queue size by means of color and rounded number, respectively. **Task status (color)**: STARTED (lightcyan), WAITING (skyblue), INPROGRESS (palegreen), BLOCKED (salmon), and FINISHED (slategrey).  

![Screenshot](https://github.com/crossminer/scava/raw/crossflow/crossflow/images/wordcount-model-running-tooltip.png)
**Figure**: Word Count experiment page *Model* tab listing Word Count workflow model during execution with mouse hovering over initial queue depicting (queue) size, in-flight count, and subscriber count.

![Screenshot](https://github.com/crossminer/scava/raw/crossflow/crossflow/images/wordcount-model-clear-all.png)
**Figure**: Word Count experiment page *Model* tab listing Word Count workflow model during execution with mouse click inside empty model area, i.e. not on a particular task or queue, displaying context menu popup to clear the cache of all queues involved in the Word Count workflow.

 ![Screenshot](https://github.com/crossminer/scava/raw/crossflow/crossflow/images/wordcount-model-clear-specific.png)
**Figure**: Word Count experiment page *Model* tab listing Word Count workflow model during execution with mouse click inside boundaries of *WordFrequencies* queue displaying context menu popup to clear the cache of all queues involved in the Word Count workflow.

 ![Screenshot](https://github.com/crossminer/scava/raw/crossflow/crossflow/images/upload.png)
**Figure**: Upload New Workflow page allowing the upload and deployment of new experiments.
