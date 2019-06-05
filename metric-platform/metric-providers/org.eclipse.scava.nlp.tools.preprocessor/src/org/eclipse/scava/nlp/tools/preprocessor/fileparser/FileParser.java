package org.eclipse.scava.nlp.tools.preprocessor.fileparser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.apache.tika.detect.Detector;
import org.apache.tika.exception.TikaException;
import org.apache.tika.exception.ZeroByteFileException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.ToXMLContentHandler;
import org.eclipse.scava.nlp.tools.preprocessor.markdown.MarkdownParser;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class FileParser
{
	
	private static OssmeterLogger logger;
	private static AutoDetectParser parser;
	private static Detector detector;
	private static HashMap<String,String> supportedFiles = new HashMap<String,String>();
	private static HashMap<String,String> contentHandlerType = new HashMap<String,String>();
	private static Pattern mediaTypePattern;
	
	static
	{
		logger = (OssmeterLogger) OssmeterLogger.getLogger("nlp.tools.preprocessor.fileparser");
		FileParserSingleton singleton = FileParserSingleton.getInstance();
		parser = singleton.getParser();
		supportedFiles = singleton.getSupportedFiles();
		contentHandlerType = singleton.getContentHandlerType();
		detector = parser.getDetector();
		mediaTypePattern= Pattern.compile(";.+$");
	}
	
	public static HashMap<String, String> getSupportedFiles()
	{
		return supportedFiles;
	}
	
	public static boolean isSupported(File file) throws FileNotFoundException, IOException
	{
		FileInputStream fis = fileToInputStream(file);
		BufferedInputStream bif = new BufferedInputStream(fis);
		Metadata metadata = new Metadata();
		metadata.add(Metadata.RESOURCE_NAME_KEY, file.getName());
		boolean supported = isSupported(bif, metadata);
		bif.close();
		fis.close();
		return supported;
		
	}
	
	private static boolean isSupported(BufferedInputStream stream, Metadata metadata) throws IOException
	{
		return isSupported(detectMediaType(stream, metadata));
	}
	
	private static boolean isSupported(MediaType mediaType)
	{
		if(mediaType==null)
			return false;
		if(supportedFiles.containsKey(mediaTypeString(mediaType)))
			return true;
		return false;
	}
	
	private static MediaType detectMediaType(BufferedInputStream stream, Metadata metadata) throws IOException
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
	

	private static FileInputStream fileToInputStream(File file) throws FileNotFoundException
	{
		try {
			FileInputStream fis = new FileInputStream(file);
			return fis;
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
	public static FileContent extractText(File file) throws Exception
	{
		FileInputStream fis = fileToInputStream(file);
		BufferedInputStream bif = new BufferedInputStream(fis);
		Metadata metadata = new Metadata();
		metadata.add(Metadata.RESOURCE_NAME_KEY, file.getName());
		FileContent fileContent = extractText(bif, metadata);
		bif.close();
		fis.close();
		return fileContent;
	}
	
	/**
	 * 
	 * @param bufferedStream
	 * @return
	 * @throws Exception 
	 */
	private static FileContent extractText(BufferedInputStream bufferedStream, Metadata metadata) throws Exception
	{
		MediaType mediaType;
		ContentHandler handler=null;
		String handlerType;
	    try {
	    	mediaType=detectMediaType(bufferedStream, metadata);
	    	if(isSupported(mediaType))
	    	{
	    		handlerType=contentHandlerType.get(mediaTypeString(mediaType));
	    		switch (handlerType)
	    		{
	    			case "HTML":
	    				handler = new ToXMLContentHandler();
	    				break;
	    			case "MARKDOWN":
	    			case "PLAIN":
	    				handler = new BodyContentHandler(-1);
	    				break;
	    		}
	    		if(handler!=null)
	    		{
		    		parser.parse(bufferedStream, handler, metadata);
		    		FileContent fileContent=null;
		    		switch (handlerType)
		    		{
		    			case "HTML":
		    				fileContent= new FileContent(handler.toString(), true);
		    				break;
		    			case "MARKDOWN":
		    				fileContent= new FileContent(MarkdownParser.parse(handler.toString()), true);
		    				break;
		    			case "PLAIN":
		    				fileContent= new FileContent(handler.toString(), false);
		    				break;
		    		}
	    			return fileContent;
	    		}
	    		throw new UnsupportedOperationException("Impossible to determine how to handle the file: ");
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
}
