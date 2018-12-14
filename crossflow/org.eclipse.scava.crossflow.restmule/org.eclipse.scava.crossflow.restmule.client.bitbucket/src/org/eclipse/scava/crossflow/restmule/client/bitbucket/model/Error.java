package org.eclipse.scava.crossflow.restmule.client.bitbucket.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Error {

	public Error(){}

	@JsonProperty("type") 
	private String type;
	
	@JsonProperty("error") 
	private ErrorInner errorInner;
	
	public String getType() {
		return this.type;
	}
	
	public ErrorInner getErrorInner() {
		return this.errorInner;
	}
	
	@Override
	public String toString() {
		return "Error [ "
			+ "type = " + this.type + ", "
			+ "errorInner = " + this.errorInner + ", "
			+ "]"; 
	}	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class ErrorInner {
	
		public ErrorInner(){}
	
		@JsonProperty("detail") 
		private String detail;
		
		@JsonProperty("message") 
		private String message;
		
		@JsonProperty("data") 
		private Data data;
		
		public String getDetail() {
			return this.detail;
		}
		
		public String getMessage() {
			return this.message;
		}
		
		public Data getData() {
			return this.data;
		}
		
		@Override
		public String toString() {
			return "ErrorInner [ "
				+ "detail = " + this.detail + ", "
				+ "message = " + this.message + ", "
				+ "data = " + this.data + ", "
				+ "]"; 
		}	
		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class Data {
		
			public Data(){}
		
			@Override
			public String toString() {
				return "Data [ "
					+ "]"; 
			}	
		}
		
	}
	
}	
