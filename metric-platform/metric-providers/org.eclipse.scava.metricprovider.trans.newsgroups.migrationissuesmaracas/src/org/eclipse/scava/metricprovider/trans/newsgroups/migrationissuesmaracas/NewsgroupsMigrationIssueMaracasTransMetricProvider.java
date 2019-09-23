package org.eclipse.scava.metricprovider.trans.newsgroups.migrationissuesmaracas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.scava.metricprovider.trans.indexing.preparation.IndexPreparationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.indexing.preparation.model.IndexPrepTransMetric;
import org.eclipse.scava.metricprovider.trans.migrationissuesmaracas.MigrationIssueMaracasTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.migrationissuesmaracas.model.MaracasMeasurement;
import org.eclipse.scava.metricprovider.trans.migrationissuesmaracas.model.MigrationIssueMaracasTransMetric;
import org.eclipse.scava.metricprovider.trans.newsgroups.migrationissues.NewsgroupsMigrationIssueTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.newsgroups.migrationissues.model.NewsgroupsMigrationIssue;
import org.eclipse.scava.metricprovider.trans.newsgroups.migrationissues.model.NewsgroupsMigrationIssueTransMetric;
import org.eclipse.scava.metricprovider.trans.newsgroups.migrationissuesmaracas.model.NewsgroupMigrationIssueMaracas;
import org.eclipse.scava.metricprovider.trans.newsgroups.migrationissuesmaracas.model.NewsgroupsMigrationIssueMaracasTransMetric;
import org.eclipse.scava.metricprovider.trans.plaintextprocessing.PlainTextProcessingTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.plaintextprocessing.model.NewsgroupArticlePlainTextProcessing;
import org.eclipse.scava.metricprovider.trans.plaintextprocessing.model.PlainTextProcessingTransMetric;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.cc.eclipseforums.EclipseForum;
import org.eclipse.scava.repository.model.cc.irc.Irc;
import org.eclipse.scava.repository.model.cc.mbox.Mbox;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.scava.repository.model.cc.sympa.SympaMailingList;
import org.eclipse.scava.repository.model.sourceforge.Discussion;

import com.mongodb.DB;

public class NewsgroupsMigrationIssueMaracasTransMetricProvider implements ITransientMetricProvider<NewsgroupsMigrationIssueMaracasTransMetric> {

	protected PlatformBugTrackingSystemManager bugTrackingSystemManager;
	
	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	@Override
	public String getIdentifier() {
		return NewsgroupsMigrationIssueMaracasTransMetricProvider.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "trans.newsgroups.migrationissuesmaracas";
	}

	@Override
	public String getFriendlyName() {
		return "Migration Issues Detection Maracas in Newsgroups";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric detects migration issues in Newsgroups along with data from Maracas.";
	}

	@Override
	public boolean appliesTo(Project project) {
		for (CommunicationChannel communicationChannel : project.getCommunicationChannels()) {
			if (communicationChannel instanceof NntpNewsGroup) return true;
			if (communicationChannel instanceof Discussion) return true;
			if (communicationChannel instanceof EclipseForum) return true;
			if (communicationChannel instanceof SympaMailingList) return true;
			if (communicationChannel instanceof Irc) return true;
			if (communicationChannel instanceof Mbox) return true;
		}
		return false;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(IndexPreparationTransMetricProvider.class.getCanonicalName(),
				NewsgroupsMigrationIssueTransMetricProvider.class.getCanonicalName(),
				MigrationIssueMaracasTransMetricProvider.class.getCanonicalName(),
				PlainTextProcessingTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.bugTrackingSystemManager = context.getPlatformBugTrackingSystemManager();
		this.context = context;
	}

	@Override
	public NewsgroupsMigrationIssueMaracasTransMetric adapt(DB db) {
		return new NewsgroupsMigrationIssueMaracasTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta delta, NewsgroupsMigrationIssueMaracasTransMetric db) {
		
		// This is for indexing
		IndexPrepTransMetric indexPrepTransMetric = ((IndexPreparationTransMetricProvider) uses.get(0)).adapt(context.getProjectDB(project));
		indexPrepTransMetric.getExecutedMetricProviders().first().getMetricIdentifiers().add(getIdentifier());
		indexPrepTransMetric.sync();
		
		NewsgroupsMigrationIssueTransMetric migrationMetric = ((NewsgroupsMigrationIssueTransMetricProvider) uses.get(1)).adapt(context.getProjectDB(project));
		
		if(migrationMetric.getNewsgroupsMigrationIssues().size()>0)
		{
			
			MigrationIssueMaracasTransMetric maracasMetric = ((MigrationIssueMaracasTransMetricProvider) uses.get(2)).adapt(context.getProjectDB(project));
			
			List<MaracasRegexData> maracasRegexDataCollection = compileRegexMaracas(maracasMetric, Integer.valueOf(delta.getDate().toString()));
			if(maracasRegexDataCollection.size()>0)
			{
				String textToBeSearched;
				MatchedMaracasText matchedMaracasText;
				PlainTextProcessingTransMetric plaintextMetric = ((PlainTextProcessingTransMetricProvider) uses.get(3)).adapt(context.getProjectDB(project));
				
				for(NewsgroupsMigrationIssue migrationIssue : migrationMetric.getNewsgroupsMigrationIssues())
				{	
					textToBeSearched=migrationIssue.getSummary();
					
					textToBeSearched+=findPlainText(plaintextMetric, migrationIssue.getNewsgroupName(), migrationIssue.getArticleId());
					
					matchedMaracasText = searchTextForIssues(maracasRegexDataCollection, textToBeSearched);
					if(matchedMaracasText.getChanges().size()>0)
					{
						NewsgroupMigrationIssueMaracas migrationIssueMaracas = findBugTrackerCommentMaracas(db, migrationIssue.getNewsgroupName(), migrationIssue.getThreadId());
						if(migrationIssueMaracas==null)
						{
							migrationIssueMaracas = new NewsgroupMigrationIssueMaracas();
							migrationIssueMaracas.setNewsgroupName(migrationIssue.getNewsgroupName());
							migrationIssueMaracas.setThreadId(migrationIssue.getThreadId());
							for(String change : matchedMaracasText.getChanges())
								migrationIssueMaracas.getChanges().add(change);
							for(Double percentage : matchedMaracasText.getMatchesPercentage())
								migrationIssueMaracas.getMatchingPercentage().add(percentage);
							db.getNewsgroupsMigrationIssuesMaracas().add(migrationIssueMaracas);
						}
						else
						{
							List<String> changes = matchedMaracasText.getChanges();
							List<Double> changesPercentages = matchedMaracasText.getMatchesPercentage();
							for(int i=0; i<changes.size(); i++)
							{
								String chagedElement=changes.get(i);
								if(!migrationIssueMaracas.getChanges().contains(chagedElement))
								{
									migrationIssueMaracas.getChanges().add(chagedElement);
									migrationIssueMaracas.getMatchingPercentage().add(changesPercentages.get(i));
								}
								else
								{
									for(int j=0; j<migrationIssueMaracas.getChanges().size(); j++)
									{
										if(migrationIssueMaracas.getChanges().get(j).equals(chagedElement))
										{
											if(migrationIssueMaracas.getMatchingPercentage().get(j)>changesPercentages.get(i))
											{
												migrationIssueMaracas.getMatchingPercentage().remove(j);
												migrationIssueMaracas.getChanges().remove(j);
												migrationIssueMaracas.getChanges().add(chagedElement);
												migrationIssueMaracas.getMatchingPercentage().add(changesPercentages.get(i));
											}
											break;
										}
									}
								}	
							}
						}
						db.sync();
					}
				}
			}
		}
		
		
	}
	
	private MatchedMaracasText searchTextForIssues(List<MaracasRegexData> maracasRegexDataCollection, String textToBeSearched)
	{
		Matcher m;
		Double counterMatches=0.0;
		Double counterPatterns;
		MatchedMaracasText matchedMaracasText = new MatchedMaracasText();
		for(MaracasRegexData maracasRegexData : maracasRegexDataCollection)
		{
			counterMatches=0.0;
			counterPatterns=0.0;
			for(Pattern pattern : maracasRegexData.getPatterns())
			{
				m=pattern.matcher(textToBeSearched);
				if(m.find())
				{
					counterMatches++;
				}
				counterPatterns++;
			}
			counterMatches/=counterPatterns;
			if(counterMatches>0)
				matchedMaracasText.addChange(maracasRegexData.getChange(), counterMatches);
		}
		return matchedMaracasText;
	}
	
	private class MatchedMaracasText {
		private List<String> changes;
		private List<Double> matchesPercentage;
		
		public MatchedMaracasText() {
			changes= new ArrayList<String>();
			matchesPercentage= new ArrayList<Double>();
		}
		
		public void addChange(String change, Double matchPercentage)
		{
			changes.add(change);
			matchesPercentage.add(matchPercentage);
		}
		
		public List<String> getChanges() {
			return changes;
		}
		
		public List<Double> getMatchesPercentage() {
			return matchesPercentage;
		}

	}
	
	private String findPlainText(PlainTextProcessingTransMetric db, String newsgroupName, long articleId) {
		String text="";
		Iterable<NewsgroupArticlePlainTextProcessing> issuesIt = db.getNewsgroupArticles().find(
				NewsgroupArticlePlainTextProcessing.NEWSGROUPNAME.eq(newsgroupName),
				NewsgroupArticlePlainTextProcessing.ARTICLENUMBER.eq(articleId));
		for (NewsgroupArticlePlainTextProcessing btmi : issuesIt) {
			text+=String.join("\n", btmi.getPlainText())+"\n";
		}
		return text;
	}
	
	private List<MaracasRegexData> compileRegexMaracas(MigrationIssueMaracasTransMetric db, int date) {
		List<MaracasRegexData> maracasRegexDataCollection = new ArrayList<MaracasRegexData>();
		Iterable<MaracasMeasurement> maracasRegexDataIT = db.getMaracasMeasurements().find(
				MaracasMeasurement.LASTUPDATEDATE.lessThanOrEqualTo(date));
		for(MaracasMeasurement mm : maracasRegexDataIT)
		{
			List<Pattern> patterns = new ArrayList<Pattern>();
			for(String regex : mm.getRegex())
			{
				patterns.add(Pattern.compile(regex));
			}
			MaracasRegexData maracasRegexData  = new MaracasRegexData(patterns, mm.getChange());
			maracasRegexDataCollection.add(maracasRegexData);
		}
		return maracasRegexDataCollection;
	}
	
	private class MaracasRegexData
	{
		private List<Pattern> patterns;
		private String change;
		
		public MaracasRegexData(List<Pattern> patterns, String change) {
			this.patterns=patterns;
			this.change=change;
		}
		
		public String getChange() {
			return change;
		}
		
		public List<Pattern> getPatterns() {
			return patterns;
		}
	}

		
	private NewsgroupMigrationIssueMaracas findBugTrackerCommentMaracas(NewsgroupsMigrationIssueMaracasTransMetric db, String newsgroupName, int threadId) {
		NewsgroupMigrationIssueMaracas bugTrackerIssues = null;
		Iterable<NewsgroupMigrationIssueMaracas> issuesIt = db.getNewsgroupsMigrationIssuesMaracas().find(
				NewsgroupMigrationIssueMaracas.NEWSGROUPNAME.eq(newsgroupName),
				NewsgroupMigrationIssueMaracas.THREADID.eq(threadId));
		for (NewsgroupMigrationIssueMaracas btmi : issuesIt) {
			bugTrackerIssues = btmi;
		}
		return bugTrackerIssues;
	}

}
