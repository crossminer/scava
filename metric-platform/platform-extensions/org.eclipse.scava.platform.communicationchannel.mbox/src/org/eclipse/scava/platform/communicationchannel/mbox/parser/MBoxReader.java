package org.eclipse.scava.platform.communicationchannel.mbox.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.platform.Date;

public class MBoxReader {
	
	public static File[] getFiles(String inputFolderString) {
		File inputFolder = new File(inputFolderString);
		if (!inputFolder.isDirectory()) {
			System.out.println(inputFolderString + " is not a valid directory!");
		}
		return inputFolder.listFiles();
	}
	
	public static List<Email> parseFile(File file, Date date) {
		List<Email> emails = new ArrayList<Email>();
		System.err.println("Path: " + file.getAbsolutePath());
		List<String> stringMessages = MBoxParser.parse(MBoxParser.preprocess(file));
		for (String stringMessage: stringMessages) {
			MBoxMessage message = new MBoxMessage(stringMessage);
			if(message!=null)
			{
				if(message.getDate()!=null)
				{
					if (date.compareTo(new Date(message.getDate())) == 0)
					{
						Email email = new Email(message);
						emails.add(email);
					}
				}
			}
		}
		return emails;
	}

}
