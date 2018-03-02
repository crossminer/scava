/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.bugzilla;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemAttachment;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemBug;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemComment;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.IBugTrackingSystemManager;
import org.eclipse.scava.repository.model.BugTrackingSystem;
import org.eclipse.scava.repository.model.bts.bugzilla.Bugzilla;

import utils.BugSearch.SearchLimiter;
import utils.BugSearch.SearchQuery;
import utils.BugzillaSession;
import utils.Comment;

import com.j2bugzilla.base.Attachment;
import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugzillaException;
import com.mongodb.DB;


public class BugzillaManager implements IBugTrackingSystemManager<Bugzilla> {
	
	private final String BUG_QUERY_LIMIT_A = "5";
	private final String BUG_QUERY_LIMIT_B = "250";
	private final int COMMENT_QUERY__NO_BUGS_RETRIEVED_AT_ONCE = 250;
	
	private Storage storage = new Storage();

	@Override
	public boolean appliesTo(BugTrackingSystem bugzilla) {
		return bugzilla instanceof Bugzilla;
	}

	@Override
	public BugTrackingSystemDelta getDelta(DB db, Bugzilla bugzilla, Date date) throws Exception {
		
		System.err.println("Date: " + date.toString());
		BugzillaSession session = new BugzillaSession(bugzilla.getUrl());
		
		BugTrackingSystemDelta delta = new BugTrackingSystemDelta();
		delta.setBugTrackingSystem(bugzilla);
		// Get bugs started on date
		List<Bug> bugs = getBugs(bugzilla, delta, session, date);
		// Get the comments of bugs that were started before date
//		PROBLEM: NOW WE SEARCH ONLY THE COMMENTS 
		getUpdatedBugsComments(bugzilla, delta, session, date);
		// Get the comments of bugs that were started on date
		getComments(bugzilla, delta, session, bugs, date);
		getUpdatedBugsAttachments(bugzilla, delta, session, date);
		return delta;
	}

	private void getUpdatedBugsComments(Bugzilla bugzilla, BugTrackingSystemDelta delta, 
			 BugzillaSession session, Date date) throws Exception {
		//=-=-=-=-=-=
		if (storage.getLatestCheckBugDate() != null && (storage.getCommentStoreSize()>0)&&(storage.getLatestCheckBugDate().compareTo(date)>0)) {
//			System.err.println("retrieving stored comments");
//			System.err.println("UBC:Date " + date + " is before " + storage.getLatestCheckBugDate());
			for (int bugId: storage.getBugIds()) {
				if (storage.containsComments(bugId)) {
					for (Comment comment: storage.getComments(bugId)) {
						if (date.compareTo(comment.getTimestamp())==0)  {
							storeComment(bugzilla, comment, delta);
							storeBug(bugzilla, storage.getBug(bugId), delta.getUpdatedBugs(), 
									"delta.getUpdatedBugs()", session);
						}
					}
				}
			}
		//=-=-=-=-=-=
		} else {
			SearchQuery[] searchQueries;
			//-=-=-=-=-=-=-=-
			// I HAVE TO CHECK IF THE BUG'S COMMENTS ARE PREVIOUSLY STORED
			// DATES NEED TO BE CHECKED AS WELL.
			//-=-=-=-=-=-=-=-
			if (!bugzilla.getComponent().equals("null") && !bugzilla.getComponent().equals("")) {
				searchQueries = new SearchQuery[5];
				searchQueries[4] = new SearchQuery(SearchLimiter.COMPONENT, bugzilla.getComponent());
			}
			else
				searchQueries = new SearchQuery[4];
			searchQueries[0] = new SearchQuery(SearchLimiter.PRODUCT, bugzilla.getProduct());
			//-=-=-=-=-=-=-=-
//    		Date queryDate = date;
//    		if (storage.getLatestCheckBugDate()!=null) {
//    			queryDate = storage.getLatestCheckBugDate().addDays(-1); 
//    			System.err.println("\tB query date was: " + date + " now changed to: " + queryDate);
//    		}
			searchQueries[1] = new SearchQuery(SearchLimiter.LIMIT, BUG_QUERY_LIMIT_B);
			searchQueries[2] = new SearchQuery( SearchLimiter.LAST_CHANGE_TIME, date.toJavaDate());

			System.err.println("retrieving comments for " + date);
			List<Bug> bugs = new ArrayList<Bug>();
			java.util.Date javaDate = date.toJavaDate();
//			int counter = 0;
			boolean noBugsRetrieved = false;
			int lastBugIdRetrieved = 0;
			while (!noBugsRetrieved) {
//				counter++;
				searchQueries[3] = new SearchQuery( SearchLimiter.CREATION_TIME, javaDate );
//				System.err.println("GET UPDATED BUG COMMENTS: retrieving bugs for: " + javaDate);
				List<Bug> retrievedBugs = session.getBugs(searchQueries);
				if (((retrievedBugs.size()==1) && (retrievedBugs.get(0).getID()==lastBugIdRetrieved))
						||(retrievedBugs.size()==0)) {
//					System.err.println("\tNo bugs retrieved!");
					noBugsRetrieved = true;
				} else {
//					System.err.println(counter + ". getBugs:\t" + retrievedBugs.size() + " bugs retrieved");
					bugs.addAll(retrievedBugs);
//					System.err.print("\tprocessing bugs:");
					for (Bug retrievedBug : retrievedBugs) {
//						System.err.print(" " + retrievedBug.getID());
						javaDate = session.getCreationTime(retrievedBug);
						lastBugIdRetrieved = retrievedBug.getID();
						storage.storeBug(retrievedBug, session.getCreationTime(retrievedBug));
					}
//					System.err.println("\n\tjavadate:" + javaDate);
				}
			}
//			-=-=-=-=-=-=-=-	
					
//			if (bugs.size() > 0)
			System.err.println("\tgetComments:\t" + bugs.size() + " bugs retrieved");
			int startOffset = 0, endOffset;
			while (startOffset < bugs.size()) {
				if (startOffset + COMMENT_QUERY__NO_BUGS_RETRIEVED_AT_ONCE <= bugs.size())
					endOffset = startOffset + COMMENT_QUERY__NO_BUGS_RETRIEVED_AT_ONCE;
				else
					endOffset = bugs.size();
				List<Bug> bugsSlice = bugs.subList(startOffset, endOffset);
				getComments(bugzilla, delta, session, bugsSlice, date);
				startOffset += COMMENT_QUERY__NO_BUGS_RETRIEVED_AT_ONCE;
			}
		}
	}
	
	private void getComments(Bugzilla bugzilla, BugTrackingSystemDelta delta, 
				BugzillaSession session, List<Bug> bugs, Date date) throws Exception {
	    if (bugs.size()>0) {
    		List<Integer> bugIds = new ArrayList<Integer>();
    		Map<Integer, Bug> bugIdBugMap = new HashMap<Integer, Bug>();
//    		Date latestCommentCheckDate = null;
//    		int storedItems = 0;
	    	for (Bug bug: bugs) {
				//=-=-=-=-=-=
				if ((storage.containsComments(bug.getID()))
						&&(date.compareTo(storage.getCommentStore(bug.getID()).
								getLatestCommentCheckDate())<0)) {
//					System.err.println("\tCO: Date " + date + " is before " + 
//							storage.getCommentStore(bug.getID()).getLatestCommentCheckDate());
					for (Comment comment: storage.getComments(bug.getID())) {
						if (date.compareTo(comment.getTimestamp())==0)  {
//							storedItems++;
							storeComment(bugzilla, comment, delta);
							storeBug(bugzilla, bug, delta.getUpdatedBugs(), 
									"delta.getUpdatedBugs()", session);
						}
					}
				//=-=-=-=-=-=
				} else {
//					if ((latestCommentCheckDate==null)||(latestCommentCheckDate.compareTo(date)<0))
//						latestCommentCheckDate = 
//							storage.getCommentStore(bug.getID()).getLatestCommentCheckDate();
					bugIds.add(bug.getID());
					bugIdBugMap.put(bug.getID(), bug);
				}
	    	}
	    	if (bugIds.size() > 0) {
	    		System.err.println("Retrieving comments for " + bugIds.size() + " bugIds.");
				//-=-=-=-=-=-=-=-
//	    		Date queryDate = date;
//	    		if (latestCommentCheckDate!=null) {
//	    			queryDate = latestCommentCheckDate.addDays(-1); 
//	    			System.err.println("\tC query date was: " + date + " now changed to: " + queryDate);
//	    		}
				List<Comment> comments = session.getCommentsForBugIds(bugIds, date.toJavaDate());
				//-=-=-=-=-=-=-=-
//	    		if (comments.size() > 0)
//	    			System.err.println("getBugs for comments:\t" + comments.size() + " comments retrieved");
	    		for (Comment comment: comments) {
	    			if (date.compareTo(comment.getTimestamp())==0)  {
//					System.out.println(date.toString() + 
//						"\t" + comment.getBugId() + 
//						"\t" + comment.getId() + 
//						"\t" + comment.getTimestamp().toString());
	    				storeBug(bugzilla, bugIdBugMap.get(comment.getBugId()), 
	    						delta.getUpdatedBugs(), "delta.getUpdatedBugs()", session);
//	    				storedItems++;
	    				storeComment(bugzilla, comment, delta);
	    			}
	    			//-=-=-=-=-=-=-=-
	    			Bug bug = bugIdBugMap.get(comment.getBugId());
	    			storage.storeComment(bug, session.getCreationTime(bug), comment);
	    			//-=-=-=-=-=-=-=-
	    		}
	    	}
//	    	if (storedItems>0) {
//	    		System.err.println("getComments(): stored " + storedItems + " new comments");
//	    		System.err.println("getComments(): stored " + storedItems + " updated bugs");
//	    	}
	    }
	}

	private void getUpdatedBugsAttachments(Bugzilla bugzilla, BugTrackingSystemDelta delta, 
			BugzillaSession session, Date date) throws BugzillaException {
		getUpdatedBugsAttachments(bugzilla, delta, delta.getNewBugs(), session, date);
		getUpdatedBugsAttachments(bugzilla, delta, delta.getUpdatedBugs(), session, date);
	}
	
	
	private void getUpdatedBugsAttachments(Bugzilla bugzilla, BugTrackingSystemDelta delta, 
			List<BugTrackingSystemBug> bugs, BugzillaSession session, Date date) throws BugzillaException {
		if (bugs.size()>0) {
			int startOffset = 0, endOffset,counter=1;
			while (startOffset < bugs.size()) {
				if (startOffset + (5*COMMENT_QUERY__NO_BUGS_RETRIEVED_AT_ONCE) <= bugs.size())
					endOffset = startOffset + (5*COMMENT_QUERY__NO_BUGS_RETRIEVED_AT_ONCE);
				else
					endOffset = bugs.size();
				List<Integer> bugIdSlice = new ArrayList<Integer>();
				for (BugTrackingSystemBug bug: bugs.subList(startOffset, endOffset)) {
					//=-=-=-=-=-=
					if ((storage.containsAttachments(bug.getBugId()))
							&&(date.compareTo(storage.getAttachmentStore(bug.getBugId()).
									getLatestAttachmentCheckDate())<0)) {
//						System.err.println("UB: Date " + date + " is before " + 
//								storage.getAttachmentStore(bug.getBugId()).getLatestAttachmentCheckDate());
						for (Attachment attachment: storage.getAttachments(bug.getBugId()))
							storeAttachment(bugzilla, attachment, delta);
					//=-=-=-=-=-=
					} else {
						bugIdSlice.add(Integer.parseInt(bug.getBugId()));
					}
				}
				if (bugIdSlice.size()>0) {
					List<Attachment> attachments = session.getAttachmentsforIdList(bugIdSlice);
					for (Attachment attachment: attachments) {
						//=-=-=-=-=-=
						storage.storeAttachment(attachment);
						//=-=-=-=-=-=
						storeAttachment(bugzilla, attachment, delta);
					}
					if (attachments.size() > 0)
						System.err.println(counter + ". getAttachments(): stored " + attachments.size() + " attachments");
				}
				counter++;
				startOffset += (5*COMMENT_QUERY__NO_BUGS_RETRIEVED_AT_ONCE);
			}
		}
	}

	private List<Bug> getBugs(Bugzilla bugzilla, 
			BugTrackingSystemDelta delta, BugzillaSession session, Date date) throws BugzillaException {
		SearchQuery[] searchQueries;
		if (!bugzilla.getComponent().equals("null") && !bugzilla.getComponent().equals(""))
			searchQueries = new SearchQuery[4];
		else
			searchQueries = new SearchQuery[3];
		searchQueries[0] = new SearchQuery(SearchLimiter.PRODUCT, bugzilla.getProduct());
		searchQueries[1] = new SearchQuery(SearchLimiter.LIMIT, BUG_QUERY_LIMIT_A);
		if (!bugzilla.getComponent().equals("null") && !bugzilla.getComponent().equals(""))
			searchQueries[3] = new SearchQuery(SearchLimiter.COMPONENT, bugzilla.getComponent());
		List<Bug> bugs = new ArrayList<Bug>();
		java.util.Date javaDate = date.toJavaDate();
//		int counter = 0;
		int	storedBugs = 0;
		boolean noBugsRetrieved = false;
		List<Integer> previousMessageIds = new ArrayList<Integer>();
		System.err.println("GET BUGS: retrieving bugs for: " + date);
		int lastBugIdRetrieved = 0;
		while ((date.compareTo(javaDate)==0)&&(!noBugsRetrieved)) {
//			counter++;
			searchQueries[2] = new SearchQuery( SearchLimiter.CREATION_TIME, javaDate );
			List<Bug> retrievedBugs = session.getBugs(searchQueries);
//		    if (retrievedBugs.size() > 0)
//		    	System.err.println(counter + ". getBugs:\t" + retrievedBugs.size() + " bugs retrieved");
			if (previousMessageIds.size()==retrievedBugs.size()) {
				boolean same = true;
				for (int index=0; index<retrievedBugs.size(); index++)
					if (retrievedBugs.get(index).getID()!=previousMessageIds.get(index))
						same = false;
				if (same) {
					String stringQuery = (String)searchQueries[1].getQuery().toString();
					searchQueries[1] = new SearchQuery(SearchLimiter.LIMIT, 2 * Integer.parseInt(stringQuery));
				}
			}
//			System.err.print("\tprocessing bugs:");
			previousMessageIds = new ArrayList<Integer>();
			for (Bug retrievedBug : retrievedBugs) {
//				System.err.print(" " + retrievedBug.getID());
				if (date.compareTo(session.getCreationTime(retrievedBug)) == 0) {
					lastBugIdRetrieved = retrievedBug.getID();
					previousMessageIds.add(retrievedBug.getID());
					storeBug(bugzilla, retrievedBug, delta.getNewBugs(), "delta.getNewBugs()", session);
					bugs.add(retrievedBug);
					storedBugs++;
				}
				javaDate = session.getCreationTime(retrievedBug);
//				System.err.println("\t javadate:" + javaDate);
				storage.storeBug(retrievedBug, session.getCreationTime(retrievedBug));
			}
//			System.err.println("\n\tlast javadate:" + javaDate);
			if ((retrievedBugs.size()==1) && (retrievedBugs.get(0).getID()==lastBugIdRetrieved)
					|| (retrievedBugs.size()==0)) {
//				System.err.println("\tNo bugs retrieved!");
				noBugsRetrieved = true;
			}
		}
		
//		System.err.println(storedBugs + " bugs to store");
		if (storedBugs>0)
			System.err.println("getBugs(): stored " + storedBugs + " new bugs");
		return bugs;
	}

	private void storeComment(Bugzilla bugzilla, Comment comment, BugTrackingSystemDelta delta) {
		Boolean alreadyStored = false;
		for (BugTrackingSystemComment storedComment: delta.getComments())
			if (storedComment.equals(comment.getBugId(), comment.getId()))
				alreadyStored = true;
		if (!alreadyStored) {
			BugzillaComment bugzillaComment = new BugzillaComment();		
			bugzillaComment.setAuthor(comment.getAuthor());
			bugzillaComment.setBugId(comment.getBugId()+"");

			Bugzilla newBugzilla = new Bugzilla();
			newBugzilla.setUrl(bugzilla.getUrl());
			newBugzilla.setProduct(bugzilla.getProduct());
			newBugzilla.setComponent(bugzilla.getComponent());
			bugzillaComment.setBugTrackingSystem(newBugzilla);

			bugzillaComment.setCommentId(comment.getId()+"");
			bugzillaComment.setCreationTime(comment.getTimestamp());
			bugzillaComment.setCreator(comment.getCreator());
			bugzillaComment.setCreatorId(comment.getCreatorId()+"");
			bugzillaComment.setText(comment.getText());
			delta.getComments().add(bugzillaComment);
		}
	}
	
	private void storeAttachment(Bugzilla bugzilla, Attachment attachment, BugTrackingSystemDelta delta) {
		Boolean alreadyStored = false;
		for (BugTrackingSystemAttachment storedAttachment: delta.getAttachments())
			if (storedAttachment.equals(attachment))
				alreadyStored = true;
		if (!alreadyStored) {
			BugTrackingSystemAttachment bugAttachment = new BugTrackingSystemAttachment();		
			bugAttachment.setCreator(attachment.getCreator());
			bugAttachment.setBugId(attachment.getBugID()+"");
			
			Bugzilla newBugzilla = new Bugzilla();
			newBugzilla.setUrl(bugzilla.getUrl());
			newBugzilla.setProduct(bugzilla.getProduct());
			newBugzilla.setComponent(bugzilla.getComponent());
			bugAttachment.setBugTrackingSystem(newBugzilla);
		
			bugAttachment.setAttachmentId(attachment.getAttachmentID()+"");
			bugAttachment.setFilename(attachment.getFileName());
			bugAttachment.setMimeType(attachment.getMIMEType());
			delta.getAttachments().add(bugAttachment);
//			System.err.println("stored 1 attachment");
		}
	}

	private void storeBug(Bugzilla bugzilla, Bug bug,
						   List<BugTrackingSystemBug> deltaBugList, String bugListName, 
						   BugzillaSession session) {
		Boolean alreadyStored = false;
		for (BugTrackingSystemBug storedBug: deltaBugList)
			if (storedBug.equals(bug.getID()))
				alreadyStored = true;
		if (!alreadyStored) {
			BugzillaBug bugzillaBug = new BugzillaBug();
			bugzillaBug.setBugId(bug.getID()+"");
			bugzillaBug.setCreationTime(session.getCreationTime(bug));
			bugzillaBug.setCreator(session.getCreator(bug));
			bugzillaBug.setStatus(bug.getStatus());
			bugzillaBug.setSummary(bug.getSummary());
			bugzillaBug.setAssignedTo(session.getAssignedTo(bug));
			bugzillaBug.setCategory(session.getCategory(bug));
			bugzillaBug.setClassification(session.getClassification(bug));
//			bugzillaBug.setCloneOf(session.getCloneOf(bug));
			bugzillaBug.setCrm(session.getCrm(bug));
			bugzillaBug.setDocType(session.getDocType(bug));
			bugzillaBug.setDocumentationAction(session.getDocumentationAction(bug));
			bugzillaBug.setEnvironment(session.getEnvironment(bug));
			bugzillaBug.setFixedIn(session.getFixedIn(bug));
			bugzillaBug.setIsCcAccesible(session.getIsCcAccesible(bug));
			bugzillaBug.setIsConfirmed(session.getIsConfirmed(bug));
			bugzillaBug.setIsCreatorAccessible(session.getIsCreatorAccessible(bug));
			bugzillaBug.setIsOpen(session.getIsOpen(bug));
			bugzillaBug.setLastChangeTime(session.getLastChangeTime(bug));
			bugzillaBug.setLastClosed(session.getLastClosed(bug));
			bugzillaBug.setMountType(session.getMountType(bug));
			bugzillaBug.setOperatingSystem(bug.getOperatingSystem());
			bugzillaBug.setPlatform(bug.getPlatform());
			bugzillaBug.setPriority(bug.getPriority());
			bugzillaBug.setProduct(bug.getProduct());
			bugzillaBug.setQualityAssuranceContact(session.getQualityAssuranceContact(bug));
			bugzillaBug.setRegressionStatus(session.getRegressionStatus(bug));
			bugzillaBug.setReleaseNotes(session.getReleaseNotes(bug));
			bugzillaBug.setResolution(bug.getResolution());
			bugzillaBug.setSeverity(session.getSeverity(bug));
			bugzillaBug.setStoryPoints(session.getStoryPoints(bug));
			bugzillaBug.setTargetMilestone(session.getTargetMilestone(bug));
			bugzillaBug.setType(session.getType(bug));
			bugzillaBug.setVerifiedBranch(session.getVerifiedBranch(bug));
			bugzillaBug.setWhiteBoard(session.getWhiteBoard(bug));
			
			Bugzilla newBugzilla = new Bugzilla();
			newBugzilla.setUrl(bugzilla.getUrl());
			newBugzilla.setProduct(bugzilla.getProduct());
			newBugzilla.setComponent(bugzilla.getComponent());
			bugzillaBug.setBugTrackingSystem(newBugzilla);

			deltaBugList.add(bugzillaBug);
		}
	}

	@Override
	public Date getFirstDate(DB db, Bugzilla bugzilla) throws Exception {

		SearchQuery[] searchQueries;
		if (!bugzilla.getComponent().equals("null") && !bugzilla.getComponent().equals(""))
			searchQueries = new SearchQuery[3];
		else
			searchQueries = new SearchQuery[2];
		searchQueries[0] = new SearchQuery(SearchLimiter.PRODUCT, bugzilla.getProduct()); // "Pulp");
		if (!bugzilla.getComponent().equals("null") && !bugzilla.getComponent().equals(""))
			searchQueries[2] = new SearchQuery(SearchLimiter.COMPONENT, bugzilla.getComponent());  // "acpi");
		searchQueries[1] = new SearchQuery(SearchLimiter.LIMIT, "10");

		BugzillaSession session = new BugzillaSession(bugzilla.getUrl());
		List<Bug> bugs = session.getBugs(searchQueries);
		System.err.println("getFirstDate:\t" + bugs.size() + " bugs retrieved");
		Date date = null;
		if (bugs.size()>0) {
			Bug bug = bugs.get(0);
			date = new Date(session.getCreationTime(bug));
		} else {
			System.err.println("Unable to retrieve first date");
		}
		date.addDays(-1);
		System.err.println("\t" + date.toString());
		return date;
	}

	@Override
	public String getContents(DB db, Bugzilla bugzilla, BugTrackingSystemBug bug) throws Exception {
		BugzillaSession session = new BugzillaSession(bugzilla.getUrl());
		Bug retrievedBug = session.getBugById(Integer.parseInt(bug.getBugId()));
//		System.err.println("getContents:\tbug retrieved");
		return retrievedBug.getSummary();
	}

	@Override
	public String getContents(DB db, Bugzilla bugzilla,
			BugTrackingSystemComment comment) throws Exception {
		BugzillaSession session = new BugzillaSession(bugzilla.getUrl());
		List<Comment> retrievedComments = 
				session.getCommentsForBugId(Integer.parseInt(comment.getBugId()));
		for (Comment retrievedComment: retrievedComments) {
			if (retrievedComment.getId() == Integer.parseInt(comment.getCommentId())) {
//				System.err.println("getContents:\tcomment retrieved");
				return retrievedComment.getText();
			}
		}
		return null;
	}

}
