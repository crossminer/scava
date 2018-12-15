// Generate JS code: thrift --gen js -out WebContent/js/gen crossflow.thrift
// Generate Java code: thrift --gen java -out src crossflow.thrift
namespace java org.eclipse.scava.crossflow.web

typedef i32 int

service Crossflow {
	
	void startBroker();
	void stopBroker();
	bool isBrokerRunning();
	
	string run(1:string workflow);
	
	
	//int add(1:int n1, 2:int n2),
	//int multiply(1:int n1, 2:int n2),
	
}