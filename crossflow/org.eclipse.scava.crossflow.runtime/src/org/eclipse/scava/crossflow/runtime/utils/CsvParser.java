package org.eclipse.scava.crossflow.runtime.utils;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CsvParser {
	
	protected CSVParser parser;
	
	public CsvParser(String path) throws Exception {
		Reader reader = new FileReader(path);
		CSVFormat format = CSVFormat.RFC4180.withFirstRecordAsHeader();
		parser = format.parse(reader);
	}
	
	public Iterable<CSVRecord> getRecordsIterable() {
		return parser;
	}
	
	public List<CSVRecord> getRecordsList() throws IOException {
		return parser.getRecords();
	}
	
}
