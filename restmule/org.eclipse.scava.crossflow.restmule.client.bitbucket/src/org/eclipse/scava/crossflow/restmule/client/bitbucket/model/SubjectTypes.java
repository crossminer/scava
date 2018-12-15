package org.eclipse.scava.crossflow.restmule.client.bitbucket.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SubjectTypes {

	public SubjectTypes(){}

	@JsonProperty("team") 
	private Team team;
	
	@JsonProperty("repository") 
	private Repository repository;
	
	@JsonProperty("user") 
	private User user;
	
	public Team getTeam() {
		return this.team;
	}
	
	public Repository getRepository() {
		return this.repository;
	}
	
	public User getUser() {
		return this.user;
	}
	
	@Override
	public String toString() {
		return "SubjectTypes [ "
			+ "team = " + this.team + ", "
			+ "repository = " + this.repository + ", "
			+ "user = " + this.user + ", "
			+ "]"; 
	}	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Team {
	
		public Team(){}
	
		@JsonProperty("events") 
		private EventsInnerInner eventsInnerInner;
		
		public EventsInnerInner getEventsInnerInner() {
			return this.eventsInnerInner;
		}
		
		@Override
		public String toString() {
			return "Team [ "
				+ "eventsInnerInner = " + this.eventsInnerInner + ", "
				+ "]"; 
		}	
		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class EventsInnerInner {
		
			public EventsInnerInner(){}
		
			@JsonProperty("href") 
			private String href;
			
			public String getHref() {
				return this.href;
			}
			
			@Override
			public String toString() {
				return "EventsInnerInner [ "
					+ "href = " + this.href + ", "
					+ "]"; 
			}	
		}
		
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Repository {
	
		public Repository(){}
	
		@JsonProperty("events") 
		private EventsInner eventsInner;
		
		public EventsInner getEventsInner() {
			return this.eventsInner;
		}
		
		@Override
		public String toString() {
			return "Repository [ "
				+ "eventsInner = " + this.eventsInner + ", "
				+ "]"; 
		}	
		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class EventsInner {
		
			public EventsInner(){}
		
			@JsonProperty("href") 
			private String href;
			
			public String getHref() {
				return this.href;
			}
			
			@Override
			public String toString() {
				return "EventsInner [ "
					+ "href = " + this.href + ", "
					+ "]"; 
			}	
		}
		
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class User {
	
		public User(){}
	
		@JsonProperty("events") 
		private Events events;
		
		public Events getEvents() {
			return this.events;
		}
		
		@Override
		public String toString() {
			return "User [ "
				+ "events = " + this.events + ", "
				+ "]"; 
		}	
		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class Events {
		
			public Events(){}
		
			@JsonProperty("href") 
			private String href;
			
			public String getHref() {
				return this.href;
			}
			
			@Override
			public String toString() {
				return "Events [ "
					+ "href = " + this.href + ", "
					+ "]"; 
			}	
		}
		
	}
	
}	
