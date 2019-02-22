/*******************************************************************************
* Copyright (c) 2019 Edge Hill University
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.gitlab.model;

import java.util.ArrayList;

public class Issue {
	
	 private String id;//
	 private String iid;
	 private String project_id;
	 private String title;
	 private String description;
	 private String state;
	 private String created_at;
	 private String updated_at;
	 private String closed_at = null;
	 private String closed_by = null;
	 ArrayList < Object > labels = new ArrayList < Object > ();
	 Milestone MilestoneObject;
	 ArrayList < Object > assignees = new ArrayList < Object > ();
	 Author AuthorObject;
	 Assignee AssigneeObject;
	 private float user_notes_count;
	 private float upvotes;
	 private float downvotes;
	 private String due_date;
	 private boolean confidential;
	 private String discussion_locked = null;
	 private String web_url;
	 Time_stats Time_statsObject;
	 private String weight = null;


	 // Getter Methods 

	 public String getId() {
	  return id;
	 }

	 public String getIid() {
	  return iid;
	 }

	 public String getProject_id() {
	  return project_id;
	 }

	 public String getTitle() {
	  return title;
	 }

	 public String getDescription() {
	  return description;
	 }

	 public String getState() {
	  return state;
	 }

	 public String getCreated_at() {
	  return created_at;
	 }

	 public String getUpdated_at() {
	  return updated_at;
	 }

	 public String getClosed_at() {
	  return closed_at;
	 }

	 public String getClosed_by() {
	  return closed_by;
	 }

	 public Milestone getMilestone() {
	  return MilestoneObject;
	 }

	 public Author getAuthor() {
	  return AuthorObject;
	 }

	 public Assignee getAssignee() {
	  return AssigneeObject;
	 }

	 public float getUser_notes_count() {
	  return user_notes_count;
	 }

	 public float getUpvotes() {
	  return upvotes;
	 }

	 public float getDownvotes() {
	  return downvotes;
	 }

	 public String getDue_date() {
	  return due_date;
	 }

	 public boolean getConfidential() {
	  return confidential;
	 }

	 public String getDiscussion_locked() {
	  return discussion_locked;
	 }

	 public String getWeb_url() {
	  return web_url;
	 }

	 public Time_stats getTime_stats() {
	  return Time_statsObject;
	 }

	 public String getWeight() {
	  return weight;
	 }

	 // Setter Methods 

	 public void setId(String id) {
	  this.id = id;
	 }

	 public void setIid(String iid) {
	  this.iid = iid;
	 }

	 public void setProject_id(String project_id) {
	  this.project_id = project_id;
	 }

	 public void setTitle(String title) {
	  this.title = title;
	 }

	 public void setDescription(String description) {
	  this.description = description;
	 }

	 public void setState(String state) {
	  this.state = state;
	 }

	 public void setCreated_at(String created_at) {
	  this.created_at = created_at;
	 }

	 public void setUpdated_at(String updated_at) {
	  this.updated_at = updated_at;
	 }

	 public void setClosed_at(String closed_at) {
	  this.closed_at = closed_at;
	 }

	 public void setClosed_by(String closed_by) {
	  this.closed_by = closed_by;
	 }

	 public void setMilestone(Milestone milestoneObject) {
	  this.MilestoneObject = milestoneObject;
	 }

	 public void setAuthor(Author authorObject) {
	  this.AuthorObject = authorObject;
	 }

	 public void setAssignee(Assignee assigneeObject) {
	  this.AssigneeObject = assigneeObject;
	 }

	 public void setUser_notes_count(float user_notes_count) {
	  this.user_notes_count = user_notes_count;
	 }

	 public void setUpvotes(float upvotes) {
	  this.upvotes = upvotes;
	 }

	 public void setDownvotes(float downvotes) {
	  this.downvotes = downvotes;
	 }

	 public void setDue_date(String due_date) {
	  this.due_date = due_date;
	 }

	 public void setConfidential(boolean confidential) {
	  this.confidential = confidential;
	 }

	 public void setDiscussion_locked(String discussion_locked) {
	  this.discussion_locked = discussion_locked;
	 }

	 public void setWeb_url(String web_url) {
	  this.web_url = web_url;
	 }

	 public void setTime_stats(Time_stats time_statsObject) {
	  this.Time_statsObject = time_statsObject;
	 }

	 public void setWeight(String weight) {
	  this.weight = weight;
	 }
}