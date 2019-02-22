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

public class Comment  {

	private String id;
	 private String type = null;
	 private String body;
	 private String attachment = null;
	 Author AuthorObject;
	 private String created_at;
	 private String updated_at;
	 private boolean system;
	 private float noteable_id;
	 private String noteable_type;
	 private boolean resolvable;
	 private float noteable_iid;


	 // Getter Methods 

	 public String getId() {
	  return id;
	 }

	 public String getType() {
	  return type;
	 }

	 public String getBody() {
	  return body;
	 }

	 public String getAttachment() {
	  return attachment;
	 }

	 public Author getAuthor() {
	  return AuthorObject;
	 }

	 public String getCreated_at() {
	  return created_at;
	 }

	 public String getUpdated_at() {
	  return updated_at;
	 }

	 public boolean getSystem() {
	  return system;
	 }

	 public float getNoteable_id() {
	  return noteable_id;
	 }

	 public String getNoteable_type() {
	  return noteable_type;
	 }

	 public boolean getResolvable() {
	  return resolvable;
	 }

	 public float getNoteable_iid() {
	  return noteable_iid;
	 }

	 // Setter Methods 

	 public void setId(String id) {
	  this.id = id;
	 }

	 public void setType(String type) {
	  this.type = type;
	 }

	 public void setBody(String body) {
	  this.body = body;
	 }

	 public void setAttachment(String attachment) {
	  this.attachment = attachment;
	 }

	 public void setAuthor(Author authorObject) {
	  this.AuthorObject = authorObject;
	 }

	 public void setCreated_at(String created_at) {
	  this.created_at = created_at;
	 }

	 public void setUpdated_at(String updated_at) {
	  this.updated_at = updated_at;
	 }

	 public void setSystem(boolean system) {
	  this.system = system;
	 }

	 public void setNoteable_id(float noteable_id) {
	  this.noteable_id = noteable_id;
	 }

	 public void setNoteable_type(String noteable_type) {
	  this.noteable_type = noteable_type;
	 }

	 public void setResolvable(boolean resolvable) {
	  this.resolvable = resolvable;
	 }

	 public void setNoteable_iid(float noteable_iid) {
	  this.noteable_iid = noteable_iid;
	 }
	}

