package org.eclipse.scava.metricprovider.trans.plaintextprocessing;

import java.util.Collections;
import java.util.List;

import org.eclipse.scava.metricprovider.trans.plaintextprocessing.model.BugTrackerCommentPlainTextProcessing;
import org.eclipse.scava.metricprovider.trans.plaintextprocessing.model.ForumPostPlainTextProcessing;
import org.eclipse.scava.metricprovider.trans.plaintextprocessing.model.NewsgroupArticlePlainTextProcessing;
import org.eclipse.scava.metricprovider.trans.plaintextprocessing.model.PlainTextProcessingTransMetric;
import org.eclipse.scava.nlp.tools.plaintext.PlainTextObject;
import org.eclipse.scava.nlp.tools.plaintext.bugtrackers.PlainTextBugTrackersOthers;
import org.eclipse.scava.nlp.tools.plaintext.bugtrackers.PlainTextBugzilla;
import org.eclipse.scava.nlp.tools.plaintext.bugtrackers.PlainTextGitHub;
import org.eclipse.scava.nlp.tools.plaintext.communicationchannels.PlainTextEclipseForums;
import org.eclipse.scava.nlp.tools.plaintext.communicationchannels.PlainTextNewsgroups;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemComment;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemProjectDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelArticle;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelForumPost;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelProjectDelta;
import org.eclipse.scava.platform.delta.communicationchannel.PlatformCommunicationChannelManager;
import org.eclipse.scava.repository.model.BugTrackingSystem;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.cc.eclipseforums.EclipseForum;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.scava.repository.model.sourceforge.Discussion;

import com.mongodb.DB;

public class PlainTextProcessingTransMetricProvider implements ITransientMetricProvider<PlainTextProcessingTransMetric> {

	protected PlatformBugTrackingSystemManager platformBugTrackingSystemManager;
	protected PlatformCommunicationChannelManager communicationChannelManager;
	
	@Override
	public String getIdentifier() {
		return PlainTextProcessingTransMetricProvider.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "plaintextprocessing";
	}

	@Override
	public String getFriendlyName() {
		return "Plain Text Processing";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric preprocess each bug comment or newsgroup article into a split " +
				"plain text format.";
	}

	@Override
	public boolean appliesTo(Project project) {
		for (CommunicationChannel communicationChannel: project.getCommunicationChannels()) {
			if (communicationChannel instanceof NntpNewsGroup) return true;
			if (communicationChannel instanceof Discussion) return true;
			if (communicationChannel instanceof EclipseForum) return true;
		}
		return !project.getBugTrackingSystems().isEmpty();
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		// DO NOTHING -- we don't use anything
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Collections.emptyList();
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.platformBugTrackingSystemManager = context.getPlatformBugTrackingSystemManager();
		this.communicationChannelManager = context.getPlatformCommunicationChannelManager();
	}

	@Override
	public PlainTextProcessingTransMetric adapt(DB db) {
		return new PlainTextProcessingTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, PlainTextProcessingTransMetric db) {

		clearDB(db);
		System.err.println("Started " + getIdentifier());
		
		PlainTextObject plainTextObject;
		
		BugTrackingSystemProjectDelta btspDelta = projectDelta.getBugTrackingSystemDelta();
		
		for (BugTrackingSystemDelta bugTrackingSystemDelta : btspDelta.getBugTrackingSystemDeltas()) {
			
			BugTrackingSystem bugTracker = bugTrackingSystemDelta.getBugTrackingSystem();
			
			for (BugTrackingSystemComment comment: bugTrackingSystemDelta.getComments()) {
				
				BugTrackerCommentPlainTextProcessing commentsData = findBugTrackerComment(db, comment);
				
				if (commentsData == null) {
					commentsData = new BugTrackerCommentPlainTextProcessing();
					commentsData.setBugTrackerId(bugTracker.getOSSMeterId());
					commentsData.setBugId(comment.getBugId());
					commentsData.setCommentId(comment.getCommentId());
					db.getBugTrackerComments().add(commentsData);
				}
				
				
				switch(bugTracker.getBugTrackerType())
				{
					//case "bitbucket" might be the same as GitHub
					case "github": plainTextObject=PlainTextGitHub.process(comment.getText()); break;
					case "bugzilla": plainTextObject=PlainTextBugzilla.process(comment.getText()); break;
					//case "jira":
					//case "redmine":
					//case "mantis":
					default: plainTextObject=PlainTextBugTrackersOthers.process(comment.getText()); break;
				}
				commentsData.setPlainText(plainTextObject.getPlainTextAsList());
				commentsData.setHadReplies(plainTextObject.hadReplies());
			}
			db.sync();
		}
		
		CommunicationChannelProjectDelta ccpDelta = projectDelta.getCommunicationChannelDelta();
		for ( CommunicationChannelDelta communicationChannelDelta: ccpDelta.getCommunicationChannelSystemDeltas()) {
			CommunicationChannel communicationChannel = communicationChannelDelta.getCommunicationChannel();
			//Process for forums
			if(communicationChannel instanceof EclipseForum)
			{
				for(CommunicationChannelForumPost post : communicationChannelDelta.getPosts())
				{
					ForumPostPlainTextProcessing forumPostsData = findForumPost(db, post);
					if(forumPostsData == null)
					{
						forumPostsData = new ForumPostPlainTextProcessing();
						forumPostsData.setForumId(communicationChannel.getOSSMeterId());
						forumPostsData.setTopicId(post.getTopicId());
						forumPostsData.setPostId(post.getPostId());
						db.getForumPosts().add(forumPostsData);
					}
					plainTextObject = PlainTextEclipseForums.process(post.getText());
					forumPostsData.setPlainText(plainTextObject.getPlainTextAsList());
					forumPostsData.setHadReplies(plainTextObject.hadReplies());
				}
			}
			else
			{
				String communicationChannelName;
				if (!(communicationChannel instanceof NntpNewsGroup))
					communicationChannelName = communicationChannel.getUrl();
				else {
					NntpNewsGroup newsgroup = (NntpNewsGroup) communicationChannel;
					communicationChannelName = newsgroup.getNewsGroupName();
				}
				for (CommunicationChannelArticle article: communicationChannelDelta.getArticles()) {
					NewsgroupArticlePlainTextProcessing newsgroupArticlesData = 
							findNewsgroupArticle(db, article);
					if (newsgroupArticlesData == null) {
						newsgroupArticlesData = new NewsgroupArticlePlainTextProcessing();
						newsgroupArticlesData.setNewsGroupName(communicationChannelName);
						newsgroupArticlesData.setArticleNumber(article.getArticleNumber());
						db.getNewsgroupArticles().add(newsgroupArticlesData);
					}
					plainTextObject = PlainTextNewsgroups.process(article.getText());
					newsgroupArticlesData.setPlainText(plainTextObject.getPlainTextAsList());
					newsgroupArticlesData.setHadReplies(plainTextObject.hadReplies());
				}
			}
			db.sync();
		}
		
	}
	
	private void clearDB(PlainTextProcessingTransMetric db) {
		db.getBugTrackerComments().getDbCollection().drop();
		db.getNewsgroupArticles().getDbCollection().drop();
		db.getForumPosts().getDbCollection().drop();
		db.sync();
	}

	private BugTrackerCommentPlainTextProcessing findBugTrackerComment(PlainTextProcessingTransMetric db, BugTrackingSystemComment comment) {
		BugTrackerCommentPlainTextProcessing bugTrackerCommentsData = null;
		Iterable<BugTrackerCommentPlainTextProcessing> bugTrackerCommentsDataIt = 
				db.getBugTrackerComments().
						find(BugTrackerCommentPlainTextProcessing.BUGTRACKERID.eq(comment.getBugTrackingSystem().getOSSMeterId()), 
								BugTrackerCommentPlainTextProcessing.BUGID.eq(comment.getBugId()),
								BugTrackerCommentPlainTextProcessing.COMMENTID.eq(comment.getCommentId()));
		for (BugTrackerCommentPlainTextProcessing bcd:  bugTrackerCommentsDataIt) {
			bugTrackerCommentsData = bcd;
		}
		return bugTrackerCommentsData;
	}
	

	private NewsgroupArticlePlainTextProcessing findNewsgroupArticle(PlainTextProcessingTransMetric db, CommunicationChannelArticle article) {
		NewsgroupArticlePlainTextProcessing newsgroupArticlesData = null;
		Iterable<NewsgroupArticlePlainTextProcessing> newsgroupArticlesDataIt = 
				db.getNewsgroupArticles().
						find(NewsgroupArticlePlainTextProcessing.NEWSGROUPNAME.eq(article.getCommunicationChannel().getNewsGroupName()), 
								NewsgroupArticlePlainTextProcessing.ARTICLENUMBER.eq(article.getArticleNumber()));
		for (NewsgroupArticlePlainTextProcessing nad:  newsgroupArticlesDataIt) {
			newsgroupArticlesData = nad;
		}
		return newsgroupArticlesData;
	}
	
	private ForumPostPlainTextProcessing findForumPost(PlainTextProcessingTransMetric db, CommunicationChannelForumPost post) {
		ForumPostPlainTextProcessing forumPostsData = null;
		Iterable<ForumPostPlainTextProcessing> forumPostsDataIt = 
		db.getForumPosts().
				find(ForumPostPlainTextProcessing.FORUMID.eq(post.getCommunicationChannel().getOSSMeterId()),
						ForumPostPlainTextProcessing.TOPICID.eq(post.getTopicId()), 
						ForumPostPlainTextProcessing.POSTID.eq(post.getPostId()));
		for (ForumPostPlainTextProcessing fpd:  forumPostsDataIt) {
			forumPostsData = fpd;
		}
		return forumPostsData;
	}
}
