package org.eclipse.scava.platform.questionanswering.stackexchange.parser;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.eclipse.scava.platform.questionanswering.stackexchange.filters.AnswerFiltersBuilder;
import org.eclipse.scava.platform.questionanswering.stackexchange.filters.QuestionFiltersBuilder;

public class StackExchangeParserBuilder
{
	private HashMap<String,Object> questionParsingFilters = null;
	private HashMap<String,Object> answersParsingFilters = null;
	private Path xmlPath;
	
	public Path getXMLPath()
	{
		return xmlPath;
	}
	
	public HashMap<String, Object> getQuestionParsingFilters() {
		return questionParsingFilters;
	}

	public HashMap<String, Object> getAnswersParsingFilters() {
		return answersParsingFilters;
	}

	public static ICreateParser dumpFile(String xmlPath)
	{
		return new StackExchangeParserBuilder.Builder(xmlPath);
	}
	
	public interface ICreateParser
	{
		IAddQuestions addPostTypes();
		IAllPosts allPosts();
	}
	
	public interface IAllPosts
	{
		StackExchangeParserBuilder build();
	}
	
	public interface IAddQuestions
	{
		IAddAnswers questionFilters(QuestionFiltersBuilder questionFilters);
		
	}
	
	public interface IAddAnswers
	{
		IAddPostTypes answerFilters(AnswerFiltersBuilder answersFilters);
	}
	
	public interface IAddPostTypes
	{
		StackExchangeParserBuilder build();
	}
	
	private static class Builder implements IAddPostTypes, ICreateParser, IAddQuestions, IAddAnswers, IAllPosts
	{

		private final StackExchangeParserBuilder instance = new StackExchangeParserBuilder();
		
		public Builder(String xmlPath)
		{
			instance.xmlPath = Paths.get(xmlPath);
		}
		
		@Override
		public IAddAnswers questionFilters(QuestionFiltersBuilder questionFilters) {
			instance.questionParsingFilters = questionFilters.getParsingFilters();
			return this;
		}

		@Override
		public IAddPostTypes answerFilters(AnswerFiltersBuilder answersFilters) {
			instance.answersParsingFilters = answersFilters.getParsingFilters();
			return this;
		}
		
		@Override
		public IAddQuestions addPostTypes() {
			return this;
		}

		@Override
		public StackExchangeParserBuilder build() {
			return instance;
		}

		@Override
		public IAllPosts allPosts() {
			instance.questionParsingFilters = QuestionFiltersBuilder.allQuestions().build().getParsingFilters();
			instance.answersParsingFilters = AnswerFiltersBuilder.allAnswers().build().getParsingFilters(); 
			return this;
		}

		
		
	}
}
