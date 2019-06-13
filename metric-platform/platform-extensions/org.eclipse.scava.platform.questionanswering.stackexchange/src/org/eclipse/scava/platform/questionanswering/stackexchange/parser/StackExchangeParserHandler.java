package org.eclipse.scava.platform.questionanswering.stackexchange.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

class StackExchangeParserHandler extends DefaultHandler
{
	private StackExchangePost post;
	
	private HashMap<String,Object> answersFilters;
	private HashMap<String,Calendar> creationDateIntervalA;
	private HashMap<String,Calendar> lastEditDateIntervalA;
	private HashMap<String,Calendar> lastActivityDateIntervalA;
	
	private HashMap<String,Object> questionFilters;
	private HashMap<String,Calendar> creationDateIntervalQ;
	private HashMap<String,Calendar> lastEditDateIntervalQ;
	private HashMap<String,Calendar> lastActivityDateIntervalQ;
	private HashMap<String,Calendar> communityOwnedDateIntervalQ;
	private HashMap<String,Calendar> closedDateIntervalQ;
	private List<String> tagsWanted;
	
	private List<Long> questionIds;
	
	private List<StackExchangePost> selectedPosts;
	
	private SimpleDateFormat dateFormatter  = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS");
	
	@SuppressWarnings("unchecked")
	public StackExchangeParserHandler(StackExchangeParserBuilder parserBuilder) throws ParserConfigurationException, SAXException
	{
		this.questionFilters = parserBuilder.getQuestionParsingFilters();
		this.answersFilters = parserBuilder.getAnswersParsingFilters();
		selectedPosts = new ArrayList<StackExchangePost>();
		questionIds = new ArrayList<Long>();
		//The following lines are done to make the parsing faster, as these elements are going to be in constant use
		if(answersFilters != null)
		{
			creationDateIntervalA = (HashMap<String, Calendar>) answersFilters.get("creationDate");
			lastEditDateIntervalA = (HashMap<String, Calendar>) answersFilters.get("lastEditDate");
			lastActivityDateIntervalA = (HashMap<String, Calendar>) answersFilters.get("lastActivityDate");
		}
		if(questionFilters != null)
		{
			creationDateIntervalQ = (HashMap<String, Calendar>) questionFilters.get("creationDate");
			lastEditDateIntervalQ = (HashMap<String, Calendar>) questionFilters.get("lastEditDate");
			lastActivityDateIntervalQ = (HashMap<String, Calendar>) questionFilters.get("lastActivityDate");
			communityOwnedDateIntervalQ = (HashMap<String, Calendar>) questionFilters.get("communityOwnedDate");
			closedDateIntervalQ = (HashMap<String, Calendar>) questionFilters.get("closedDate");
			tagsWanted = (List<String>) questionFilters.get("tags");
		}
		
	}
	
	public List<StackExchangePost> getPosts()
	{
		return selectedPosts;
	}
	
	
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
		if (qName.equalsIgnoreCase("row"))
		{
	          post = new StackExchangePost(attributes.getValue("Id"), attributes.getValue("PostTypeId"));
	          if(post.getPostType()==1)//Question
	          {
	        	  post.setAcceptedAnswerId(attributes.getValue("AcceptedAnswerId"));
	        	  post.setViewCount(attributes.getValue("ViewCount"));
	        	  post.setCommunityOwnedDate(stringToDate(attributes.getValue("CommunityOwnedDate")));
	        	  post.setClosedDate(stringToDate(attributes.getValue("ClosedDate")));
	        	  post.setTitle(attributes.getValue("Title"));
	        	  post.setTags(attributes.getValue("Tags"));
	        	  post.setAnswerCount(attributes.getValue("AnswerCount"));
	        	  post.setFavoriteCount(attributes.getValue("FavoriteCount"));
	          }
	          else if(post.getPostType()==2)//Answer
	          {
	        	  post.setParentId(attributes.getValue("ParentId"));
	          }
	          else
	          {
	        	  post=null;		//In some cases, the post type can be greater than 2, despite it shouldn't be the case
	          }
	          
	          if(post!=null)
	          {
	        	  post.setCreationDate(stringToDate(attributes.getValue("CreationDate")));
	        	  post.setScore(attributes.getValue("Score"));
	        	  post.setBody(attributes.getValue("Body"));
	        	  post.setOwnerUserId(attributes.getValue("OwnerUserId"));
	        	  post.setLastEditorUserId(attributes.getValue("LastEditorUserId"));
	        	  post.setLastEditorDisplayName(attributes.getValue("LastEditorDisplayName"));
	        	  post.setLastEditDate(stringToDate(attributes.getValue("LastEditDate")));
	        	  post.setLastActivityDate(stringToDate(attributes.getValue("LastActivityDate")));	        	  
	        	  post.setCommentCount(attributes.getValue("CommentCount"));
	        	  
	          }
	          
	       }
    }
	
	private Date stringToDate(String stringDate)
	{
		if(stringDate==null)
			return null;
		try {
			return dateFormatter.parse(stringDate);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		if(post!=null)
			processPost();
	}
	
	private void processPost()
	{
		//Questions
		if(post.getPostType()==1 && questionFilters != null)
		{
			if(((Boolean) questionFilters.get("acceptedAnswer")==true) && post.getAcceptedAnswerId() == null)
				return;
			if(!valuesGreaterEqual(questionFilters.get("viewCount"), post.getViewCount()))
				return;
			if(!dateComparisons(closedDateIntervalQ, post.getClosedDate()))
				return;
			if(!dateComparisons(communityOwnedDateIntervalQ, post.getClosedDate()))
				return;
			if(!dateComparisons(creationDateIntervalQ, post.getCreationDate()))
				return;
			if(!dateComparisons(lastEditDateIntervalQ, post.getLastEditDate()))
				return;
			if(!dateComparisons(lastActivityDateIntervalQ, post.getLastActivityDate()))
				return;
			if(!valuesGreaterEqual(questionFilters.get("answerCount"), post.getAnswerCount()))
				return;
			if(!valuesGreaterEqual(questionFilters.get("favoriteCount"), post.getFavoriteCount()))
				return;
			if(!tagsComparison(post.getTags()))
				return;
			if(!valuesGreaterEqual(questionFilters.get("score"), post.getScore()))
				return;
			if(!valuesGreaterEqual(questionFilters.get("commentCount"), post.getCommentCount()))
				return;
			questionIds.add(post.getId());
		}
		else if(post.getPostType()==1)
		{
			questionIds.add(post.getId());
		}
		//Answers
		else if (post.getPostType()==2)
		{
			if(!questionIds.contains(post.getParentId()))
				return;
			if(answersFilters != null)
			{
				if(!dateComparisons(creationDateIntervalA, post.getCreationDate()))
					return;
				if(!dateComparisons(lastEditDateIntervalA, post.getLastEditDate()))
					return;
				if(!dateComparisons(lastActivityDateIntervalA, post.getLastActivityDate()))
					return;
				if(!valuesGreaterEqual(answersFilters.get("score"), post.getScore()))
					return;
				if(!valuesGreaterEqual(answersFilters.get("commentCount"), post.getCommentCount()))
					return;
			}
		}
		else
		{
			return;
		}
		selectedPosts.add(post);
	}
	
	private Boolean tagsComparison(List<String> tags)
	{
		if(tagsWanted==null)
			return true;
		
		Boolean flag = false;
		for(String tag : tags)
		{
			if(tagsWanted.contains(tag))
				flag=true;
		}
	
		return flag;
	}
	
	private Boolean valuesGreaterEqual(Object filter, Integer value)
	{
		if(filter == null)
			return true;
		if((Integer) filter <= value)
			return true;
		else
			return false;
	}
	
	private Boolean valuesGreaterEqual(Object filter, Long value)
	{
		if(filter == null)
			return true;
		if((Long) filter <= value)
			return true;
		else
			return false;
	}
	
	private Boolean dateComparisons(HashMap<String,Calendar> dateInterval, Date date)
	{
		if(dateInterval == null)
			return true;
		Calendar convertedDate = Calendar.getInstance();
		convertedDate.setTime(date);
		Calendar inferior = dateInterval.get("inferior");
		Calendar superior = dateInterval.get("superior");
		if(inferior==null && superior == null)
			return true;
		Boolean flag=true;
		if(inferior != null )
			if(isBefore(inferior, convertedDate))
				flag = flag && false;
		if(superior != null)
			if(isAfter(superior, convertedDate))
				flag = flag && false;
		return flag;
	}
	
	private boolean isBefore(Calendar reference, Calendar toCompare)
	{
		return reference.get(Calendar.YEAR) >= toCompare.get(Calendar.YEAR) &&
				reference.get(Calendar.MONTH) >= toCompare.get(Calendar.MONTH) &&
				reference.get(Calendar.DAY_OF_YEAR) >= toCompare.get(Calendar.DAY_OF_YEAR);
	}
	
	private boolean isAfter(Calendar base, Calendar toCompare)
	{
		return base.get(Calendar.YEAR) <= toCompare.get(Calendar.YEAR) &&
				base.get(Calendar.MONTH) <= toCompare.get(Calendar.MONTH) &&
				base.get(Calendar.DAY_OF_YEAR) <= toCompare.get(Calendar.DAY_OF_YEAR);
	}
}
