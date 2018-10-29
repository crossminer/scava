package org.eclipse.scava.crossflow.runtime.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class CsvWriter {

	private CSVPrinter csvPrinter;

	/**
	 * Initializes writer and specifies output location
	 * 
	 * @param csvFileLocation location of CSV file to be written to
	 * @param header          record columns in CSV file to be written to
	 */
	public CsvWriter(String csvFileLocation, String... header) {

		try {
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(csvFileLocation));
			csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(header));
		} catch (IOException e) {
			System.err.println("Failed to initialize CSV writer with file: " + csvFileLocation);
			e.printStackTrace();
		}

	}

	public void writeRecord(Object... record) {
		try {
			csvPrinter.printRecord(Arrays.asList(record));
		} catch (IOException e) {
			System.err.println("Failed to write CSV record: " + Arrays.asList(record));
			e.printStackTrace();
		}
	}

	public void flush() {
		try {
			csvPrinter.flush();
		} catch (IOException e) {
			System.err.println("Failed to flush CSV file.");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {

		CsvWriter writer = new CsvWriter("record-out.csv", "FileExtension", "Keyword");

		writer.writeRecord("atl", "rule");
		writer.writeRecord("qvto", "transformation");
		writer.writeRecord("emf", "class");
		writer.writeRecord("mtl", "template");
		writer.writeRecord("egl", "var");
		writer.writeRecord("eiq", "pattern");
		writer.writeRecord("ecore", "gmf");
		writer.writeRecord("gmfgraph", "figure");
		writer.writeRecord("eol", "var");
		writer.writeRecord("xtext", "grammar");
		writer.writeRecord("etl", "transformation");
		writer.writeRecord("ecore", "Eclass");
		writer.writeRecord("evl", "context");
		writer.writeRecord("ocl", "context");
		writer.writeRecord("odesign", "node");
		writer.writeRecord("henshin", "rule");
		writer.writeRecord("m2t", "texttransformation");
		writer.writeRecord("kmt", "class");
		writer.writeRecord("xcore", "class");
		writer.writeRecord("javajet", "jet");
		writer.writeRecord("cs", "syntaxdef");
		writer.writeRecord("xpt", "for");
		
		writer.flush();

	}

}
