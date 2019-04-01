/*******************************************************************************
 * Copyright (c) 2018 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.mantis.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.scava.platform.bugtrackingsystem.mantis.MantisIssue;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemComment;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.fasterxml.jackson.databind.JsonNode;

public class MantisReaderUtils {

	public static Date convertStringToDate(String isoDate) {
		isoDate = isoDate.replaceAll("\"", "");
		DateTimeFormatter parser = ISODateTimeFormat.dateTimeParser();
		DateTime date = parser.parseDateTime(isoDate);
		return date.toDate();
	}

	private static String formatString(String input) {

		try {

			input = input.substring(1, input.length() - 1);

		} catch (NullPointerException e) {

			input = "";

		}

		return input;
	}

	// These methods are designed to handle "null pointer exceptions correctly
	// when issues
	// do not not have those fields populated

	public static String setAttribute(JsonNode node, String nodename) {

		String att = new String();

		try {
			att = formatString(node.get(nodename).toString());

		} catch (NullPointerException e) {
			att = "";
			return att;
		}

		return att;
	}

	public static String setAttribute(JsonNode node, String nodename1, String nodename2) {

		String att = new String();

		try {
			att = formatString(node.get(nodename1).get(nodename2).toString());

		} catch (NullPointerException e) {
			att = "";
			return att;
		}

		return att;
	}

	public static List<String> setTags(JsonNode node) {
		List<String> tags = new ArrayList<>();
		try {
			// fixes Tags being sent as JSON objects by Mantis - really annoying
			// but unnecessary
			String[] pairs = node.get("tags").toString().replaceAll("\\{", "").replaceAll("\\[", "")
					.replaceAll("\\}", "").replaceAll("\\]", "").replaceAll("\"", "").split(",");

			for (String s : pairs) {
				if (s.contains("name")) {
					String[] keyvalue = s.split(":");
					String tag = keyvalue[1].toString();
					tags.add(tag);
				}
				
			}

		} catch (NullPointerException np) {

		}

		return tags;

	}
	
	
	public static List<BugTrackingSystemComment> setNotes(MantisIssue issue, JsonNode node) {
		List<BugTrackingSystemComment> notes = new ArrayList<>();
	
			// fixes Tags being sent as JSON objects by Mantis - really annoying
			// but unnecessary
		
		try{
			BugTrackingSystemComment comment = new BugTrackingSystemComment();
			
			comment.setBugId(issue.getBugId());
			comment.setBugTrackingSystem(issue.getBugTrackingSystem());
			
			String[] pairs = node.get("notes").toString().replaceAll("\\{", "").replaceAll("\\[", "")
					.replaceAll("\\}", "").replaceAll("\\]", "").replaceAll("\"", "").split(",");

			for (String s : pairs) {
				
				String[] keyvalue = s.split(":");
				
				switch (keyvalue[0]) {
					
					case "id":
						comment.setCommentId(keyvalue[1]);
						break;
					case "text":
						comment.setText(keyvalue[1]);
						break;
					case "created_at":
						comment.setCreationTime(convertStringToDate(keyvalue[1]));
						break;
					case "reporter":
						comment.setCreator(keyvalue[1]);
						break;
					default:
						break;
					
				}
			}
			notes.add(comment);
						
	} catch (NullPointerException np) {

	}
		return notes;

	}
	

}
