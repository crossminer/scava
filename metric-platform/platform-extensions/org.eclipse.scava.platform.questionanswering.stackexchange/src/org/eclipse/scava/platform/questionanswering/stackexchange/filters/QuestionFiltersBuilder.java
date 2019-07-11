package org.eclipse.scava.platform.questionanswering.stackexchange.filters;

import java.util.HashMap;
import java.util.List;

public class QuestionFiltersBuilder
{
	HashMap<String,Object> parsingFilters;
	
	private QuestionFiltersBuilder()
	{
		parsingFilters = new HashMap<String, Object>();
		//Default values
		parsingFilters.put("lastEditDateInterval", FiltersUtils.dateRangerCreator("", ""));
		parsingFilters.put("creationDateRange", FiltersUtils.dateRangerCreator("", ""));
		parsingFilters.put("score", null);
		parsingFilters.put("acceptedAnswer", false);
		parsingFilters.put("commentCount", null);
		parsingFilters.put("viewCount", null);
		parsingFilters.put("closedDateRange", FiltersUtils.dateRangerCreator("", ""));
		parsingFilters.put("communityOwnedDateRange", FiltersUtils.dateRangerCreator("", ""));
		parsingFilters.put("answerCount", null);
		parsingFilters.put("favoriteCount", null);
		parsingFilters.put("tags", null);
		parsingFilters.put("lastActivityDateRange", FiltersUtils.dateRangerCreator("", ""));

	}
	
	public HashMap<String,Object> getParsingFilters()
	{
		return parsingFilters;
	}
	
	public static IAddQuestionOptionsMandatory addFilters()
	{
		return new QuestionFiltersBuilder.BuilderQuestions();
	}
	
	public static IAddAllQuestions allQuestions()
	{
		return new QuestionFiltersBuilder.BuilderQuestions();
	}
	
	public interface IAddAllQuestions
	{
		QuestionFiltersBuilder build();
	}
	
	public interface IAddQuestionOptions
	{
		IAddQuestionOptions acceptedAnswerPresent();
		IAddQuestionOptions creationDateRange(String inferior, String superior);
		IAddQuestionOptions lastEditDateRange(String inferior, String superior);
		IAddQuestionOptions score(Integer minValue);
		IAddQuestionOptions lastActivityDateRange(String inferior, String superior);
		IAddQuestionOptions commentCount(Integer minValue);
		IAddQuestionOptions viewCount(Long minValue);
		IAddQuestionOptions closedDateRange(String inferior, String superior);
		IAddQuestionOptions communityOwnedDateRange(String inferior, String superior);
		IAddQuestionOptions answerCount(Integer minValue);
		IAddQuestionOptions favoriteCount(Integer minValue);
		IAddQuestionOptions tags(List<String> tags);
		QuestionFiltersBuilder build();
	}
	
	public interface IAddQuestionOptionsMandatory
	{
		IAddQuestionOptions acceptedAnswerPresent();
		IAddQuestionOptions creationDateRange(String inferior, String superior);
		IAddQuestionOptions lastEditDateRange(String inferior, String superior);
		IAddQuestionOptions score(Integer minValue);
		IAddQuestionOptions lastActivityDateRange(String inferior, String superior);
		IAddQuestionOptions commentCount(Integer minValue);
		IAddQuestionOptions viewCount(Long minValue);
		IAddQuestionOptions closedDateRange(String inferior, String superior);
		IAddQuestionOptions communityOwnedDateRange(String inferior, String superior);
		IAddQuestionOptions answerCount(Integer minValue);
		IAddQuestionOptions favoriteCount(Integer minValue);
		IAddQuestionOptions tags(List<String> tags);
	}
	
	private static class BuilderQuestions implements IAddQuestionOptionsMandatory, IAddQuestionOptions,IAddAllQuestions
	{
		QuestionFiltersBuilder instance = new QuestionFiltersBuilder();
		
		@Override
		public IAddQuestionOptions lastEditDateRange(String inferior, String superior) {
			instance.parsingFilters.put("lastEditDateInterval", FiltersUtils.dateRangerCreator(inferior, superior));
			return this;
		}

		@Override
		public IAddQuestionOptions creationDateRange(String inferior, String superior) {
			instance.parsingFilters.put("creationDateRange", FiltersUtils.dateRangerCreator(inferior, superior));
			return this;
		}

		@Override
		public IAddQuestionOptions score(Integer minValue) {
			instance.parsingFilters.put("score", minValue);
			return this;
		}

		@Override
		public IAddQuestionOptions commentCount(Integer minValue) {
			instance.parsingFilters.put("commentCount", Math.abs(minValue));
			return this;
		}

		@Override
		public IAddQuestionOptions viewCount(Long minValue) {
			instance.parsingFilters.put("viewCount", Math.abs(minValue));
			return this;
		}

		@Override
		public IAddQuestionOptions closedDateRange(String inferior, String superior) {
			instance.parsingFilters.put("closedDateRange", FiltersUtils.dateRangerCreator(inferior, superior));
			return this;
		}

		@Override
		public IAddQuestionOptions communityOwnedDateRange(String inferior, String superior) {
			instance.parsingFilters.put("communityOwnedDateRange", FiltersUtils.dateRangerCreator(inferior, superior));
			return this;
		}

		@Override
		public IAddQuestionOptions answerCount(Integer minValue) {
			instance.parsingFilters.put("answerCount", Math.abs(minValue));
			return this;
		}

		@Override
		public IAddQuestionOptions favoriteCount(Integer minValue) {
			instance.parsingFilters.put("favoriteCount", Math.abs(minValue));
			return this;
		}

		@Override
		public IAddQuestionOptions tags(List<String> tags) {
			instance.parsingFilters.put("tags", tags);
			return this;
		}
		
		@Override
		public IAddQuestionOptions lastActivityDateRange(String inferior, String superior) {
			instance.parsingFilters.put("lastActivityDateRange", FiltersUtils.dateRangerCreator(inferior, superior));
			return this;
		}

		@Override
		public QuestionFiltersBuilder build() {
			return instance;
		}

		@Override
		public IAddQuestionOptions acceptedAnswerPresent() {
			instance.parsingFilters.put("acceptedAnswer", true);
			return this;
		}

	}
}
