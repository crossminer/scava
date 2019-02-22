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

public class Milestone {
	 private float id;
	 private float iid;
	 private float project_id;
	 private String title;
	 private String description;
	 private String state;
	 private String created_at;
	 private String updated_at;
	 private String due_date = null;
	 private String start_date = null;
	 private String web_url;


	 // Getter Methods 

	 public float getId() {
	  return id;
	 }

	 public float getIid() {
	  return iid;
	 }

	 public float getProject_id() {
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

	 public String getDue_date() {
	  return due_date;
	 }

	 public String getStart_date() {
	  return start_date;
	 }

	 public String getWeb_url() {
	  return web_url;
	 }

	 // Setter Methods 

	 public void setId(float id) {
	  this.id = id;
	 }

	 public void setIid(float iid) {
	  this.iid = iid;
	 }

	 public void setProject_id(float project_id) {
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

	 public void setDue_date(String due_date) {
	  this.due_date = due_date;
	 }

	 public void setStart_date(String start_date) {
	  this.start_date = start_date;
	 }

	 public void setWeb_url(String web_url) {
	  this.web_url = web_url;
	 }
}
