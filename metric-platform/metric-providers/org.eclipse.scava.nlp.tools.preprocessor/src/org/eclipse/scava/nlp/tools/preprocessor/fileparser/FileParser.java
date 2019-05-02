package org.eclipse.scava.nlp.tools.preprocessor.fileparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.xml.sax.SAXException;

public class FileParser
{
	
	private static OssmeterLogger logger;
	
	static
	{
		logger = (OssmeterLogger) OssmeterLogger.getLogger("nlp.tools.preprocessor.fileparser");
	}
	
	public static String extractTextAsString(File file)
	{
		try {
			FileInputStream fis = new FileInputStream(file);
			return extractTextAsString(fis);
		} catch (FileNotFoundException e) {
			logger.error("File not found:", e);
			e.printStackTrace();
		}
		return "";
	}
	
	public static String extractTextAsString(InputStream stream)
	{
		BodyContentHandler handler = new BodyContentHandler();
	    AutoDetectParser parser = new AutoDetectParser();
	    Metadata metadata = new Metadata();
	    try {
			parser.parse(stream, handler, metadata);
			return handler.toString();
		} catch (IOException | SAXException | TikaException e) {
			logger.error("Error while parsing the file into text:", e);
			e.printStackTrace();
		}
	    
	    return "";
	}
	
	public static List<String> extractTextAsList(File file)
	{
		return stringToList(extractTextAsString(file));
	}
	
	public static List<String> extractTextAsList(InputStream inputStream)
	{
		return stringToList(extractTextAsString(inputStream));
	}
	
	
	private static List<String> stringToList(String text)
	{
		List<String> split = Arrays.asList(text.split("\\v+"));
		return split;
	}
}
