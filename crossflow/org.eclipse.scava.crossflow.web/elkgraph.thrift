// Generate JS code: thrift --gen js -out WebContent/js/gen elkgraph.thrift
// Generate Java code: thrift --gen java -out src elkgraph.thrift
namespace java org.eclipse.scava.crossflow.web.elkgraph

typedef i32 int

service ElkgraphService {
	
	void startLanguageServer();
	void stopLanguageServer();
	bool isLanguageServerRunning();
	
	ElkgraphServiceDiagnostics getDiagnostics();
	
}


struct ElkgraphServiceDiagnostics {
	1: optional bool languageServerRunning;
	2: optional string rootDirectory;
}
