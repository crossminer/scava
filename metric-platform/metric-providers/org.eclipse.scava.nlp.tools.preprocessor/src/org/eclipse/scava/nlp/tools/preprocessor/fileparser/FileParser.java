package org.eclipse.scava.nlp.tools.preprocessor.fileparser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.tika.detect.Detector;
import org.apache.tika.exception.TikaException;
import org.apache.tika.exception.ZeroByteFileException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.xml.sax.SAXException;

public class FileParser
{
	
	private static OssmeterLogger logger;
	private static AutoDetectParser parser;
	private static Metadata metadata;
	private static Detector detector;
	private static HashMap<String,String> supportedFiles = new HashMap<String,String>();
	private static Pattern mediaTypePattern;
	
	static
	{
		logger = (OssmeterLogger) OssmeterLogger.getLogger("nlp.tools.preprocessor.fileparser");
		FileParserSingleton singleton = FileParserSingleton.getInstance();
		parser = singleton.getParser();
		metadata = singleton.getMetadata();
		supportedFiles = singleton.getSupportedFiles();
		detector = parser.getDetector();
		mediaTypePattern= Pattern.compile(";.+$");
	}
	
	public static HashMap<String, String> getSupportedFiles()
	{
		return supportedFiles;
	}
	
	public static boolean isSupported(File file) throws FileNotFoundException, IOException
	{
		return isSupported(fileToStream(file));
	}
	
	public static boolean isSupported(BufferedInputStream stream) throws IOException
	{
		return isSupported(detectMediaType(stream));
	}
	
	private static boolean isSupported(MediaType mediaType)
	{
		if(mediaType==null)
			return false;
		if(supportedFiles.containsKey(mediaTypeString(mediaType)))
			return true;
		return false;
	}
	
	private static MediaType detectMediaType(BufferedInputStream stream) throws IOException
	{
		try {
			return detector.detect(stream, metadata);
		}
		catch (IOException e) {
			logger.error("Error while detecting the media type:", e);
			e.printStackTrace();
			throw e;
		}
	}
	
	private static String mediaTypeString (MediaType mediaType)
	{
		return mediaTypePattern.matcher(mediaType.toString()).replaceAll("");
	}
	
	private static BufferedInputStream fileToStream(File file) throws FileNotFoundException
	{
		try {
			return new BufferedInputStream(new FileInputStream(file));
		}
		catch (FileNotFoundException e) {
			logger.error("File not found:", e);
			throw e;
		}
	}
	
	
	
	/**
	 * 
	 * @param file
	 * @return Null if the file is not supported
	 * @throws Exception 
	 */
	public static String extractTextAsString(File file) throws Exception
	{
		return extractTextAsString(fileToStream(file)); 
	}
	
	/**
	 * 
	 * @param stream
	 * @return
	 * @throws Exception 
	 */
	public static String extractTextAsString(BufferedInputStream stream) throws Exception
	{
		BodyContentHandler handler = new BodyContentHandler();
	    try {
	    	if(isSupported(stream))
	    	{
	    		parser.parse(stream, handler, metadata);
	    		return handler.toString();
	    	}
	    	throw new UnsupportedOperationException("File is not supported: ");
		}
	    catch (UnsupportedOperationException e)
	    {
	    	throw e;
	    }
	    catch (ZeroByteFileException e)
	    {
	    	throw new UnsupportedOperationException("File is empty: ");
	    }
	    catch (IOException | SAXException | TikaException e) {
			logger.error("Error while parsing the file into text: ", e);
			e.printStackTrace();
			throw e;
		}
	    
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 * @throws Exception 
	 */
	public static List<String> extractTextAsList(File file) throws Exception
	{
		return stringToList(extractTextAsString(file));
	}
	
	/**
	 * 
	 * @param inputStream
	 * @return
	 * @throws Exception 
	 */
	public static List<String> extractTextAsList(BufferedInputStream inputStream) throws Exception
	{
		return stringToList(extractTextAsString(inputStream));
	}
	
	
	private static List<String> stringToList(String text)
	{
		List<String> split = Arrays.asList(text.split("\\v+"));
		return split;
	}
}
