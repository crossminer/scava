package org.eclipse.scava.crossflow.restmule.client.github.test.query;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

// https://help.github.com/articles/searching-repositories/
// https://help.github.com/articles/search-syntax/

public class RepositoryQuery 
implements IDateValue<IRepositoryQueryBuilder>, 	
INumericValue<Integer,IRepositoryQueryBuilder>, IRepositoryQueryBuilder//, Builder<IRepositoryQueryBuilder, Query>
{
	// TODO sorting conventions could be kept similar for a particular search class
	private String query;

	private RepositoryQuery() {
		this.query = "";
	}

	// repoQuery (q parameter)
	public static IRepositoryQueryBuilder build(){
		return (IRepositoryQueryBuilder) new RepositoryQuery();
	}
	
	//@Override
	public static IRepositoryQueryBuilder create() {
		return (IRepositoryQueryBuilder) new RepositoryQuery();
	}

/*	@Override
	public Query build() {
		return (Query) this;
	}*/
	
	@Override
	public String getQuery() {
		//String encode = URLEncoder.encode(query,"ASCII");
		String encode = query;
		return encode.substring(0, encode.length()-1);
	}

	public static void main(String[] args) {
		IRepositoryQueryBuilder query = RepositoryQuery.build()
				.keywords("word", "a phrase")
				.forkOnly()
				.forkTrue()
				.size().eq(2)
				.created().at(new Date());
		System.out.println(query.getQuery());
	}
	
	@Override
	public IRepositoryQueryBuilder keywords(String... keywords){
		Iterator<String> iterator = Arrays.asList(keywords).iterator();
		String query = ""; 
		while(iterator.hasNext()){
			String keyword = iterator.next(); // TODO Validate ENCODING
			if(keyword.contains(" ")){
				keyword = "\""+keyword+"\"";
			}
			query+= keyword + " ";
		}
		this.query = query; 
		return (IRepositoryQueryBuilder) this;
	}
	
	@Override
	public IRepositoryQueryBuilder forkTrue() {
		this.query += KEYWORD_FORK + Boolean.TRUE + " ";
		return (IRepositoryQueryBuilder) this;
	}

	@Override
	public IRepositoryQueryBuilder forkOnly() {
		this.query += KEYWORD_FORK + "only ";
		return (IRepositoryQueryBuilder) this;
	}
	
	@Override
	public IDateValue<IRepositoryQueryBuilder> created(){
		this.query += KEYWORD_CREATED;
		return (IDateValue<IRepositoryQueryBuilder>) this;		
	}
	
	@Override
	public IDateValue<IRepositoryQueryBuilder> pushed(){
		this.query += KEYWORD_UPDATED;
		return (IDateValue<IRepositoryQueryBuilder>) this;		
	}
	
	@Override
	public INumericValue<Integer, IRepositoryQueryBuilder> size(){
		this.query += KEYWORD_SIZE;
		return (INumericValue<Integer, IRepositoryQueryBuilder>) this;		
	}

	@Override
	public IRepositoryQueryBuilder between(Integer start, Integer end) {
		if (end > start){
			this.query += String.valueOf(start)+INumericValue.BETWEEN+String.valueOf(end) + " ";
		}
		return this;
	}

	@Override
	public IRepositoryQueryBuilder gt(Integer val) {
		this.query += INumericValue.GT + String.valueOf(val) + " ";
		return this;
	}

	@Override
	public IRepositoryQueryBuilder geq(Integer val) {
		this.query += INumericValue.GEQ + String.valueOf(val) + " ";
		return this;
	}

	@Override
	public IRepositoryQueryBuilder eq(Integer val) {
		this.query += INumericValue.EQ + String.valueOf(val) + " ";
		return this;
	}

	@Override
	public IRepositoryQueryBuilder leq(Integer val) {
		this.query += INumericValue.LEQ + String.valueOf(val) + " ";
		return this;
	}

	@Override
	public IRepositoryQueryBuilder lt(Integer val) {
		this.query += INumericValue.LT + String.valueOf(val) + " ";
		return this;
	}

	@Override
	public IRepositoryQueryBuilder between(Date start, Date end) {
		if (end.after(start)){
			this.query += String.valueOf(start)+INumericValue.BETWEEN+String.valueOf(end) + " ";
		}
		return this;
	}

	@Override
	public IRepositoryQueryBuilder from(Date start) {
		this.query += INumericValue.GT + IDateValue.ISO_8601_FORMAT.format(start) + " ";
		return this;
	}

	@Override
	public IRepositoryQueryBuilder before(Date end) {
		this.query += INumericValue.LT + IDateValue.ISO_8601_FORMAT.format(end) + " ";
		return this;
	}

	@Override
	public IRepositoryQueryBuilder at(Date date) {
		this.query += INumericValue.EQ + IDateValue.ISO_8601_FORMAT.format(date) + " ";
		return this;
	}

	

}
/*
class RepositorySortBuilder{
	private static final String ORDER_ASC = "order=asc";
	private static final String ORDER_DES = "order=desc";

	private static final String SORT_STARS = "sort=stars";
	private static final String SORT_FORKS = "sort=forks";
	private static final String SORT_UPDATED = "sort=updated";

}*/
