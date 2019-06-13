// Generate JS code: thrift --gen js -out WebContent/js/gen crossflow.thrift
// Generate Java code: thrift --gen java -out src crossflow.thrift
namespace java org.eclipse.scava.crossflow.web

typedef i32 int

service Crossflow {
	
	void startBroker();
	void stopBroker();
	bool isBrokerRunning();
	
	string startExperiment(1:string experimentId, 2:bool worker);
	bool isExperimentRunning(1:string experimentId);
	void stopExperiment(1:string experimentId);
	void resetExperiment(1:string experimentId);
	bool clearQueueCache(1:string experimentId, 2:string queueName);
	Table getContent(1:FileDescriptor fileDescriptor);
	
	list<Experiment> getExperiments();
	Experiment getExperiment(1:string experimentId);
	
	Diagnostics getDiagnostics();
	
}

struct FileDescriptor {
	1: optional string experimentId;
	2: optional string id;
	3: optional string path;
	4: optional string title;
	5: optional bool input;
}

struct Table {
	1: optional Row header;
	2: optional list<Row> rows;
}

struct Row {
	1: optional list<string> cells;
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
	8: optional string inputDirectory;
	9: optional string outputDirectory;
	10: optional string runtimeModel;
	11: optional bool cached;
	12: optional bool executed;
	13: optional list<FileDescriptor> fileDescriptors;
}