module org::maracas::Extractor
import lang::java::m3::AST;
import lang::java::m3::Core;
import analysis::m3::TypeSymbol;

import org::maracas::Maracas;
import org::maracas::RunAll;
import org::maracas::delta::Detector;
import org::maracas::delta::Delta;
import org::maracas::delta::vis::Visualizer;
import ValueIO;
import ParseTree;

import IO;
import Relation;
import Set;
import String;
import List;

void methodBreakingChangesExporter(loc delta, loc report=|file:///Users/juri/Desktop/report_spring_data_mongo/|, loc output=|file:///Users/juri/Desktop/rascal-TEST.txt|) {
	Delta d = parseDeltaFile(delta);
	Delta mbc = methodDelta(d);
	writeHtml(report + "Delta.html", d);
	writeFile(|file:///Users/juri/Desktop/delta.txt|,mbc);
	writeFile(output, ""); 
	println("________REMOVED____________");
	for(m <- mbc.removed){
		appendToFile(output,"<m.elem.path[1..]>#null\n");
		println(".");
	}
	println("________CHANGE PUBLIC TO PRIVATE________");
	for(m<- mbc.accessModifiers){	
		if(m.mapping[0]==\public())
			appendToFile(output,"<m.elem.path[1..]>#null\n");
		println(".");
	}
	println("_______RENAMED____________");
	for(m <- mbc.renamed){
		appendToFile(output,"<m.mapping[0].path[1..]>#<m.mapping[1].path[1..]>\n");
		println(".");
	}
	println("_______MOVED_________");
	for(m <- mbc.moved){
		appendToFile(output,"<m.mapping[0].path[1..]>#<m.mapping[1].path[1..]>\n");
		println(".");
	}
	println("_______CHANGE PARAM LIST_________");
	for(m <- mbc.paramLists){
		appendToFile(output,"<m.elem.path[1..-1]><m.mapping[0]>)#<m.elem.path[1..-1]><m.mapping[1]>)\n");
		println(".");
	}
	println("_______CHANGE RETURN TYPE_________");
	for(m <- mbc.types){
		appendToFile(output,"<m.elem.path[1..-1]><m.mapping[0]>)#<m.elem.path[1..-1]><m.mapping[1]>)\n");
		println(".");
	}
	for(m <- mbc.deprecated) {
		appendToFile(output,"<m.elem.path[1..]>#null\n");
		println(".");
	}
}

void myRunAll(loc libs=|file:///Users/ochoa/Documents/cwi/crossminer/data/api-migration-dataset/sonarqube/erase/|, loc clients=|file:///Users/ochoa/Documents/cwi/crossminer/data/api-migration-dataset/sonarqube/clients/|,loc output=|file:///Users/ochoa/Desktop/results.txt|){
	
	set[loc] libraries = walkJARs(libs);
	for (lib1 <- libraries){
		for (lib2 <- libraries)
			if (lib1 != lib2){
				try {
					str v1 = lib1.path[findLast(lib1.path,"/")+1..-4];
					v1 = replaceLast(v1, "sonar-plugin-api-","sonar-plugin-api__");
					loc clients_loc = clients + v1;
					if(exists(clients_loc)){
						println("Computing Delta model <lib1.path> <lib2.path>");
						
						Delta d;
						if(!exists(libs + "Delta" + lib1.file + "-" + lib2.file + ".delta"))
							d = delta(lib1, lib2);
						else 
							d = parseDeltaFile(clients_loc);
						println("Pruning breaking changes...");
						d = breakingChanges(d);

						set[loc] clients = walkJARs(clients_loc);
						int i = 0;
						int count = size(clients);
						
						for (client <- clients) {
							try {
								i = i + 1;
								println("[<i>/<count>] Computing detection model for <client>... ");
						
								M3 m = createM3FromJar(client);	
								set[Detection] detects = detections(m, d);
								
								if (size(detects) > 0)
									writeBinaryValueFile(clients_loc + "detection" + lib1.file + "-" + lib2.file + (client.file + ".detection"), detects);
								writeFile(output, "<lib1.path>,<lib2.path>,<client>, <size(detects)>");
							}
							catch e:
								writeFile(output, "<lib1.path>,<lib2.path>,<client>,-1 \n <e> \n \n");
					
						}
					}
				}
				catch e:
						writeFile(output, "<lib1>,<lib2>,-1,-1 \n <e> \n \n");
				}
			}
}


void allVersionsDeltas(loc libs=|file:///Users/juri/development/git/aethereal/aethereal/dataset/libraries/|, loc report=|file:///Users/juri/Desktop/report_spring_data_mongo/|, loc output=|file:///Users/juri/Desktop/rascal-TEST.txt|){
	set[loc] libraries = walkJARs(libs);
	for (lib1 <- libraries){
		for (lib2 <- libraries)
			if(lib1 != lib2)
				try{
					println("Computing: <lib1> <lib2>");
					methodBreakingChangesExporter(lib1, lib2);
				}
				catch:
					println("\t###ERROR: <lib1> <lib2>");
	}
}


void methodBreakingChangesExporter(loc libV1, loc libV2, loc report=|file:///Users/juri/Desktop/report_sonarcube/|, loc output=|file:///Users/juri/Desktop/rascal-TEST.txt|) {
	println(report + ("Delta" + libV1.file + "-" + libV2.file + ".delta"));
	if(!exists(report + ("Delta" + libV1.file + "-" + libV2.file + ".delta"))){
		Delta d = delta(libV1, libV2);
		writeFile(|file:///Users/juri/Desktop/delta.txt|,d);
		writeFile(output, ""); 
		writeBinaryValueFile(report + ("Delta" + libV1.file + "-" + libV2.file + ".delta"), d);
		writeHtml(report + ("Delta" + libV1.file + "-" + libV2.file + ".html"), d);
	}
}

Delta parseDeltaFile(loc report) {
     loc entry = report;
     Delta deltas = readBinaryValueFile(#Delta, entry);
     return deltas;
}