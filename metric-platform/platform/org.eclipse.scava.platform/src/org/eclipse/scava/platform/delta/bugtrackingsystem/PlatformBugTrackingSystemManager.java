/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.delta.bugtrackingsystem;

import java.util.List;

import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.cache.bugtrackingsystem.BugTrackingSystemContentsCache;
import org.eclipse.scava.platform.cache.bugtrackingsystem.BugTrackingSystemDeltaCache;
import org.eclipse.scava.platform.cache.bugtrackingsystem.IBugTrackingSystemContentsCache;
import org.eclipse.scava.platform.cache.bugtrackingsystem.IBugTrackingSystemDeltaCache;
import org.eclipse.scava.platform.delta.NoManagerFoundException;
import org.eclipse.scava.repository.model.BugTrackingSystem;
import org.eclipse.scava.repository.model.ManagerAnalysis;

import com.mongodb.DB;

public abstract class PlatformBugTrackingSystemManager implements IBugTrackingSystemManager<BugTrackingSystem> {
	
	protected final Platform platform;
	protected List<IBugTrackingSystemManager> bugTrackingSystemManagers;
	protected IBugTrackingSystemDeltaCache deltaCache;
	protected IBugTrackingSystemContentsCache contentsCache;
	
	abstract public List<IBugTrackingSystemManager> getBugTrackingSystemManagers();
	
	public PlatformBugTrackingSystemManager(Platform platform) {
		this.platform = platform;
	}
	
	@Override
	public boolean appliesTo(BugTrackingSystem bugTrackingSystem) {
		try {
			return getBugTrackingSystemManager(bugTrackingSystem) != null;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	protected IBugTrackingSystemManager getBugTrackingSystemManager(BugTrackingSystem bugTrackingSystem) throws Exception {
		for (IBugTrackingSystemManager bugTrackingSystemManager : getBugTrackingSystemManagers()) {
			if (bugTrackingSystemManager.appliesTo(bugTrackingSystem)) {
				return bugTrackingSystemManager;
			}
		}
		throw new NoManagerFoundException("No bug tracking system manager found for " + bugTrackingSystem);
	}
	
	@Override
	public Date getFirstDate(DB db, BugTrackingSystem bugTrackingSystem)
			throws Exception {
		IBugTrackingSystemManager bugTrackingSystemManager = getBugTrackingSystemManager(bugTrackingSystem);
		if (bugTrackingSystemManager != null) {
			ManagerAnalysis mAnal = ManagerAnalysis.create(bugTrackingSystemManager.toString(), 
					"getFirstDate",
					bugTrackingSystem.getUrl(),
					null,
					new java.util.Date());
			platform.getProjectRepositoryManager().getProjectRepository().getManagerAnalysis().add(mAnal);
			long start = System.currentTimeMillis();
			
			Date firstDate = bugTrackingSystemManager.getFirstDate(db, bugTrackingSystem);
			
			mAnal.setMillisTaken(System.currentTimeMillis() - start);
			platform.getProjectRepositoryManager().getProjectRepository().getManagerAnalysis().sync();

			return firstDate;
		}
		
		return null;
	}
	
	@Override
	public BugTrackingSystemDelta getDelta(DB db, BugTrackingSystem bugTrackingSystem, Date date)  throws Exception {
		BugTrackingSystemDelta cache = getDeltaCache().getCachedDelta(bugTrackingSystem.getUrl(), date);
		if (cache != null) {
			System.err.println("BugTrackingSystemBug CACHE HIT!");
			return cache;
		}
		
		IBugTrackingSystemManager bugTrackingSystemManager = getBugTrackingSystemManager(bugTrackingSystem);
		if (bugTrackingSystemManager != null) {
			ManagerAnalysis mAnal = ManagerAnalysis.create(bugTrackingSystemManager.toString(), 
					"getDelta",
					bugTrackingSystem.getUrl(),
					date.toJavaDate(),
					new java.util.Date());
			platform.getProjectRepositoryManager().getProjectRepository().getManagerAnalysis().add(mAnal);
			long start = System.currentTimeMillis();
			
			BugTrackingSystemDelta delta = bugTrackingSystemManager.getDelta(db, bugTrackingSystem, date);
			
			mAnal.setMillisTaken(System.currentTimeMillis() - start);
			platform.getProjectRepositoryManager().getProjectRepository().getManagerAnalysis().sync();
			//RESTMULE manages internally cache
			if(!bugTrackingSystemManager.isRestmule())
				getDeltaCache().putDelta(bugTrackingSystem.getUrl(), date, delta);
			return delta;
		}
		return null;
	}

	@Override
	public String getContents(DB db, BugTrackingSystem bugTrackingSystem, BugTrackingSystemBug bug) throws Exception {
		String cache = getContentsCache().getCachedContents(bug);
		if (cache != null) {
			System.err.println("BugTrackingSystemBug CACHE HIT!");
			return cache;
		}

		IBugTrackingSystemManager bugTrackingSystemManager =
		getBugTrackingSystemManager((bug.getBugTrackingSystem()));
		
		if (bugTrackingSystemManager != null) {
			String contents = bugTrackingSystemManager.getContents(db, bugTrackingSystem, bug);
			getContentsCache().putContents(bug, contents);
			return contents;
		}
		return null;
	}
	
	@Override
	public String getContents(DB db, BugTrackingSystem bugTrackingSystem, BugTrackingSystemComment comment) throws Exception {
		String cache = getContentsCache().getCachedContents(comment);
		if (cache != null) {
			System.err.println("BugTrackingSystemBug CACHE HIT!");
			return cache;
		}

		IBugTrackingSystemManager bugTrackingSystemManager =
									getBugTrackingSystemManager((comment.getBugTrackingSystem()));
		
		if (bugTrackingSystemManager != null) {
			String contents = bugTrackingSystemManager.getContents(db, bugTrackingSystem, comment);
			getContentsCache().putContents(comment, contents);
			return contents;
		}
		return null;
	}
	
	public IBugTrackingSystemContentsCache getContentsCache() {
		if (contentsCache == null) {
			contentsCache = new BugTrackingSystemContentsCache();
		}
		return contentsCache;
	}
	
	public IBugTrackingSystemDeltaCache getDeltaCache() {
		if (deltaCache == null) {
			deltaCache = new BugTrackingSystemDeltaCache();
		}
		return deltaCache;		
	}
	
	@Override
	public boolean isRestmule() {
		return false;
	}

}
