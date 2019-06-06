package org.eclipse.scava.nlp.tools.preprocessor.fileparser;

public class FileContent {
	
	private String content;
	private String mime;
	private String formatName;
	private boolean htmlFormated;
	
	public FileContent(String content, String mime, String formatName, boolean htmlFormated) {
		this.content=content;
		this.mime=mime;
		this.formatName=formatName;
		this.htmlFormated=htmlFormated;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getMime() {
		return mime;
	}
	
	public String getFormatName() {
		return formatName;
	}
	
	public boolean isHtmlFormat() {
		return htmlFormated;
	}
}
