/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.vcs.svn;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.delta.vcs.AbstractVcsManager;
import org.eclipse.scava.platform.delta.vcs.VcsChangeType;
import org.eclipse.scava.platform.delta.vcs.VcsCommit;
import org.eclipse.scava.platform.delta.vcs.VcsCommitItem;
import org.eclipse.scava.platform.delta.vcs.VcsRepositoryDelta;
import org.eclipse.scava.repository.model.VcsRepository;
import org.eclipse.scava.repository.model.vcs.svn.SvnRepository;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.io.SVNRepository;

public class SvnManager extends AbstractVcsManager {
	
	@Override
	public boolean appliesTo(VcsRepository repository) {
		return repository instanceof SvnRepository;
	}

	protected SVNRepository getSVNRepository(SvnRepository repository) {
		SvnUtil.setupLibrary();
		SVNRepository svnRepository = SvnUtil.connectToSVNInstance(repository.getUrl());
		return svnRepository;
	}
	
	@Override
	public VcsRepositoryDelta getDelta(VcsRepository repository, String startRevision, String endRevision) throws Exception {
		SvnRepository _svnRepository = (SvnRepository) repository;
		SVNRepository svnRepository = getSVNRepository(_svnRepository);

		VcsRepositoryDelta delta = new VcsRepositoryDelta();
		delta.setRepository(repository);
		
		String userProviderURL = _svnRepository.getUrl();
		String rootURL = svnRepository.getRepositoryRoot(false).toDecodedString();
		
		String overLappedURL = makeRelative(rootURL, userProviderURL);
		if (!overLappedURL.startsWith("/")) {
			overLappedURL = "/" + overLappedURL;
		}
		
//		if (!startRevision.equals(endRevision)) {
			Collection<?> c = svnRepository.log(new String[]{""}, null, Long.valueOf(startRevision), Long.valueOf(endRevision), true, true);

			for (Object o : c) {
				SVNLogEntry svnLogEntry = (SVNLogEntry) o;

				VcsCommit commit = new VcsCommit();
				
				commit.setAuthor(svnLogEntry.getAuthor());
				commit.setAuthorEmail(svnLogEntry.getAuthor());
				commit.setMessage(svnLogEntry.getMessage());
				commit.setRevision(svnLogEntry.getRevision() + "");
				commit.setDelta(delta);
				commit.setJavaDate(svnLogEntry.getDate());
				delta.getCommits().add(commit);
				
				Map<String, SVNLogEntryPath> changedPaths = svnLogEntry.getChangedPaths();
				for (String path : changedPaths.keySet()) {
					SVNLogEntryPath svnLogEntryPath = changedPaths.get(path);

//					String[] exts = {".cxx",".h",".hxx",".cpp",".cpp",".html"};
					
//					System.err.println(path);
//					if (svnLogEntryPath.getKind() == SVNNodeKind.FILE) {
						String[] blacklist = {".png",".jpg",".bmp",".zip",".jar",".gz",".tar"};
						
						if (path.lastIndexOf(".") <= 0) continue;
						String ext = path.substring(path.lastIndexOf("."), path.length());
//						System.err.println(ext + " in " + blacklist + " == " + !Arrays.asList(blacklist).contains(ext));
						if (!Arrays.asList(blacklist).contains(ext)){
						
							VcsCommitItem commitItem = new VcsCommitItem();
							commit.getItems().add(commitItem);
							commitItem.setCommit(commit);
							
							String relativePath = makeRelative(overLappedURL, path);
							if (!relativePath.startsWith("/")) {
								relativePath = "/" + relativePath;
							}
							commitItem.setPath(relativePath);
							
							if (svnLogEntryPath.getType() == 'A') {
								commitItem.setChangeType(VcsChangeType.ADDED);
							} else if (svnLogEntryPath.getType() == 'M') {
								commitItem.setChangeType(VcsChangeType.UPDATED);
							} else if (svnLogEntryPath.getType() == 'D') {
								commitItem.setChangeType(VcsChangeType.DELETED);
							} else if (svnLogEntryPath.getType() == 'R') {
								commitItem.setChangeType(VcsChangeType.REPLACED);
							} else {
								System.err.println("Found unrecognised svn log entry type: " + svnLogEntryPath.getType());
								commitItem.setChangeType(VcsChangeType.UNKNOWN);
							}
						}
//					}
				}
			}
//		}

		return delta;
	}
	
	@Override
	public String getContents(VcsCommitItem item) throws Exception {
		
		SVNRepository repository = getSVNRepository((SvnRepository) item.getCommit().getDelta().getRepository());
		
		SVNProperties fileProperties = new SVNProperties();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		repository.getFile(item.getPath(), Long.valueOf(item.getCommit().getRevision()), fileProperties, baos);
        
		//TODO: Store mimetype?
		//TODO: Think about adding a notion of a filter
//		String mimeType = fileProperties.getStringValue(SVNProperty.MIME_TYPE);
     
//		System.err.println("File being read from SVN: " + item.getPath());
		
        StringBuffer sb = new StringBuffer();
		BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(baos.toByteArray())));
		String line;
		while((line =reader.readLine())!= null ){
			//TODO: Think about a platform-wide new line character
			sb.append(line + "\r\n");
		}
		return sb.toString();
	}

	@Override
	public String getCurrentRevision(VcsRepository repository) throws Exception {
		return getSVNRepository((SvnRepository) repository).getLatestRevision() + "";
	}
	
	/**
	 * TODO: Cache the log?
	 */
	@Override
	public String getFirstRevision(VcsRepository repository) throws Exception {
		SVNRepository svnRepository = getSVNRepository((SvnRepository) repository);
		Collection<?> c = svnRepository.log(new String[]{""}, null, 0, Long.valueOf(getCurrentRevision(repository)), true, true);
		
		for (Object o : c) {
			return String.valueOf(((SVNLogEntry) o).getRevision());
		}
		return null;
	}
	
	@Override
	public int compareVersions(VcsRepository repository, String versionOne, String versionTwo) throws Exception {
		return (Long.valueOf(versionOne).compareTo(Long.valueOf(versionTwo)));
	}

	/**
	 * TODO: Is there a more efficient implementation? (simple cache?)
	 */
	@Override
	public String[] getRevisionsForDate(VcsRepository repository, Date date) throws Exception {
		String[] revs = new String[2];
		
		SvnRepository _svnRepository = (SvnRepository) repository;
		SVNRepository svnRepository = getSVNRepository(_svnRepository);
		
		Collection<?> c = svnRepository.log(new String[]{""}, null, 0, Long.valueOf(getCurrentRevision(repository)), true, true);
		boolean foundStart = false;
		SVNLogEntry svnLogEntry;
		
		for (Object o : c) {
			svnLogEntry = (SVNLogEntry) o;
			int dateComparison = date.compareTo(svnLogEntry.getDate());
			
			if (!foundStart && dateComparison == 0) {
				revs[0] = String.valueOf(svnLogEntry.getRevision());
				revs[1] = String.valueOf(svnLogEntry.getRevision());
				foundStart = true;
			} else if (foundStart && dateComparison == 0) {
				revs[1] = String.valueOf(svnLogEntry.getRevision());
			} else if (dateComparison < 0) { // Future
				break;
			}
		}
		System.out.println("SVN revisions: "+revs[0] + ", " + revs[1]);
		return revs;
	}

	/**
	 */
	@Override
	public Date getDateForRevision(VcsRepository repository, String revision) throws Exception {
		SvnRepository _svnRepository = (SvnRepository) repository;
		SVNRepository svnRepository = getSVNRepository(_svnRepository);
		
		Collection<?> c = svnRepository.log(new String[]{""}, null, 0, Long.valueOf(getCurrentRevision(repository)), true, true);
		SVNLogEntry svnLogEntry;
		
		for (Object o : c) {
			svnLogEntry = (SVNLogEntry) o;
			if (svnLogEntry.getRevision() == Long.valueOf(revision)) {
				return new Date(svnLogEntry.getDate());
			}
		}
		return null;
	}
	
	private String makeRelative(String base, String extension) {
		StringBuilder result = new StringBuilder();
		List<String> baseSegments = Arrays.asList(base.split("/"));
		String[] extensionSegments = extension.split("/");
		for (String ext : extensionSegments) {
			if (!baseSegments.contains(ext)) {
				result.append(extension.substring(extension.indexOf(ext)));
				break;
			}
		}
		return result.toString();
	}

	@Override
	public boolean validRepository(VcsRepository repository) throws Exception {
		return getSVNRepository((SvnRepository)repository) != null;
	}
}
