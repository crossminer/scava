package org.eclipse.scava.nlp.tools.preprocessor.fileparser;

public class FileContent {
	
	private String content;
	private boolean htmlFormated;
	
	public FileContent(String content, boolean htmlFormated) {
		this.content=content;
		this.htmlFormated=htmlFormated;
	}
	
	public String getContent() {
		return content;
	}
	
	public boolean isHtmlFormat() {
		return htmlFormated;
	}
}
