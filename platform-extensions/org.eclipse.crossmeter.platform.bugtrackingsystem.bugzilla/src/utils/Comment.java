/*
 * Copyright 2011 Thomas Golden
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package utils;

import java.util.Date;

/**
 * The {@code Comment} class represents a comment entered for a particular
 * {@link Bug} in a Bugzilla installation. Each comment has a unique ID, and
 * is correlated with at most one {@code Bug}.
 * 
 * @author tgolden
 *
 */
public class Comment {

	/**
	 * The unique ID of this {@link Comment} on a Bugzilla installation
	 */
	private final int id;
	
	/**
	 * The message content of this {@link Comment}
	 */
	private String text;
	private Date timestamp;
	private boolean isPrivate;
	private int bugId;
	private int creatorId;
	private String creator;
	private String author;
	
	/**
	 * Creates a new {@link Comment} from a Bugzilla installation
	 * @param id The unique ID of this comment
	 * @param text The text content of this comment
	 */
	public Comment(int id) {
//		, String text, Date time) {
		this.id = id;
	}
	
	/**
	 * @return The unique ID of this {@link Comment}
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return The text content of this {@link Comment}
	 */
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public int getBugId() {
		return bugId;
	}

	public void setBugId(int bugId) {
		this.bugId = bugId;
	}

	public int getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	
	
}