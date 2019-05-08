package org.eclipse.scava.nlp.tools.plaintext.bugtrackers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

import org.eclipse.scava.nlp.tools.plaintext.PlainTextObject;
import org.eclipse.scava.nlp.tools.preprocessor.htmlparser.HtmlParser;

public class PlainTextRedmine {
	
private static Pattern newlines;
	
	static
	{
		newlines=Pattern.compile("\\r\\n");
	}
	
	public static PlainTextObject process(String text)
	{
		if(text==null)
			return new PlainTextObject(new ArrayList<String>(Arrays.asList("")));
		text=newlines.matcher(text).replaceAll("</br>");
		return new PlainTextObject(HtmlParser.parse(text));
	}
	
}
