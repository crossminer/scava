/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import utils.BugSearch.SearchLimiter;
import utils.BugSearch.SearchQuery;

import com.j2bugzilla.base.Attachment;
import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugzillaException;
import com.j2bugzilla.base.ConnectionException;

import utils.GetAttachments;
import com.j2bugzilla.rpc.GetBug;
import com.j2bugzilla.rpc.LogIn;

// see http://j2bugzilla.com/quickstart.html
public class BugzillaSession {
	private BugzillaConnector conn;

	/*
	 * BugzillaConnector conn = new BugzillaConnector();
	 * conn.connectTo("http://landfill.bugzilla.org/bugzilla-tip"); BugSearch
	 * findBugs = new BugSearch(SearchLimiter.ASSIGNED_TO,
	 * "rfeynman@cornell.edu"); conn.executeMethod(findBugs); List<Bug> results
	 * = findBugs.getSearchResults();
	 */

	public BugzillaSession(String url) throws ConnectionException {
		conn = new BugzillaConnector();
		conn.connectTo(url);
	}

	public BugzillaSession(String url, String user, String password)
			throws ConnectionException, BugzillaException {
		conn = new BugzillaConnector();
		conn.connectTo(url);
		login(user, password);
	}

	public void login(String user, String password) throws BugzillaException {
		LogIn logIn = new LogIn(user, password);
		conn.executeMethod(logIn);
	}

	public List<Bug> getBugsByOwner(String owner) throws BugzillaException {
		BugSearch findBugs = new BugSearch(new SearchQuery(SearchLimiter.OWNER,
				owner));
		conn.executeMethod(findBugs);
		return findBugs.getSearchResults();
	}

	public Bug getBugById(int id) throws BugzillaException {
		GetBug getBug = new GetBug(id);
		conn.executeMethod(getBug);
		return getBug.getBug();
	}

	public Bug getBugByAlias(String alias) throws BugzillaException {
		GetBug getBug = new GetBug(alias);
		conn.executeMethod(getBug);
		return getBug.getBug();
	}

	public List<Comment> getCommentsForBugId(int bugId)
			throws BugzillaException {
		BugComments comments = new BugComments(bugId);
		conn.executeMethod(comments);
		return comments.getComments();
	}

	public List<Comment> getCommentsForBugId(int bugId, Date date)
			throws BugzillaException {
		BugComments comments = new BugComments(bugId, date);
		conn.executeMethod(comments);
		return comments.getComments();
	}

	public List<Comment> getCommentsForBugIds(List<Integer> bugIdArray, Date date)
			throws BugzillaException {
		BugComments comments = new BugComments(bugIdArray, date);
		conn.executeMethod(comments);
		return comments.getComments();
	}
	
	public List<Attachment> getAttachmentForBugId(int bugId) throws BugzillaException {
		GetAttachments getAttachments= new GetAttachments(bugId);
		conn.executeMethod(getAttachments);
		List<Attachment> attachments = getAttachments.getAttachments();
//		for(Attachment attachment: attachments) {
//			System.out.println("bugId: " + bugId 
//								+ "\tattachmentId: " + attachment.getAttachmentID() 
//								+ "\tattachmentFilenameType: " + attachment.getFileName() 
//								+ "\tattachmentMIMEtype: " + attachment.getMIMEType());
//		}
		return attachments;
	}

	public List<Attachment> getAttachmentsforIdList(List<Integer> idArray) 
			throws BugzillaException {
		GetAttachments getAttachments = new GetAttachments(idArray);
		conn.executeMethod(getAttachments);
		List<Attachment> attachments = getAttachments.getAttachments();
		return attachments;
	}

	public List<Comment> getCommentsForBugId(List<Integer> bugIdArray) //int[] bugIdArray)
			throws BugzillaException {
		BugComments comments = new BugComments(bugIdArray);
		conn.executeMethod(comments);
		return comments.getComments();
	}

	public List<Comment> getCommentsForId(Comment comment) //int[] bugIdArray)
			throws BugzillaException {
		BugComments comments = new BugComments(comment);
		conn.executeMethod(comments);
		return comments.getComments();
	}

	public List<Bug> getBugs(SearchQuery[] searchQueries)
			throws BugzillaException {
		BugSearch findBugs = new BugSearch(searchQueries);
		conn.executeMethod(findBugs);
		return findBugs.getSearchResults();
	}

	// Is this correct?
	public List<Bug> getBugsByAliases(Collection<String> aliases)
			throws BugzillaException {
		List<Bug> bugs = null;
		if (null != aliases && aliases.size() > 0) {
			SearchQuery[] queries = new SearchQuery[aliases.size()];
			int i = 0;
			for (String alias : aliases) {
				queries[i] = new SearchQuery(SearchLimiter.ALIAS, alias);
			}

			BugSearch findBugs = new BugSearch(queries);
			conn.executeMethod(findBugs);
			bugs = findBugs.getSearchResults();
		}
		return bugs;
	}

	public static void main(String[] args) {
		final String URL = "https://bugzilla.redhat.com/xmlrpc.cgi";

		try {
			BugzillaSession session = new BugzillaSession(URL);
			SearchQuery[] searchQueries = new SearchQuery[3];
			searchQueries[0] = 
					new SearchQuery(SearchLimiter.PRODUCT,"Fedora"); //	"Pulp");
//			searchQueries[1] = new SearchQuery(SearchLimiter.COMPONENT, "acpi");  // "acpi");
			searchQueries[2] = 
					new SearchQuery(SearchLimiter.LIMIT, "100");

			Calendar cal = Calendar.getInstance();
		    cal.set(Calendar.YEAR, 2013);
		    cal.set(Calendar.MONTH, 0);
		    cal.set(Calendar.DAY_OF_MONTH, 15);
		    cal.set(Calendar.HOUR, 23);
		    cal.set(Calendar.MINUTE, 30);
		    cal.set(Calendar.SECOND, 43);
		    Date dateRepresentation = cal.getTime();

			searchQueries[1] = new SearchQuery(SearchLimiter.LAST_CHANGE_TIME, dateRepresentation);
//					"2011" + "-" + "6" + "-" + "15" + " " + "18" + ":"  + "38" + ":" + "42");
			
//			searchQueries[3] = new SearchQuery(SearchLimiter.CREATION_TIME, ":2011" + "-" + "12" + "-" + "31");
//			searchQueries[2] = new SearchQuery(SearchLimiter.CREATION_TIME, "2009/10/30:2011/12/31");
//			searchQueries[2] = new SearchQuery(SearchLimiter.CREATION_DATE, "2011" + "/" + "6" + "/" + "15");
		    
//			opened-after:2009/4/1
//			opened-after:2009/4/1
			
//			searchQueries[0] = new SearchQuery(SearchLimiter.STATUS, "NEW");
			List<Bug> bugs = session.getBugs(searchQueries);
//			session.getBugByAlias(alias);
//			session.getBugById(id);
//			session.getBugsByAliases(aliases);
//			session.getBugsByOwner(owner);
			
			System.out.println("Number of bugs: " + bugs.size());
			System.out.println("----------------");
			for (Bug bug : bugs) {
				System.out.println("Id: "+ bug.getID());
				System.out.println("\tProduct: "+ bug.getProduct());
				System.out.println("\tStatus: "+ bug.getStatus());
				System.out.println("\tSummary: "+ bug.getSummary());
//				System.out.println("Flags: "+ bug.getFlags());
//				System.out.println("Components: "+ bug.getComponent());
//				System.out.println("\tOperating System: "+ bug.getOperatingSystem());
//				System.out.println("\tPlatform: "+ bug.getPlatform());
				System.out.println("\tResolution: "+ bug.getResolution());
//				System.out.println("Alias: "+ bug.getAlias());
				System.out.println("\tPriority: "+ bug.getPriority());
//				System.out.println("Version: "+ bug.getVersion());
//				System.out.println("\tParameter - Target_milestone: "			+ session.getTargetMilestone(bug));
//				System.out.println("\tParameter - LastChangeTime: "			+ session.getLastChangeTime(bug));
//				System.out.println("\tParameter - IsConfirmed: "				+ session.getIsConfirmed(bug));
//				System.out.println("\tParameter - Environment: "				+ session.getEnvironment(bug));
				System.out.println("\tParameter - IsOpen: "						+ session.getIsOpen(bug));
//				System.out.println("\tParameter - StoryPoints: "				+ session.getStoryPoints(bug));
//				System.out.println("\tParameter - DocumentationAction: "		+ session.getDocumentationAction(bug));
//				System.out.println("\tParameter - Crm: "						+ session.getCrm(bug));
//				System.out.println("\tParameter - Creator: "					+ session.getCreator(bug));
//				System.out.println("\tParameter - QualityAssuranceContact: "	+ session.getQualityAssuranceContact(bug));
//				System.out.println("\tParameter - CreationTime: "				+ session.getCreationTime(bug));
//				System.out.println("\tParameter - Category: "					+ session.getCategory(bug));
//				System.out.println("\tParameter - MountType: "				+ session.getMountType(bug));
//				System.out.println("\tParameter - FixedIn-parameter: "		+ session.getFixedIn(bug));
//				System.out.println("\tParameter - IsCreatorAccessible: "		+ session.getIsCreatorAccessible(bug));
//				System.out.println("\tParameter - IsCcAccesible: "			+ session.getIsCcAccesible(bug));
//				System.out.println("\tParameter - VerifiedBranch: "			+ session.getVerifiedBranch(bug));
//				System.out.println("\tParameter - ReleaseNotes: "				+ session.getReleaseNotes(bug));
//				System.out.println("\tParameter - Severity: "					+ session.getSeverity(bug));
//				System.out.println("\tParameter - DocType: "					+ session.getDocType(bug));
//				System.out.println("\tParameter - Url: "						+ session.getUrl(bug));
//				System.out.println("\tParameter - CloneOf: "					+ session.getCloneOf(bug));
//				System.out.println("\tParameter - AssignedTo: "				+ session.getAssignedTo(bug));
//				System.out.println("\tParameter - LastClosed: "				+ session.getLastClosed(bug));
//				System.out.println("\tParameter - WhiteBoard: "				+ session.getWhiteBoard(bug));
//				System.out.println("\tParameter - RegressionStatus: "			+ session.getRegressionStatus(bug));
//				System.out.println("\tParameter - Classification: "			+ session.getClassification(bug));
//				System.out.println("\tParameter - Type: "						+ session.getType(bug));

//				for (Entry<Object, Object> parameter: bug.getParameterMap().entrySet()) {
//					System.out.println("Parameter: "+ parameter.getKey() + "\t" + parameter.getValue() + "\t" + parameter.getValue().getClass().getName());
//				}
//
			}
			
//			Calendar cal = Calendar.getInstance();
//		    cal.set(Calendar.YEAR, 2012);
//		    cal.set(Calendar.MONTH, 0);
//		    cal.set(Calendar.DAY_OF_MONTH, 25);
//		    cal.set(Calendar.HOUR, 11);
//		    cal.set(Calendar.MINUTE, 53);
//		    cal.set(Calendar.SECOND, 43);
//		    Date dateRepresentation = cal.getTime();
//		    
		    List<Integer> idArray = new ArrayList<Integer>();
//		    idArray.add(bugId);
		    idArray.add(713616);
		    idArray.add(741542);
		    idArray.add(741547);
//
		    List<Comment> comments = session.getCommentsForBugIds(idArray, dateRepresentation);
			
////			int commentNo = 1;
			for (Comment comment: comments) {
////				System.out.println("Comment No: "+ commentNo);
				System.out.println("\tComment Id: "+ comment.getId());
				System.out.println("\t\tComment Time: "+ comment.getTimestamp());
				System.out.println("\t\tComment BugId: "+ comment.getBugId());
//				System.out.println("\t\tComment Creator: "+ comment.getCreator());
//				System.out.println("\t\tComment CreatorId: "+ comment.getCreatorId());
//				System.out.println("\t\tComment Author: "+ comment.getAuthor());
////				System.out.println("Comment Text: "+ comment.getText());
////				commentNo++;
			}
			
		int attachmentNo = 1;
			
		for (Bug bug: bugs) idArray.add(bug.getID());
		List<Attachment> attachments = session.getAttachmentsforIdList(idArray);
		for (Attachment attachment: attachments) {
			System.out.println("Attachment No: "+ attachmentNo);
			System.out.println("\tAttachment Id: "+ attachment.getAttachmentID());
			System.out.println("\t\tAttachment BugId: "+ attachment.getBugID());
			System.out.println("\t\tAttachment Filename: "+ attachment.getFileName());
			System.out.println("\t\tAttachment Mimetype: "+ attachment.getMIMEType());
			System.out.println("\t\tAttachment Creator: "+ attachment.getCreator());
			System.out.println("\t\tAttachment RawData: "+ attachment.getRawData());
			attachmentNo++;
		}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String targetMilestoneString = "target_milestone";
	public String getTargetMilestone(Bug bug) {
		return (String) bug.getParameterMap().get(targetMilestoneString);
	}

	private static String lastChangeTimeString = "last_change_time";
	public Date getLastChangeTime(Bug bug) {
		return (Date) bug.getParameterMap().get(lastChangeTimeString);
	}

	private static String isConfirmedString = "is_confirmed";
	public Boolean getIsConfirmed(Bug bug) {
		return (Boolean) bug.getParameterMap().get(isConfirmedString);
	}

	private static String environmentString = "cf_environment";
	public String getEnvironment(Bug bug) {
		return (String) bug.getParameterMap().get(environmentString);
	}

	private static String isOpenString = "is_open";
	public Boolean getIsOpen(Bug bug) {
		return (Boolean) bug.getParameterMap().get(isOpenString);
	}

	private static String storyPointsString = "cf_story_points";
	public String getStoryPoints(Bug bug) {
		return (String) bug.getParameterMap().get(storyPointsString);
	}

	private static String documentationActionString = "cf_documentation_action";
	public String getDocumentationAction(Bug bug) {
		return (String) bug.getParameterMap().get(documentationActionString);
	}

	private static String crmString = "cf_crm";
	public String getCrm(Bug bug) {
		return (String) bug.getParameterMap().get(crmString);
	}

	private static String creatorString = "creator";
	public String getCreator(Bug bug) {
		return (String) bug.getParameterMap().get(creatorString);
	}
		
	private static String qualityAssuranceContactString = "qa_contact";
	public String getQualityAssuranceContact(Bug bug) {
		return (String) bug.getParameterMap().get(qualityAssuranceContactString);
	}
	
	
	private static String creationTimeString = "creation_time";
	public Date getCreationTime(Bug bug) {
		return (Date) bug.getParameterMap().get(creationTimeString);
	}

	private static String categoryString = "cf_category";
	public String getCategory(Bug bug) {
		return (String) bug.getParameterMap().get(categoryString);
	}
	
	private static String mountTypeString = "cf_mount_type";
	public String getMountType(Bug bug) {
		return (String) bug.getParameterMap().get(mountTypeString);
	}
	
	private static String fixedInString = "cf_fixed_in";
	public String getFixedIn(Bug bug) {
		return (String) bug.getParameterMap().get(fixedInString);
	}
		
	private static String isCreatorAccessibleString = "is_creator_accessible";
	public Boolean getIsCreatorAccessible(Bug bug) {
		return (Boolean) bug.getParameterMap().get(isCreatorAccessibleString);
	}

	private static String isCcAccesibleString = "is_cc_accessible";
	public Boolean getIsCcAccesible(Bug bug) {
		return (Boolean) bug.getParameterMap().get(isCcAccesibleString);
	}

	private static String verifiedBranchString = "cf_verified_branch";
	public String getVerifiedBranch(Bug bug) {
		return (String) bug.getParameterMap().get(verifiedBranchString);
	}
	
	private static String releaseNotesString = "cf_release_notes";
	public String getReleaseNotes(Bug bug) {
		return (String) bug.getParameterMap().get(releaseNotesString);
	}
	
	private static String severityString = "severity";
	public String getSeverity(Bug bug) {
		return (String) bug.getParameterMap().get(severityString);
	}
	
	private static String docTypeString = "cf_doc_type";
	public String getDocType(Bug bug) {
		return (String) bug.getParameterMap().get(docTypeString);
	}
	
	private static String urlString = "url";
	public String getUrl(Bug bug) {
		return (String) bug.getParameterMap().get(urlString);
	}
	
	private static String cloneOfString = "cf_clone_of";
	public String getCloneOf(Bug bug) {
		return (String) bug.getParameterMap().get(cloneOfString);
	}
	
	private static String assignedToString = "assigned_to";
	public String getAssignedTo(Bug bug) {
		return (String) bug.getParameterMap().get(assignedToString);
	}
	
	private static String lastClosedString = "cf_last_closed";
	public Date getLastClosed(Bug bug) {
		return (Date) bug.getParameterMap().get(lastClosedString);
	}
	
	private static String whiteBoardString = "whiteboard";
	public String getWhiteBoard(Bug bug) {
		return (String) bug.getParameterMap().get(whiteBoardString);
	}
	
	private static String regressionStatusString = "cf_regression_status";
	public String getRegressionStatus(Bug bug) {
		return (String) bug.getParameterMap().get(regressionStatusString);
	}
	
	private static String classificationString = "classification";
	public String getClassification(Bug bug) {
		return (String) bug.getParameterMap().get(classificationString);
	}
	
	private static String typeString = "cf_type";
	public String getType(Bug bug) {
		return (String) bug.getParameterMap().get(typeString);
	}
	
}
