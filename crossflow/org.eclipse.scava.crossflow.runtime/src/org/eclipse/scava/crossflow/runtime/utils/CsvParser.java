package org.eclipse.scava.crossflow.runtime.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class CsvParser {
	
	private Iterable<CSVRecord> records;
	
	/**
	 * Initializes parser and loads records into memory
	 * 
	 * @param csvFileLocation location of CSV file to be parsed
	 */
	public CsvParser(String csvFileLocation) {
		Reader reader;
		
		try {
			reader = new FileReader(csvFileLocation);
		} catch (FileNotFoundException e) {
			System.err.println("Failed to find CSV file: " + csvFileLocation);
			e.printStackTrace();
			return;
		}	
		
		// loading all records into memory
		try {
			records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(reader);
		} catch (IOException e) {
			System.err.println("Failed to parse CSV file: " + csvFileLocation);
			e.printStackTrace();
		}
	}
	
	/**
	 * @return iterable records excluding header
	 */
	public Iterable<CSVRecord> getRecordsIterable() {
		return records;
	}
	
	/**
	 * @return list of records excluding header
	 */
	public List<CSVRecord> getRecordsList() {
		List<CSVRecord> recordList = new LinkedList<CSVRecord>();
		for (CSVRecord record : records) {
			recordList.add(record);
		}
		return recordList;
	}
	
	/**
	 * prints record number followed by record entries separated by comma
	 */
	public void printRecords() {
		for (CSVRecord record : records) {
			for ( int i=0; i<record.size()-1; i++ ) {
				System.out.print(record.getRecordNumber() + ": " + record.get(i) + ", ");
			}
			System.out.println(record.get(record.size()-1));
		}
	}

	public static void main(String args[]) throws IOException {	
		CsvParser parser = new CsvParser("record-in.csv");
		parser.printRecords();
	}
	
}
