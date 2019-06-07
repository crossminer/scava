package org.eclipse.scava.nlp.classifiers.documentation;

import java.util.List;
import java.util.Map.Entry;

import org.eclipse.scava.nlp.tools.preprocessor.htmlparser.HtmlParser;

class HtmlHeadingsFinder {
	
	private static String[] headingsHTML = {"h1","h2","h3","h4","h5","h6"};

	public static List<String> find(String htmlCode, boolean fromPDF)
	{
		List<Entry<String, String>> tagsText = HtmlParser.parseWithTags(htmlCode);
		//In PDF only the headings will be parsed with li, these will be located at the end of the document.
		if(fromPDF)
			return HtmlParser.filterParsedHtmlWithTags(tagsText, "li");
		else
			return HtmlParser.filterParsedHtmlWithTags(tagsText, headingsHTML);
	}
}
