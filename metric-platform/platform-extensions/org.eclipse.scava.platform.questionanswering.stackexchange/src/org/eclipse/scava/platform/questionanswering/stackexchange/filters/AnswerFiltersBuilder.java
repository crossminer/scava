package org.eclipse.scava.platform.questionanswering.stackexchange.filters;

import java.util.HashMap;

public class AnswerFiltersBuilder
{
	HashMap<String,Object> parsingFilters;
	
	private AnswerFiltersBuilder()
	{
		parsingFilters = new HashMap<String, Object>();
		//Default values
		parsingFilters.put("lastEditDateInterval", FiltersUtils.dateRangerCreator("", ""));
		parsingFilters.put("creationDateRange", FiltersUtils.dateRangerCreator("", ""));
		parsingFilters.put("score", null);
		parsingFilters.put("commentCount", 0);
		parsingFilters.put("lastActivityDateRange", FiltersUtils.dateRangerCreator("", ""));
	}
	
	public HashMap<String,Object> getParsingFilters()
	{
		return parsingFilters;
	}
	
	public static IAddAnswersOptionsMandatory addFilters()
	{
		return new AnswerFiltersBuilder.BuilderAnswers();
	}
	
	public static IAddAllAnswers allAnswers()
	{
		return new AnswerFiltersBuilder.BuilderAnswers();
	}
	
	public interface IAddAllAnswers
	{
		AnswerFiltersBuilder build();
	}
	
	public interface IAddAnswersOptionsMandatory
	{
		IAddAnswersOptions creationDateRange(String inferior, String superior);
		IAddAnswersOptions score(Integer minValue);
		IAddAnswersOptions lastEditDateRange(String inferior, String superior);
		IAddAnswersOptions lastActivityDateRange(String inferior, String superior);
		IAddAnswersOptions commentCount(Integer minValue);
	}
	
	public interface IAddAnswersOptions
	{
		IAddAnswersOptions creationDateRange(String inferior, String superior);
		IAddAnswersOptions score(Integer minValue);
		IAddAnswersOptions lastEditDateRange(String inferior, String superior);
		IAddAnswersOptions lastActivityDateRange(String inferior, String superior);
		IAddAnswersOptions commentCount(Integer minValue);
		AnswerFiltersBuilder build();
	}
	
	private static class BuilderAnswers implements IAddAnswersOptionsMandatory, IAddAnswersOptions, IAddAllAnswers
	{
		AnswerFiltersBuilder instance = new AnswerFiltersBuilder();

		@Override
		public IAddAnswersOptions creationDateRange(String inferior, String superior) {
			instance.parsingFilters.put("creationDateRange", FiltersUtils.dateRangerCreator(inferior, superior));
			return this;
		}

		@Override
		public IAddAnswersOptions score(Integer minValue) {
			instance.parsingFilters.put("score", minValue);
			return this;
		}

		@Override
		public IAddAnswersOptions lastEditDateRange(String inferior, String superior) {
			instance.parsingFilters.put("lastEditDateRange", FiltersUtils.dateRangerCreator(inferior, superior));
			return this;
		}

		@Override
		public IAddAnswersOptions lastActivityDateRange(String inferior, String superior) {
			instance.parsingFilters.put("lastActivityDateRange", FiltersUtils.dateRangerCreator(inferior, superior));
			return this;
		}

		@Override
		public IAddAnswersOptions commentCount(Integer minValue) {
			instance.parsingFilters.put("commentCount", Math.abs(minValue));
			return this;
		}

		@Override
		public AnswerFiltersBuilder build() {
			return instance;
		}
	
	}

}
