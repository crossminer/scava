package org.eclipse.scava.crossflow.nlp.stackexchange;

import org.eclipse.scava.crossflow.nlp.stackexchange.gen.PlainTextBase;
import org.eclipse.scava.crossflow.nlp.stackexchange.gen.Post;
import org.eclipse.scava.nlp.tools.plaintext.documentation.PlainTextDocumentationHtmlBased;


public class PlainText extends PlainTextBase {
	
	@Override
	public Post consumePlainTextQueue(Post post) throws Exception {
		post.setPlainText(PlainTextDocumentationHtmlBased.process(post.getBody()).getPlainTextAsString());
		return post;
	}
	
}
