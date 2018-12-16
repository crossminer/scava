package org.eclipse.scava.crossflow.runtime.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class CsvWriter {

	private CSVPrinter csvPrinter;

	public CsvWriter(File file, String...header ) throws IOException {
		
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			file.createNewFile();
		}
		
		BufferedWriter writer = Files.newBufferedWriter(Paths.get(file.getAbsolutePath()));
		csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(header));
	}
	
	public CsvWriter(String csvFileLocation, String... header) throws IOException {
		this(new File(csvFileLocation), header);
	}

	public void writeRecord(Object... record) throws IOException {
		csvPrinter.printRecord(Arrays.asList(record));
	}

	public void flush() throws IOException {
		csvPrinter.flush();
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
