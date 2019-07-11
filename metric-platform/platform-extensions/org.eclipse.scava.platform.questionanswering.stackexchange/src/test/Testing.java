package test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.scava.platform.questionanswering.stackexchange.filters.AnswerFiltersBuilder;
import org.eclipse.scava.platform.questionanswering.stackexchange.filters.QuestionFiltersBuilder;
import org.eclipse.scava.platform.questionanswering.stackexchange.parser.StackExchangeParser;
import org.eclipse.scava.platform.questionanswering.stackexchange.parser.StackExchangeParserBuilder;
import org.eclipse.scava.platform.questionanswering.stackexchange.parser.StackExchangePost;
import org.xml.sax.SAXException;

public class Testing {

	public static void main(String[] args)
	{
		QuestionFiltersBuilder questions = QuestionFiltersBuilder.addFilters().acceptedAnswerPresent().build();
		
		AnswerFiltersBuilder answers = AnswerFiltersBuilder.addFilters().score(5).build();
		
		StackExchangeParserBuilder parserBuilder = StackExchangeParserBuilder.dumpFile("D:/StackOverflow_Dump/StackOverflow/SplitXML/Folder_0/0_part_00.xml")
														.addPostTypes()
															.questionFilters(questions)
															.answerFilters(AnswerFiltersBuilder.
																				allAnswers()
																				.build())
														.build();
		
		StackExchangeParser parser;
		try {
			parser = new StackExchangeParser(parserBuilder);
			List<StackExchangePost> posts = parser.getPosts();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		System.out.println("End");
	}

}
