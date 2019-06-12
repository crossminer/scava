package org.eclipse.scava.nlp.classifiers.documentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.scava.nlp.tools.predictions.multilabel.MultiLabelPrediction;

public class DocumentationClassifier {

	private static HashMap<String, List<Pattern>> classesRegex;
	private static Set<String> classesNames;
	
	static
	{
		classesRegex = DocumentationClassifierSingleton.getInstance().getClassesRegex();
		classesNames = classesRegex.keySet();
	}
	
	private static String findDocumentationClass(String heading)
	{
		heading=heading.toLowerCase(Locale.ENGLISH);
		Matcher m;
		for(String className : classesNames)
		{
			for(Pattern pattern : classesRegex.get(className))
			{
				m = pattern.matcher(heading); 
				if(m.find())
					return className;
			}
		}
		return "";
	}
	
	/**
	 * 
	 * @param documentation The text in the MultLabelPredict is expected to be in HTML format, as it is
	 * the only way to determine the headings of a file.
	 * @param fromPDF
	 * @return
	 */
	public static MultiLabelPrediction classify(MultiLabelPrediction documentation, boolean fromPDF)
	{
		List<String> headings = HtmlHeadingsFinder.find(documentation.getText(), fromPDF);
		Set<String> documentationClasses = Collections.synchronizedSet(new HashSet<String>(headings.size()));
		headings.parallelStream().forEach(h->documentationClasses.add(findDocumentationClass(h))); 
		documentationClasses.remove("");
		documentation.setLabels(new ArrayList<String>(documentationClasses));
		return documentation;
	}
	
	
}
