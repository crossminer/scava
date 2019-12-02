package org.eclipse.scava.platform.communicationchannel.mbox.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MBoxReader {
	
	private static boolean verbose;

	public static File[] getFiles(String inputFolderString) {
		File inputFolder = new File(inputFolderString);
		if (!inputFolder.isDirectory()) {
			System.out.println(inputFolderString + " is not a valid directory!");
		}
		return inputFolder.listFiles();
	}
	
	private static void parseFolder(String inputFolder) throws IOException {
		//MBoxParser.initialise();
		for (File file: getFiles(inputFolder)) {
			if (!file.isDirectory() && file.length()>0) {
				parseFile(file);
			}
		}
	}
	
	public static List<Email> parseFile(File file) {
		List<Email> emails = new ArrayList<Email>();
		System.err.println("Path: " + file.getAbsolutePath());
		List<String> stringMessages = MBoxParser.parse(MBoxParser.preprocess(file, verbose), verbose);
		for (String stringMessage: stringMessages) {
			MBoxMessage message = new MBoxMessage(stringMessage, verbose);
			if(message!=null)
			{
				Email email = new Email(message);
				emails.add(email);
			}
		}
		return emails;
	}

	public static void main(String[] args) throws IOException {
		verbose = false;
	    String directoryPath = "K:/eclipse.mboxes/";
//	    String directoryPath = "mboxes";
		if (verbose) {
			System.out.println("Parsing folder: " + directoryPath);
		}
    	parseFolder(directoryPath);

	}

}
