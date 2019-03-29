/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.contentclassifier.opennlptartarus.libsvm;

import java.util.List;

import uk.ac.nactem.posstemmer.OpenNlpTartarusSingleton;
import uk.ac.nactem.posstemmer.Token;

public class ClassificationInstance {
	
	private String bugTrackerId;
	private String bugId;
	private String commentId;
	
	private String newsgroupName;
	private long articleNumber;
	private int threadId;
	private int positionFromThreadBeginning;
//	private int positionFromThreadEnd;
	private String subject;
	private List<List<Token>> tokenSentences;
	private List<List<Token>> cleanTokenSentences;

	private String text;
	private String cleanText;
	private String composedId;
	
	public  ClassificationInstance() {	}
	
	public String getComposedId() {
		if (composedId==null) setComposedId();
		return composedId;
	}

	private void setComposedId() {
		if ((bugTrackerId!=null)&&(bugId!=null)&&(commentId!=null))
			composedId = bugTrackerId+"#"+bugId+"#"+commentId;
		else if ((newsgroupName!=null)&&(articleNumber!=0)) 
			composedId = newsgroupName+"#"+articleNumber;
		else {
			System.err.println("Unable to compose ID");
		}
		toString();
	}

	public String getBugTrackerId() {
		return bugTrackerId;
	}
	
	public void setBugTrackerId(String bugTrackerId) {
		this.bugTrackerId = bugTrackerId;
		if (composedId!=null) setComposedId();
	}
	
	public String getNewsgroupName() {
		return newsgroupName;
	}

	public void setNewsgroupName(String newsgroupName) {
		this.newsgroupName = newsgroupName;
		if (composedId!=null) setComposedId();
	}

	public String getBugId() {
		return bugId;
	}

	public void setBugId(String bugId) {
		this.bugId = bugId;
		if (composedId!=null) setComposedId();
	}


	public String getCommentId() {
		return commentId;
	}


	public void setCommentId(String commentId) {
		this.commentId = commentId;
		if (composedId!=null) setComposedId();
	}


	public long getArticleNumber() {
		return articleNumber;
	}
	
	public void setArticleNumber(long articleNumber) {
		this.articleNumber = articleNumber;
		if (composedId!=null) setComposedId();
	}
	
	public int getThreadId() {
		return threadId;
	}

	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}
	
	public int getPositionFromThreadBeginning() {
		return positionFromThreadBeginning;
	}

	public void setPositionFromThreadBeginning(int positionFromThreadBeginning) {
		this.positionFromThreadBeginning = positionFromThreadBeginning;
	}

//	public int getPositionFromThreadEnd() {
//		return positionFromThreadEnd;
//	}

//	public void setPositionFromThreadEnd(int positionFromThreadEnd) {
//		this.positionFromThreadEnd = positionFromThreadEnd;
//	}

	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
		setCleanText();
	}
	
	public void setCleanText() {
		StringBuilder stringBuilder = new StringBuilder();
		for (String line: text.split("\n")) {
			String trimmedLine = line.trim();
			if ( (!trimmedLine.startsWith("<")) && (!trimmedLine.startsWith(">")) ) {
				stringBuilder.append(line);
				stringBuilder.append("\n");
			}
		}
		cleanText = stringBuilder.toString();
	}
	
	public String getCleanText() {
		return cleanText;
	}

	public List<List<Token>> getTokenSentences() {
		if (tokenSentences==null) {
			OpenNlpTartarusSingleton tartarus = OpenNlpTartarusSingleton.getInstance();
			tokenSentences = tartarus.getTagger().tag(text);
//			outputTag(tokenSentences);
		}
		return tokenSentences;
	}

	public List<List<Token>> getCleanTokenSentences() {
		if (cleanTokenSentences==null) {
			OpenNlpTartarusSingleton tartarus = OpenNlpTartarusSingleton.getInstance();
			cleanTokenSentences = tartarus.getTagger().tag(getCleanText());
//			outputTag(cleanTokenSentences);
		}
		return cleanTokenSentences;
	}

//	private void outputTag(List<List<Token>> tokenSentences) {
//	for (List<Token> tokens: tokenSentences)
//		for (Token token: tokens)
//			System.out.println(token.toString());
//		System.out.println();
//}

	@Override
	public String toString() {
		if (newsgroupName!=null)
			return "ClassificationInstance "
					+ "[url=" + newsgroupName + ", threadId=" + threadId
					+ ", articleNumber=" + articleNumber + ", subject=" + subject + "]";
		else
			return "ClassificationInstance "
					+ "[bugTrackerId=" + bugTrackerId + ", bugId=" + bugId
					+ ", commentId=" + commentId + ", subject=" + subject + "]";
			
	}

}
