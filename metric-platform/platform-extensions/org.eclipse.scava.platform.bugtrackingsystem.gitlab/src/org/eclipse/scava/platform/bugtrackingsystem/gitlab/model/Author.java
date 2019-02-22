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

public class Author {
	 private float id;
	 private String name;
	 private String username;
	 private String state;
	 private String avatar_url;
	 private String web_url;


	 // Getter Methods 

	 public float getId() {
	  return id;
	 }

	 public String getName() {
	  return name;
	 }

	 public String getUsername() {
	  return username;
	 }

	 public String getState() {
	  return state;
	 }

	 public String getAvatar_url() {
	  return avatar_url;
	 }

	 public String getWeb_url() {
	  return web_url;
	 }

	 // Setter Methods 

	 public void setId(float id) {
	  this.id = id;
	 }

	 public void setName(String name) {
	  this.name = name;
	 }

	 public void setUsername(String username) {
	  this.username = username;
	 }

	 public void setState(String state) {
	  this.state = state;
	 }

	 public void setAvatar_url(String avatar_url) {
	  this.avatar_url = avatar_url;
	 }

	 public void setWeb_url(String web_url) {
	  this.web_url = web_url;
	 }
	}