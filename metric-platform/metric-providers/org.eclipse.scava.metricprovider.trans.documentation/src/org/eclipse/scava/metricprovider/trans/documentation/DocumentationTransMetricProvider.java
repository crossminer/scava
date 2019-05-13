package org.eclipse.scava.metricprovider.trans.documentation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.eclipse.scava.metricprovider.trans.documentation.model.DocumentationTransMetric;
import com.mongodb.DB;

import org.eclipse.scava.platform.Constants;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.vcs.PlatformVcsManager;
import org.eclipse.scava.platform.delta.vcs.VcsCommit;
import org.eclipse.scava.platform.delta.vcs.VcsCommitItem;
import org.eclipse.scava.platform.delta.vcs.VcsProjectDelta;
import org.eclipse.scava.platform.delta.vcs.VcsRepositoryDelta;
import org.eclipse.scava.platform.vcs.workingcopy.manager.WorkingCopyCheckoutException;
import org.eclipse.scava.platform.vcs.workingcopy.manager.WorkingCopyFactory;
import org.eclipse.scava.platform.vcs.workingcopy.manager.WorkingCopyManagerUnavailable;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.VcsRepository;
import org.eclipse.scava.repository.model.github.GitHubWiki;

public class DocumentationTransMetricProvider implements ITransientMetricProvider<DocumentationTransMetric> {

	protected PlatformVcsManager platformVcsManager;
	
	@Override
	public String getIdentifier() {
		return DocumentationTransMetricProvider.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFriendlyName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSummaryInformation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean appliesTo(Project project) {
		for(VcsRepository repository : project.getVcsRepositories())
			if(repository instanceof GitHubWiki) return true;
		return false;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		
		
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Collections.emptyList();
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.platformVcsManager=context.getPlatformVcsManager();
		
	}

	@Override
	public DocumentationTransMetric adapt(DB db) {
		return new DocumentationTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, DocumentationTransMetric db) {


		
		VcsProjectDelta vcsProjectDelta = projectDelta.getVcsDelta();
		for(VcsRepositoryDelta vcsDelta : vcsProjectDelta.getRepoDeltas())
		{
			for(VcsCommit commit : vcsDelta.getCommits())
			{
				String revision = commit.getRevision();
				Map<String, File> workingCopyFolders = new HashMap<String, File>();				
				Map<String, File> scratchFolders = new HashMap<String, File>();
				
				try {
					WorkingCopyFactory.getInstance().checkout(project, revision, workingCopyFolders, scratchFolders);
				} catch (WorkingCopyManagerUnavailable | WorkingCopyCheckoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(VcsCommitItem item : commit.getItems())
				{
					File file = new File(workingCopyFolders.get(vcsDelta.getRepository().getUrl())+"/"+item.getPath());
					String contents ="";
					Scanner scanner;
					try {
						scanner = new Scanner(file);
						while(scanner.hasNext()) {
							contents += scanner.nextLine() + Constants.NEW_LINE; 
						}
						scanner.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		}
		
	}

}
