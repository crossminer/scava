// Generate JS code: thrift --gen js -out WebContent/js/gen crossflow.thrift
// Generate Java code: thrift --gen java -out src crossflow.thrift
namespace java org.eclipse.scava.crossflow.web

typedef i32 int

service Crossflow {
	
	void startBroker();
	void stopBroker();
	bool isBrokerRunning();
	
	string startWorkflow(1:string jar, 2:string main);
	bool isWorkflowRunning(1:string instanceId);
	void stopWorkflow(1:string instanceId);
	
}