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

public class Time_stats {
	 private float time_estimate;
	 private float total_time_spent;
	 private String human_time_estimate = null;
	 private String human_total_time_spent = null;


	 // Getter Methods 

	 public float getTime_estimate() {
	  return time_estimate;
	 }

	 public float getTotal_time_spent() {
	  return total_time_spent;
	 }

	 public String getHuman_time_estimate() {
	  return human_time_estimate;
	 }

	 public String getHuman_total_time_spent() {
	  return human_total_time_spent;
	 }

	 // Setter Methods 

	 public void setTime_estimate(float time_estimate) {
	  this.time_estimate = time_estimate;
	 }

	 public void setTotal_time_spent(float total_time_spent) {
	  this.total_time_spent = total_time_spent;
	 }

	 public void setHuman_time_estimate(String human_time_estimate) {
	  this.human_time_estimate = human_time_estimate;
	 }

	 public void setHuman_total_time_spent(String human_total_time_spent) {
	  this.human_total_time_spent = human_total_time_spent;
	 }
	}
