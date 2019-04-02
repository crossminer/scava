/*******************************************************************************
 * Copyright (c) 2018 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.tools.preprocessor.markdown;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;


public class MarkdownParser
{
	private static Parser parser;
	private static HtmlRenderer renderer;
	private static Pattern newline;
	
	static
	{
		List<Extension> extensions = Arrays.asList(TablesExtension.create(),StrikethroughExtension.create());
		parser=Parser.builder()
				.extensions(extensions)
				.build();
		renderer=HtmlRenderer.builder()
				.extensions(extensions)
				.build();
		newline=Pattern.compile("\\v+");
	}
	
	public static String parse(String text)
	{
		/*We need to duplicate the number of new lines as
		newlines by default are softlines and disappear
		in the Markdown parser.
		*/
		text=newline.matcher(text).replaceAll("\n\n");
		Node document = parser.parse(text);
		text=renderer.render(document);
		//We need to delete the extra newlines again before returning the text
		text=newline.matcher(text).replaceAll("");
		return text;
	}

}
