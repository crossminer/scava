# Crossflow

Crossflow is a distributed data processing framework that supports dispensation of work across multiple opinionated and low-commitment workers.

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
- Download a copy of Tomcat from http://archive.apache.org/dist/tomcat/tomcat-7/v7.0.41/bin/apache-tomcat-7.0.41.zip
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
- To build the web application, run `$ yarn install && yarn run build` from directory  org.eclipse.scava.crossflow.web
- To run the elkgraph application (port: 9090) right-click on class `ServerLauncher.java/xtend` in project `org.eclipse.scava.crossflow.elkgraph.web` and select Run as -> Java Application  
- To run the web application (port: 8080) right-click on org.eclipse.scava.crossflow.web and select Run as -> Run on Server
- The web app should be running on http://localhost:8080/org.eclipse.scava.crossflow.web/

### Screenshots

![Screenshot](https://i.imgur.com/aJjLs73.png)
![Screenshot](https://i.imgur.com/EXeAYkc.png)
![Screenshot](https://i.imgur.com/pPbK3zy.png)
