package org.eclipse.scava.crossflow.nlp.stackexchange;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.scava.crossflow.nlp.stackexchange.gen.Post;
import org.eclipse.scava.crossflow.nlp.stackexchange.gen.StackExchangeReaderBase;
import org.eclipse.scava.platform.questionanswering.stackexchange.filters.AnswerFiltersBuilder;
import org.eclipse.scava.platform.questionanswering.stackexchange.filters.QuestionFiltersBuilder;
import org.eclipse.scava.platform.questionanswering.stackexchange.parser.StackExchangeParser;
import org.eclipse.scava.platform.questionanswering.stackexchange.parser.StackExchangeParserBuilder;
import org.eclipse.scava.platform.questionanswering.stackexchange.parser.StackExchangePost;
import org.xml.sax.SAXException;

public class SourceStackExchangeReader extends StackExchangeReaderBase {
	
	
	static String dumpDirectory = "test/stackoverflowSampleData.xml";
	
	@Override
	public void produce() {

				StackExchangeParser parser;
				
				QuestionFiltersBuilder questions = QuestionFiltersBuilder.addFilters().acceptedAnswerPresent().build();
				AnswerFiltersBuilder answers = AnswerFiltersBuilder.addFilters().score(5).build();

				StackExchangeParserBuilder parserBuilder = StackExchangeParserBuilder
						.dumpFile(dumpDirectory).addPostTypes()
						.questionFilters(questions).answerFilters(AnswerFiltersBuilder.allAnswers().build()).build();

				try {
					
					System.out.println("im getting posts");
					parser = new StackExchangeParser(parserBuilder);
					
					// Run Parser and iterate over split output...
					for (StackExchangePost sop : parser.getPosts()) {
						//convert to Post
						Post post = convertToPost(sop);
						//add to queue
						sendToPlainTextQueue(post);

					}
					
				} catch (ParserConfigurationException | SAXException | IOException e) {
				
					e.printStackTrace();
				}
			}


	/**
	 * Converts StackExchangePost object into a Post object
	 * 
	 * @param sop
	 * @return
	 */
	private static Post convertToPost(StackExchangePost sop) {
		Post post = new Post();
		post.setPostId(sop.getId());
		post.setPostType(sop.getPostType());
		post.setParentId(sop.getParentId() );
		post.setAcceptedAnswerId(sop.getAcceptedAnswerId() );
		post.setCreationDate(sop.getCreationDate());
		post.setScore(sop.getScore());
		post.setViewCount(sop.getViewCount() );
		post.setBody(sop.getBody());
		post.setOwnerUserId(sop.getOwnerUserId());
		post.setLastEditorUserId(sop.getLastEditorUserId());
		post.setLastEditorDisplayName(sop.getLastEditorDisplayName());
		post.setLastEditDate(sop.getLastEditDate());
		post.setLastActivityDate(sop.getLastActivityDate());
		post.setCommunityOwnedDate(sop.getCommunityOwnedDate());
		post.setClosedDate(sop.getClosedDate());
		post.setTitle(sop.getTitle() );
		post.setTags(sop.getTags());
		post.setAnswerCount(sop.getAnswerCount());
		post.setCommentCount(sop.getCommentCount());
		post.setFavoriteCount(sop.getFavoriteCount());
		return post;
	}
}
