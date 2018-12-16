// Generate JS code: thrift --gen js -out WebContent/js/gen crossflow.thrift
// Generate Java code: thrift --gen java -out src crossflow.thrift
namespace java org.eclipse.scava.crossflow.web

typedef i32 int

service Crossflow {
	
	void startBroker();
	void stopBroker();
	bool isBrokerRunning();
	
	string startExperiment(1:string experimentId);
	bool isExperimentRunning(1:string experimentId);
	void stopExperiment(1:string experimentId);
	void resetExperiment(1:string experimentId);
	
	list<Experiment> getExperiments();
	Experiment getExperiment(1:string experimentId);
	
	Diagnostics getDiagnostics();
	
}

struct Diagnostics {
	1: optional bool brokerRunning;
	2: optional string rootDirectory;
}

struct Experiment {
	1: optional string id;
	2: optional string title;
	3: optional string className;
	4: optional string jar;
	5: optional string summary;
	6: optional string description;
	7: optional string status;
	8: optional string input;
	9: optional string output;
	10: optional bool cached;
	11: optional bool executed;
}