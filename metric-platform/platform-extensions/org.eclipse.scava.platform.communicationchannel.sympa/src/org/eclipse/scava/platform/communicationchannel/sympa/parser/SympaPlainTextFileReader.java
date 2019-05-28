package org.eclipse.scava.platform.communicationchannel.sympa.parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class SympaPlainTextFileReader {

	static Pattern pattern = Pattern.compile("\\/arctxt[^\\/]+\\/\\d{1,2}_");

	public static List<Email> parseFolder(Path inputFolder) {

		List<Email> emails = new ArrayList<Email>();

		Stream<Path> filePaths;

		try {

			filePaths = Files.walk(inputFolder);

			for (Path path : filePaths.filter(Files::isRegularFile).toArray(Path[]::new))

			{
				File file = path.toFile();

				if (pattern.matcher(path.toString()).find() == true) {

					PlainTextMessage message = new PlainTextMessage(file);
					Email email = new Email(message);
					emails.add(email);
					file.delete();

				} else {
					//deletes all files that are not required for processing...
					file.delete();
				}
			}
			
//			// This reader clears up after it's self...
//			File root = Paths.get(inputFolder).toFile();
//			root.delete();
			

		} catch (IOException e) {

			e.printStackTrace();
		}
		return emails;
	}

}
