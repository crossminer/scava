@license{
Copyright (c) 2014 CROSSMETER Partners.
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html
}
module org::eclipse::crossmeter::metricprovider::ProjectDelta

data ProjectDelta
  = projectDelta(datetime date, Project project, list[VcsRepositoryDelta] vcsProjectDelta)
  ;
  
ProjectDelta empty() = projectDelta($1970-01-01$, project("", []), []);
  
data Project
  = project(str name, list[VcsRepository] vcsRepositories)
  ;
  
data VcsRepository
  = vcsRepository(loc url)
  ;
  
data VcsRepositoryDelta
  = vcsRepositoryDelta(VcsRepository repository, list[VcsCommit] commits, str lastRevision)
  ;
  
data VcsCommit
  = vcsCommit(datetime date, str author, str email, str message, list[VcsCommitItem] items, str revision)
  ;
  
data VcsCommitItem
  = vcsCommitItem(str path, VcsChangeType changeType, list[Churn] churns) // TODO path as loc?
  ;
  
data VcsChangeType
  = added()
  | deleted()
  | updated()
  | replaced()
  | unknown()
  ;
  
data Churn
  = linesAdded(int i)
  | linesDeleted(int i)
  ;
  
map[loc, list[str]] getChangedItemsPerRepository(ProjectDelta delta) {
  list[str] emptyList = [];
  map[loc, list[str]] result = ();
  for (/VcsRepositoryDelta vcrd <- delta) {
    result[vcrd.repository.url]? emptyList += [ fVCI.path | VcsCommitItem fVCI <- checkSanity([ vci | /VcsCommitItem vci <- vcrd ]) ];
  }
  return result;
}

@memo
set[VcsCommitItem] checkSanity(list[VcsCommitItem] items) {
  set[VcsCommitItem] result = {};
  for (VcsCommitItem item <- items) {
    result += item;
    if (\deleted() := item.changeType || \unknown() := item.changeType) {
      result -= { vci | VcsCommitItem vci <- items, vci.path == item.path };
    }
  }
  return result;
}

list[loc] getChangedFilesInWorkingCopyFolders(ProjectDelta delta, map[loc, loc] workingCopyFolders)
{
  list[loc] result = [];
  map[loc, list[str]] changedItemsPerRepo = getChangedItemsPerRepository(delta);
  return [workingCopyFolders[repo] + item | repo <- changedItemsPerRepo, item <- changedItemsPerRepo[repo]];
}
