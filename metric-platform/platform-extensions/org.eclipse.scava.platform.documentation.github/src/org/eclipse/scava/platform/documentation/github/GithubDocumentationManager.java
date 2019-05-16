package org.eclipse.scava.platform.documentation.github;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.delta.vcs.VcsRepositoryDelta;
import org.eclipse.scava.platform.vcs.git.GitManager;
import org.eclipse.scava.repository.model.VcsRepository;
import org.eclipse.scava.repository.model.github.GitHubWiki;
import org.eclipse.scava.repository.model.vcs.git.GitRepository;

public class GithubDocumentationManager extends GitManager {

	Set<VcsRepository> firstCommitSeen = new HashSet<VcsRepository>();
	
	
	@Override
	public boolean appliesTo(VcsRepository repository) {
		return repository instanceof GitHubWiki;
	}
	
	@Override
	public VcsRepositoryDelta getDelta(VcsRepository repository, String startRevision, String endRevision) throws Exception {
		if(super.validRepository(repository))
			return super.getDelta(repository, startRevision, endRevision);
		VcsRepositoryDelta vcsDelta = new VcsRepositoryDelta();
		vcsDelta.setRepository(repository);
		return vcsDelta;
	}
	
	@Override
	public String[] getRevisionsForDate(VcsRepository repository, Date date) throws Exception {
		if(super.validRepository(repository))
		{
			String revisionBeforeToday;
			List<String> revisions = new ArrayList<String>();
			revisionBeforeToday=getLastRevisionBeforeDate(repository, date);
			if(!revisionBeforeToday.isEmpty())
				revisions.add(revisionBeforeToday);
			String[] revisionsOfToday =super.getRevisionsForDate(repository, date);
			for(String revision : revisionsOfToday)
			{
				revisions.add(revision);
			}
			return revisions.toArray(new String[revisions.size()]);
		}
		return new String[0];
	}
	
	private String getLastRevisionBeforeDate(VcsRepository repository, Date date) throws Exception
	{
		Git git = getGit((GitRepository)repository);
		
		Repository repo = git.getRepository();
		RevWalk walk = new RevWalk(repo);
		
		Iterator<RevCommit> iterator = git.log().call().iterator();
		
		String revision="";
		// The commits are ordered latest first, so we want to find the fist that it is before the date
		int dateComparison;
		while(iterator.hasNext()) 
		{
			RevCommit commit = walk.parseCommit(iterator.next());
			dateComparison=new Date(Long.valueOf(commit.getCommitTime())*1000).compareTo(date);
			if (dateComparison < 0) {
				revision=commit.getId().getName();
				break;
			}
		}
		
		repo.close();
		git.close();
		
		if(revision.isEmpty())
			return "";
		return revision;
	}
	

}
