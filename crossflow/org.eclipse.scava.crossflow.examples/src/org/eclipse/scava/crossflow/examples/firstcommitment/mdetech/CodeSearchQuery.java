package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

import java.util.Arrays;
import java.util.List;

import io.reactivex.annotations.NonNull;

public class CodeSearchQuery {

	private String query;
	
	public CodeSearchQuery(){}

	CodeSearchQuery(String query){
		this.query = query;
	}
	
	public String getQuery(){
		return this.query;
	}
	
	public Builder create(@NonNull String keyword){
		return new Builder(keyword);
	}
	
	public class Builder {
		
		private String keyword;
		private String userOrRepo;	
		private String in;	
		private String filename;	
		private String path;	
		private String extension;
		private String language;
		
		Builder(@NonNull String keyword){
			this.keyword = keyword;
			this.userOrRepo = "";
			this.in = "";
			this.filename = "";	
			this.path = "";	
			this.extension = "";
			this.language = "";
		}
		
		public Builder user(@NonNull String user){
			this.userOrRepo = "user:" + user;
			return this;
		}
		
		public Builder repo(@NonNull String repo){
			this.userOrRepo = "repo:" + repo;
			return this;
		}
		
		public Builder extension(@NonNull String extension){
			this.extension = "extension:" + extension;
			return this;
		}
		
		public Builder filename(@NonNull String filename){
			this.filename = "filename:" + filename;
			return this;
		}
		
		public Builder path(@NonNull String path){
			this.path = "path:" + path;
			return this;
		}
		public Builder inFile(){
			this.in = "in:file";
			return this;
		}
		public Builder inPath(){
			this.in = "in:path";
			return this;
		}
		public Builder inPathAndFile(){
			this.in = "in:path&file";
			return this;
		}
		
		public Builder language(@NonNull String language){
			this.language = "language:" + language + " ";
			return this;
		}
		
		public CodeSearchQuery build(){
			List<String> list = Arrays.asList(extension, userOrRepo, in, filename, path, language);
			String filters = list.stream().filter(a -> !a.isEmpty()).map(a-> a.concat("+")).reduce("", (a,b) -> a+b );
			filters = filters.substring(0, filters.length()-1);
			return new CodeSearchQuery(keyword + "+" + filters);
		}
	}
	
	
}