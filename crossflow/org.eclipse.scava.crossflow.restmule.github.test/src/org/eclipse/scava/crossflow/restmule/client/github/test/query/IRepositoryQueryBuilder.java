package org.eclipse.scava.crossflow.restmule.client.github.test.query;

public interface IRepositoryQueryBuilder {

	static final String KEYWORD_SEARCH_FIELDS = "in:";
	static final String KEYWORD_SIZE = "size:";
	static final String KEYWORD_FORKS = "forks:";
	static final String KEYWORD_FORK = "fork:";
	static final String KEYWORD_CREATED = "created:";
	static final String KEYWORD_UPDATED = "pushed:";
	static final String KEYWORD_USER = "user:";
	static final String KEYWORD_TOPIC = "topic:";
	static final String KEYWORD_TOPICS = "topics:";
	static final String KEYWORD_LANGUAGE = "language:";
	static final String KEYWORD_STARS = "stars";
	
	IRepositoryQueryBuilder keywords(String... keywords);
	IRepositoryQueryBuilder forkTrue();
	IRepositoryQueryBuilder forkOnly();
	IDateValue<IRepositoryQueryBuilder> created();
	IDateValue<IRepositoryQueryBuilder> pushed();
	INumericValue<Integer, IRepositoryQueryBuilder> size();
	String getQuery();
}
