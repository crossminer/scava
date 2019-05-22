package org.eclipse.scava.platform.documentation.github;

import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.delta.vcs.VcsRepositoryDelta;
import org.eclipse.scava.platform.vcs.git.GitManager;
import org.eclipse.scava.repository.model.VcsRepository;
import org.eclipse.scava.repository.model.github.GitHubWiki;

public class GithubDocumentationManager extends GitManager {

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
			return super.getRevisionsForDate(repository, date);
		return new String[0];
	}
	

}
