module org::maracas::pipeline::quatlaas::Corpus

import IO;
import lang::csv::IO;
import lang::java::m3::Core;
import org::maracas::measure::java::Size;
import Set;
import String;
import ValueIO;

alias Matrix = rel[
		str name,
		loc location,
		int bytes,
		int locs,
		int clocs,
		int elocs,
		int declarations,
		int classes,
		int interfaces,
		int types,
		int methods,
		int fields,
		int methodInvocations,
		int fieldAccesses,
		int extends,
		int implements
	];
	
void computeMatrix(loc corpus, loc m3s, loc output) {
	Matrix matrix = {};
	
	for (e <- listEntries(corpus), !startsWith(e, ".")) {
		println("Computing data related to <e>...");
		
		loc entry = corpus + e;
		loc entryM3 = m3s + "<e>.m3";
		M3 m = readBinaryValueFile(#M3, entryM3);
		
		str name = e;
		loc location = entry;
		int bytes = countBytes(entry);
		int locs = countLOCs(entry);
		int clocs = countCLOCs(entry);
		int elocs = locs - clocs;
		int declarations = size(m.declarations);
		int classesDecl = size(classes(m));
		int interfacesDecl = size(interfaces(m));
		int typesDecl = classesDecl + interfacesDecl;
		int methodsDecl = size(methods(m));
		int fieldsDecl = size(fields(m));
		int methodInvocations = size(m.methodInvocation);
		int fieldAccesses = size(m.fieldAccess);
		int extends = size(m.extends);
		int implements = size(m.implements);
		
		matrix += <
			name,
			location,
			bytes,
			locs,
			clocs,
			elocs,
			declarations,
			classesDecl,
			interfacesDecl,
			typesDecl,
			methodsDecl,
			fieldsDecl,
			methodInvocations,
			fieldAccesses,
			extends,
			implements
		>;
	}
	
	writeCSV(matrix, output);
}

void computeM3s(loc corpus, loc output, bool overwrite=false) {
	for (e <- listEntries(corpus)) {
		loc entry = corpus + e;
		
		if (isDirectory(entry)) {
			loc mLoc = output + "<e>.m3"; 
			
			if (overwrite || !exists(mLoc)) {
				println("Computing <e> M3...");
				M3 m = createM3FromDirectory(entry);
				println("Serializing <e> M3...");
				writeBinaryValueFile(mLoc, m);
			}
		}	
	}
}

