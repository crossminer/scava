package org.eclipse.scava.platform.communicationchannel.mbox.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.james.mime4j.mboxiterator.CharBufferWrapper;
import org.apache.james.mime4j.mboxiterator.MboxIterator;
import org.eclipse.scava.platform.communicationchannel.mbox.encoding.UTF8toASCII;
import org.eclipse.scava.platform.logging.OssmeterLogger;

public class MBoxParser {
	
	private static final String[] linesToDelete = {"Content-Disposition: attachment; filename*=",
												   "Content-Transfer-Encoding: unknown",
												   "Content-Type: multipart/alternative; boundary=001a11c29d82c0391505267e8b4a",
												   
												   "Content-Type: multipart/alternative;",
												   "boundary=\"b1_03e7115ca651fb88a1fe019fbf8bc91c\""
												   };
	
	private static SimpleDateFormat inputFormat1, inputFormat2, outputFormat1 ,outputFormat2;
	private static OssmeterLogger logger;
	//private static File outputFile;
	
	static{
		logger = (OssmeterLogger) OssmeterLogger.getLogger("platform.communicationchannel.mbox.parser.MBoxParser");
		inputFormat1 = new SimpleDateFormat("'Date:' EEE MMM dd HH:mm:ss yyyy");
		inputFormat2 = new SimpleDateFormat("'Date:' EEE, dd MMM HH:mm:ss yyyy Z");
		outputFormat1 = new SimpleDateFormat("'Date:' EEE, dd MMM yyyy HH:mm:ss");
		outputFormat2 = new SimpleDateFormat("'Date:' EEE, dd MMM yyyy HH:mm:ss Z");
	}
	
	public static List<String> parse(File mbox) {
		
		logger.debug("PARSE MBOX: " + mbox);
		List<String> messages = new ArrayList<String>();
		
		CharsetEncoder ENCODER = Charset.forName("UTF-8").newEncoder();
		
		try {
			for (CharBufferWrapper message : MboxIterator.fromFile(mbox).charset(ENCODER.charset()).build()) {
				logger.debug("Message found:" + message);
				messages.add(message.toString());
			}
		} catch (IOException e) {
			logger.error("Error while reading file", e);
		}
		
		mbox.delete();
		System.gc();
		
		return messages;
		
	}
	
	public static File preprocess(File file) {
		
		File outputFile = null;
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line;
			BufferedWriter writer = null;
			try {
				outputFile = File.createTempFile("temp", ".mbox");
				writer = new BufferedWriter(new FileWriter(outputFile));
				while((line = bufferedReader.readLine()) != null) {
					
					if (!UTF8toASCII.isPureAscii(line)) {
						String encoded = UTF8toASCII.encodeQP(line);
						logger.debug("non ASCII line: |" + line + "|");
						logger.debug("encoded: |" + encoded + "|");
						logger.debug("encoded no new lines: |" + UTF8toASCII.removeSoftNewLines(encoded) + "|");
						logger.debug("decoded: |" + UTF8toASCII.decodeQP(encoded) + "|");
						logger.debug("encodedText: |" + UTF8toASCII.encodeTextQP(line) + "|\n");
						line =  UTF8toASCII.removeSoftNewLines(encoded);
					}
					
					if (line.startsWith("Date: ")) {
						logger.debug("Original Date line: "+line);
						line = correctDate(line);
						logger.debug("Corrected Date line: "+line);
					}

					if (line.startsWith("From: ") && (!line.contains("<"))) {
						String addressString = line.replace("From: ", "").trim();
						try {
							new InternetAddress(addressString);
						} catch (AddressException e) {
							line = line.trim() + " <>";
						}
					}

					for (String lineToDelete: linesToDelete) {
						if (line.equals(lineToDelete)) {
							line="";
						}
					}
					if (line.startsWith("Content-Type: TEXT/PLAIN; charset=X-UNKNOWN")) {
						line = line.replace("; charset=X-UNKNOWN", ";");
					}
					
					if (line.equals("Content-Type: text/plain; charset=Any")) {
						line = line.replace("; charset=Any", ";");
					}
									
					if (line.startsWith("Content-Type: text/plain; charset=x-gbk")) {
						line = line.replace("; charset=x-gbk", "; charset=gbk");
					}
					
					if (line.contains("Content-Type:X-System-Of-Record")) {
						line=line.replace("Content-Type:X-System-Of-Record", "Content-Type;X-System-Of-Record");
					}
					
					if (line.startsWith("Content-Disposition: inline; filename=")) {
						String filename = line.substring("Content-Disposition: inline; filename=".length());
						if (filename.contains(" ") && (!filename.contains("\""))) {
							line = "Content-Disposition: inline; filename=\"" + filename  +"\"";
						}
					}
					
					if (line.startsWith("	filename=")) {
						String filename = line.substring("	filename=".length());
						if (filename.contains(" ") && (!filename.contains("\""))) {
							line = "	filename=\"" + filename  +"\"";
						}
					}
							
					if (line.startsWith("Content-Disposition: attachment; filename=")) {
						String filename = line.substring("Content-Disposition: attachment; filename=".length());
						if (filename.contains(" ") && (!filename.contains("\""))) {
							line = "Content-Disposition: attachment; filename=\"" + filename  +"\"";
						}
						if (filename.contains("; size=")) {
							filename = filename.substring(0, filename.indexOf("; size=")+1);
							line = "Content-Disposition: attachment; filename=" + filename;
						}
					}

					if (line.contains("Content-Disposition:References")) {
						line=line.replace("Content-Disposition:References", "Content-Disposition;References");
					}
					
					if (line.startsWith("To: \"") && line.endsWith("\"\"")) {
						line = line.replace("\"\"", "\"");
					}
						
					if (line.contains("Content-Transfer-Encoding:Message-Id") ||
						line.contains("Content-Transfer-Encoding:Content-Type") ||
						line.contains("Content-Transfer-Encoding:Content-Language") ||
						line.contains("Content-Transfer-Encoding:Subject") ||
						line.contains("Content-Transfer-Encoding:DKIM;") ||
						line.contains("Content-Transfer-Encoding:MIME-Version") ||
						line.contains("Content-Transfer-Encoding:Content-ID") ||
						line.contains("Content-Transfer-Encoding:Date:")) {
							line = line.replace("Content-Transfer-Encoding:", "Content-Transfer-Encoding;");
					}
					
					if (line.contains("content-transfer-encoding:x-trusted-delivery") ||
						line.contains("content-transfer-encoding:content-type:") ||
						line.contains("content-transfer-encoding:subject:") ||
						line.contains("content-transfer-encoding:in-reply-to") ||
						line.contains("content-transfer-encoding:x-mailer") ||
						line.contains("content-transfer-encoding:mime-version") ||
						line.contains("content-transfer-encoding:content-language")) {
							line=line.replace("content-transfer-encoding:", "content-transfer-encoding;");
					}
					
					if (line.contains("content-transfer-encoding : mime-version") ||
						line.contains("content-transfer-encoding : message-id")) {
							line = line.replace("content-transfer-encoding :", "content-transfer-encoding ;");
					}
					
					if (line.contains("charset=iso-8859-10")) {
						line=line.replace("charset=iso-8859-10", "charset=iso-8859-1");
					}

					if (line.contains("charset=\"iso-8859-10\"")) {
						line=line.replace("charset=\"iso-8859-10\"", "charset=\"iso-8859-1\"");
					}
					
					if (line.contains("charset=\"iso-8859-14\"")) {
						line=line.replace("charset=\"iso-8859-14\"", "charset=\"iso-8859-1\"");
					}

					if (line.contains("charset=ISO-8859-10")) {
						line=line.replace("charset=ISO-8859-10", "charset=ISO-8859-1");
					}

					if (line.contains("charset=HZ-GB-2312")) {
						line=line.replace("charset=HZ-GB-2312", "charset=UTF-8");
					}
					
				    writer.write(line+"\n");
				}
			} catch (IOException e) {
				logger.error("Error while parsing file: ", e);
			} finally {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error("Error while closing writer: ", e);
				}
			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			logger.error("File not found: ", e);
		} catch (IOException e) {
			logger.error("Error while closing buffered reader: ",e);
		}
		
		return outputFile;
	}
	
	private static String correctDate(String line) {
		Date date = null;
		try {
			date = inputFormat1.parse(line);
		} catch (ParseException e1) {
			try {
				date = inputFormat2.parse(line);
			} catch (ParseException e2) {
				return line; 
			}
			return outputFormat2.format(date); 
		}
		return outputFormat1.format(date);
	}

	public static void main(String[] args) throws IOException {
		String line = "Date: Wed Jun  11 00:05:04 2011";
//		String line = "Date: Wed, 22 Jun 05:30:16 2005 +0000";
		
		//initialise();
		System.out.println("Original Line: " + line);
		if (line.startsWith("Date: ")) {
			System.out.println("Corrected Line: " + correctDate(line));
		}
		
		String str = "helloslkhellodjladfjhello";
		String findStr = "hello";
		System.out.println(str.split(findStr, -1).length-1);
	}
	

}
