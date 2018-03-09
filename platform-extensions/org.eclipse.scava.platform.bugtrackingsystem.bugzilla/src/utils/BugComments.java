/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugzillaMethod;

/**
 * This class allows clients to request a list of all public {@link Comment Comments} made on a 
 * specific {@link Bug} in a Bugzilla installation. The {@link Bug} must already
 * exist in the installation. 
 * 
 * @author Tom
 *
 */
public class BugComments implements BugzillaMethod {

	/**
	 * The XML-RPC method Bugzilla will use
	 */
	private static final String METHOD_NAME = "Bug.comments";
	
	/**
	 * A {@code Map} of parameter objects required by the XML-RPC method call.
	 */
	private final Map<Object, Object> params = new HashMap<Object, Object>();
	
	/**
	 * A {@code Map} of objects returned by the XML-RPC method call.
	 */
	private Map<Object, Object> hash = new HashMap<Object, Object>();
	
	/**
	 * The ID of the {@link Bug}
	 */
//	private final int id;
	private final Date date;
	private final List<Integer> bugIdList;
	
	/**
	 * Creates a new {@link BugComments} object for the specified
	 * {@link Bug}
	 * 
	 * @param bug A {@link Bug} to retrieve comments for
	 */
	public BugComments(Bug bug) {
		bugIdList = new ArrayList<Integer>();
		bugIdList.add(bug.getID());
		params.put("ids", bugIdList);
		date = null;
	}
	
	/**
	 * Creates a new {@link BugComments} object for the specified
	 * {@link Bug} ID.
	 * @param bugId An integer specifying which {@link Bug} to retrieve
	 * comments for
	 */
	public BugComments(int bugId) {
		bugIdList = new ArrayList<Integer>();
		bugIdList.add(bugId);
		params.put("ids", bugIdList);
		date = null;
	}

	public BugComments(Comment comment) {
		bugIdList = null;
		params.put("comment_ids", comment.getId());
		date = null;
	}

	public BugComments(int bugId, Date date) {
		bugIdList = new ArrayList<Integer>();
		bugIdList.add(bugId);
		params.put("ids", bugIdList);
		this.date = date;
		params.put("new_since", date);
	}
	
	public BugComments(List<Integer> bugIdList) { // int[] idArray) {
		this.bugIdList = bugIdList;
		params.put("ids", bugIdList);
		date = null;
	}

	public BugComments(List<Integer> bugIdList, Date date) {
		this.bugIdList = bugIdList;
		params.put("ids", bugIdList);
		this.date = date;
		params.put("new_since", date);
	}
	
	/**
	 * Returns a <code>List</code> of all public comments made on the
	 * {@link Bug} requested from the installation
	 * 
	 * @return A List of {@link Comment} objects representing user comments
	 */
	public List<Comment> getComments() {
		List<Comment> commentList = new ArrayList<Comment>();
		
		if(hash.containsKey("bugs")) {
			/*
			 * Hideous, but it's the structure of the XML that
			 * Bugzilla returns. Since it's designed to return comments for
			 * multiple bugs at a time, there's extra nesting we don't need.
			 * TODO Allow requests for lists of Bugs
			 */
			@SuppressWarnings("unchecked")
			Map<String, Map<String, Map<Object, Object>[]>> m = 
				(Map<String, Map<String, Map<Object, Object>[]>>)hash.get("bugs");

			for (int id: bugIdList) {
				Map<String, Map<Object, Object>[]> weird = m.get(String.valueOf(id));
				Object[] comments = weird.get("comments");
				
				if(comments.length == 0) { return commentList; } //Early return to prevent ClassCast
				
				for(Object o : comments) {
					@SuppressWarnings("unchecked")
					Map<Object, Object> comment = (Map<Object, Object>)o;
//					for (Entry<Object, Object> i:comment.entrySet())
//						System.out.println("comment object:\t"+i.getKey()+"\t"+i.getValue());
					Comment c = new Comment((Integer)comment.get("id"));
					c.setText((String)comment.get("text"));
					c.setTimestamp((Date)comment.get("time"));
					c.setPrivate((Boolean)comment.get("is_private"));
					c.setBugId((Integer)comment.get("bug_id"));
					//c.setCreatorId((Integer)comment.get("creator_id"));
					c.setCreator((String)comment.get("creator"));
					c.setAuthor((String)comment.get("author"));
					commentList.add(c);
				}
			}
			
		}
		
		return commentList;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setResultMap(Map<Object, Object> hash) {
		this.hash = hash;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Object, Object> getParameterMap() {
		return Collections.unmodifiableMap(params);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getMethodName() {
		return METHOD_NAME;
	}

}
