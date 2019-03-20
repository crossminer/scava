# RUNNING THE CROSSFLOW ELKGRAPH SERVER 

### Run as foreground process in console (will terminate on any keyboard input):

$ java -jar org.eclipse.scava.crossflow.elkgraph.web.jar


### Run as 'screen' process in console (allows screen disconnect and console logout):

$ apt-get install screen

$ screen

$ java -jar org.eclipse.scava.crossflow.elkgraph.web.jar
 
Disconnect from running screen:

$ CTRL+A

$ CTRL+D

Logout from console:

$ exit