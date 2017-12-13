/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.crossmeter.platform.delta.vcs;

import java.util.List;

import org.eclipse.crossmeter.platform.Date;
import org.eclipse.crossmeter.platform.Platform;
import org.eclipse.crossmeter.platform.cache.vcs.IVcsContentsCache;
import org.eclipse.crossmeter.platform.cache.vcs.IVcsDeltaCache;
import org.eclipse.crossmeter.platform.cache.vcs.VcsContentsCache;
import org.eclipse.crossmeter.platform.cache.vcs.VcsDeltaCache;
import org.eclipse.crossmeter.platform.delta.NoManagerFoundException;
import org.eclipse.crossmeter.repository.model.ManagerAnalysis;
import org.eclipse.crossmeter.repository.model.VcsRepository;

public abstract class PlatformVcsManager extends AbstractVcsManager {
	
	protected final Platform platform;
	protected List<IVcsManager> vcsManagers;
	protected IVcsDeltaCache deltaCache;
	protected IVcsContentsCache contentsCache;
	
	abstract public List<IVcsManager> getVcsManagers();
	
	public PlatformVcsManager (Platform platform) {
		this.platform = platform;
	}

	@Override
	public String getCurrentRevision(VcsRepository repository) throws Exception {
		IVcsManager manager = getVcsManager(repository);
		if (manager != null) {
			return manager.getCurrentRevision(repository);
		}
		return null;
	}
	
	@Override
	public String getFirstRevision(VcsRepository repository) throws Exception {
		IVcsManager manager = getVcsManager(repository);
		if (manager != null) {
			return manager.getFirstRevision(repository);
		}
		return null;
	}
	
	@Override
	public int compareVersions(VcsRepository repository, String versionOne, String versionTwo) throws Exception {
		IVcsManager manager = getVcsManager(repository);
		if (manager != null) {
			return manager.compareVersions(repository, versionOne, versionTwo);
		}
		return -1; // TODO: fair?
	}

	@Override
	public String[] getRevisionsForDate(VcsRepository repository, Date date) throws Exception {
		IVcsManager manager = getVcsManager(repository);
		if (manager != null) {

			ManagerAnalysis mAnal = ManagerAnalysis.create(manager.toString(),
				"getRevisionsForDate",
				repository.getUrl(),
				date.toJavaDate(),
				new java.util.Date());
			platform.getProjectRepositoryManager().getProjectRepository().getManagerAnalysis().add(mAnal);
			long start = System.currentTimeMillis();
			
			String[] revisions = manager.getRevisionsForDate(repository, date);
			
			mAnal.setMillisTaken(System.currentTimeMillis() - start);
			platform.getProjectRepositoryManager().getProjectRepository().getManagerAnalysis().sync();
			
			return revisions;
		}
		return null;
	}

	@Override
	public Date getDateForRevision(VcsRepository repository, String revision) throws Exception {
		IVcsManager manager = getVcsManager(repository);
		if (manager != null) {
			return manager.getDateForRevision(repository, revision);
		}
		throw new NoManagerFoundException("Manager for repository: " + repository + " not found.");
	}
	
	@Override
	public boolean appliesTo(VcsRepository repository) {
		try {
			return getVcsManager(repository) != null;
		} catch (NoManagerFoundException e) {
			e.printStackTrace(); //FIXME
			return false;
		}
	}

	protected IVcsManager getVcsManager(VcsRepository repository) throws NoManagerFoundException {
		for (IVcsManager vcsManager : getVcsManagers()) {
			if (vcsManager.appliesTo(repository)) {
				return vcsManager;
			}
		}
		return null;
//		throw new NoManagerFoundException("No vcs manager found for repository " + repository);
	}
	
	@Override
	public VcsRepositoryDelta getDelta(VcsRepository repository, String startVersion, String endVersion)  throws Exception {
		VcsRepositoryDelta cache = getDeltaCache().getCachedDelta(repository, startVersion, endVersion);
		if (cache != null) {
			return cache;
		}
		
		IVcsManager vcsManager = getVcsManager(repository);
		if (vcsManager != null) {
			ManagerAnalysis mAnal = ManagerAnalysis.create(vcsManager.toString(), 
					"getDelta " + startVersion + ":" + endVersion,
					repository.getUrl(),
					null,
					new java.util.Date());
			platform.getProjectRepositoryManager().getProjectRepository().getManagerAnalysis().add(mAnal);
			
			long start = System.currentTimeMillis();
			
			VcsRepositoryDelta delta = vcsManager.getDelta(repository, startVersion, endVersion);
			getDeltaCache().putDelta(repository, startVersion, endVersion, delta);
			
			mAnal.setMillisTaken(System.currentTimeMillis() - start);
			platform.getProjectRepositoryManager().getProjectRepository().getManagerAnalysis().sync();
			
			return delta;
		}
		return null;
	}

	@Override
	public String getContents(VcsCommitItem item) throws Exception {
		String cache = getContentsCache().getCachedContents(item);
		if (cache != null) {
			return cache;
		}
		
		IVcsManager vcsManager = getVcsManager(item.getCommit().getDelta().getRepository());
		if (vcsManager != null) {
			String contents =  vcsManager.getContents(item);
			getContentsCache().putContents(item, contents);
			return contents;
		}
		return null;
	}
	
	public IVcsContentsCache getContentsCache() {
		if (contentsCache == null) {
			contentsCache = new VcsContentsCache();
		}
		return contentsCache;
	}
	
	public IVcsDeltaCache getDeltaCache() {
		if (deltaCache == null) {
			deltaCache = new VcsDeltaCache();
		}
		return deltaCache;		
	}
	
	@Override
	public boolean validRepository(VcsRepository repository) throws Exception {
		IVcsManager vcsManager = getVcsManager(repository);
		if (vcsManager != null) {
			return vcsManager.validRepository(repository);
		}
		return false;
	}
}
