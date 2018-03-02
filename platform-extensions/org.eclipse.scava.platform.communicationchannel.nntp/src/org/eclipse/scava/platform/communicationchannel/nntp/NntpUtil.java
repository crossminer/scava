/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.communicationchannel.nntp;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import javax.mail.internet.MimeUtility;

import org.apache.commons.net.io.DotTerminatedMessageReader;
import org.apache.commons.net.nntp.Article;
import org.apache.commons.net.nntp.NNTPClient;
import org.apache.commons.net.nntp.NewsgroupInfo;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;


public class NntpUtil {

	public static NNTPClient connectToNntpServer(NntpNewsGroup newsgroup) {

		NNTPClient client = new NNTPClient();
		client.setDefaultPort(newsgroup.getPort());
		String serverUrl = newsgroup.getUrl();		
		
		if (serverUrl.endsWith("/")) {
			serverUrl = newsgroup.getUrl().substring(0, newsgroup.getUrl().lastIndexOf("/"));
		}
		
		try {
			client.connect(serverUrl);
			if (newsgroup.getAuthenticationRequired()) {
				client.authenticate(newsgroup.getUsername(), newsgroup.getPassword());
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
	        System.err.println("SocketException while connecting to NNTP server: '"+ 
	        		newsgroup.getUrl() + "': " + e.getMessage());
	        e.printStackTrace();
//	        System.exit(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
	        System.err.println("IOException while connecting to NNTP server: '"+ 
	        		newsgroup.getUrl() + "': " + e.getMessage());
	        e.printStackTrace();
//	        System.exit(1);
		}
		return client;
	}
	
	public static NewsgroupInfo selectNewsgroup(NNTPClient client, NntpNewsGroup newsgroup) {
		String newsgroupName = newsgroup.getNewsGroupName();//newsgroup.getUrl().substring(newsgroup.getUrl().lastIndexOf("/") + 1);
		NewsgroupInfo newsgroupInfo = new NewsgroupInfo();
		try {
			client.selectNewsgroup(newsgroupName, newsgroupInfo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
	        System.err.println("IOException while selecting newsgroup: '"+ 
	        		newsgroupName + "': " + e.getMessage());
	        e.printStackTrace();
		}
		return newsgroupInfo;
	}

	public static void disconnectFromNntpServer(NNTPClient nntpClient) {
		try {
			nntpClient.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
	        System.err.println("IOException while disconnectiong from NNTP server: "+ e.getMessage());
		}
	}

	public static Article[] getArticleInfo(NNTPClient nntpClient,
			int startArticleNumber, int endArticleNumber) throws IOException {
		Reader reader = null;
		Article[] articles = null;
		reader = (DotTerminatedMessageReader) nntpClient.retrieveArticleInfo(
					startArticleNumber, endArticleNumber);

		if (reader != null) {
			String theInfo = readerToString(reader);
			StringTokenizer st = new StringTokenizer(theInfo, "\n");

			// Extract the article information
			// Mandatory format (from NNTP RFC 2980) is :
			// Subject\tAuthor\tDate\tID\tReference(s)\tByte Count\tLine Count

			int count = st.countTokens();
			articles = new Article[count];
			int index = 0;

			while (st.hasMoreTokens()) {
				StringTokenizer stt = new StringTokenizer(st.nextToken(), "\t");
				Article article = new Article();
				article.setArticleNumber(Integer.parseInt(stt.nextToken()));
				article.setSubject(decodeSubject(stt.nextToken()));
				article.setFrom(stt.nextToken());
				article.setDate(stt.nextToken());
				article.setArticleId(stt.nextToken());
				article.addHeaderField("References", stt.nextToken());
				articles[index++] = article;
			}
		} else {
			return null;
		}

		return articles;
	}
	
	public static Article getArticleInfo(NNTPClient client, int articleNumber)
			throws IOException {
		return getArticleInfo(client, articleNumber, articleNumber)[0];
/*		DotTerminatedMessageReader reader = (DotTerminatedMessageReader) client.retrieveArticleInfo(articlePointer.articleNumber);
	
		
		System.err.println("reader: " + readerToString(reader));
		

		if (reader != null) {
			String theInfo = readerToString(reader);
			StringTokenizer st = new StringTokenizer(theInfo, "\n");

			
			// Extract the article information
			// Mandatory format (from NNTP RFC 2980) is :
			// Subject\tAuthor\tDate\tID\tReference(s)\tByte Count\tLine Count

			String nextToken = st.nextToken();
			System.out.println("nextToken: " + nextToken);
			while (st.hasMoreTokens()) {
				StringTokenizer stt = new StringTokenizer(nextToken, "\t");
				article = new Article();
				String article_no = stt.nextToken(); 
				System.out.println("article_no: " + article_no);
				try {
					int an = Integer.parseInt(article_no);
					article.setArticleNumber(an);
				} catch (Exception e) {
					// TODO: handle exception
				} 
				String subject = stt.nextToken();
				
				article.setSubject(subject);
				article.setFrom(stt.nextToken());
				article.setDate(stt.nextToken());
				article.setArticleId(stt.nextToken());
				article.addHeaderField("References", stt.nextToken());
			}
		} else {
			return article;
		}

		return article;*/
		}

	protected static String decodeSubject(String subject) {
		if (subject.contains("=?utf-8?")) {
//			System.err.println("D: subject:\t" + subject);
			String decodedSubject = "";
        	for (String str: subject.replace("=?utf-8?", " =?utf-8?").split("\\s+"))
    			try {
    				decodedSubject += MimeUtility.decodeText(str);
    			} catch (UnsupportedEncodingException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
//    		System.err.println("D: decoded:\t" + decodedSubject);
        	return decodedSubject;
    	} else
    		return subject;
    }

	public  static String getArticleBody(NNTPClient client, int articleNumber)
			throws IOException {
			String articleBody = null;
			Reader reader = (DotTerminatedMessageReader) client.retrieveArticleBody(articleNumber);

			if (reader != null) {
				articleBody = readerToString(reader);
			} else {
				return articleBody;
			}
			return articleBody;
		}
	
	public static String readerToString(Reader reader) {
		String temp = null;
		StringBuffer sb = null;
		BufferedReader bufReader = new BufferedReader(reader);

		sb = new StringBuffer();
		try {
			temp = bufReader.readLine();
			while (temp != null) {
				sb.append(temp);
				sb.append("\n");
				temp = bufReader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

	static SimpleDateFormat[] sdfList = new SimpleDateFormat[]{
			new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz"),
			new SimpleDateFormat("dd MMM yyyy HH:mm:ss zzz"),
			new SimpleDateFormat("EEE, dd MMM yyyy HH:mm zzz (Z)"),
			new SimpleDateFormat("EEE, dd MMM yyyy HH:mm zzz"),
			new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss"),
			new SimpleDateFormat("dd MMM yyyy HH:mm:ss"),
			new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss Z"),
			new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy"),
			new SimpleDateFormat("yyyyMMdd")
		};
	
    public static Date parseDate(String dateString) {
    	for (SimpleDateFormat sdf: sdfList) {
    		ParsePosition ps = new ParsePosition(0);
    		Date result = sdf.parse(dateString, ps);
    		if (ps.getIndex() != 0)
    			return result;
    	}
    	System.err.println("\t\t" + dateString + " cannot be parsed!\n");
		return null;
    }

}
