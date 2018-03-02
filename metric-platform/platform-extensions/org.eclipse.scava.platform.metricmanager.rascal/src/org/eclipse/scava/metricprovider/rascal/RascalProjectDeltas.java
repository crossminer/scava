/*******************************************************************************
 * Copyright (c) 2017 Centrum Wiskunde & Informatica
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.rascal;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.vcs.VcsChangeType;
import org.eclipse.scava.platform.delta.vcs.VcsCommit;
import org.eclipse.scava.platform.delta.vcs.VcsCommitItem;
import org.eclipse.scava.platform.delta.vcs.VcsRepositoryDelta;
import org.eclipse.scava.platform.vcs.workingcopy.manager.Churn;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.VcsRepository;
import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IDateTime;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.eclipse.imp.pdb.facts.type.TypeStore;
import org.rascalmpl.interpreter.Evaluator;
import org.rascalmpl.interpreter.NullRascalMonitor;
import org.rascalmpl.uri.URIUtil;
import org.rascalmpl.values.ValueFactoryFactory;

public class RascalProjectDeltas {
  private final TypeStore store = new TypeStore();
  private final IValueFactory values = ValueFactoryFactory.getValueFactory();
  private final TypeFactory TF = TypeFactory.getInstance();
  public static final String MODULE = "org::eclipse::scava::metricprovider::ProjectDelta";
  private Map<VcsCommit, List<Churn>> churns;
  
  public RascalProjectDeltas(Evaluator eval) {
	if (!eval.getHeap().existsModule(MODULE)) {
		eval.doImport(new NullRascalMonitor(), MODULE);
	}
	store.extendStore(eval.getHeap().getModule(MODULE).getStore());
  }

  public TypeStore getStore() {
    return store;
  }

  public IConstructor emptyDelta(ProjectDelta delta) {
	  return convert(delta, Collections.<VcsCommit, List<Churn>>emptyMap());
  }
  
  public IConstructor convert(final ProjectDelta delta, Map<VcsCommit, List<Churn>> churnPerCommit) {
	List<IValue> children = new ArrayList<>();
	this.churns = churnPerCommit;
	
	children.add(convert(delta.getDate()));
	children.add(convert(delta.getProject()));
	children.add(convert(delta.getVcsDelta().getRepoDeltas()));
	
	return createConstructor("ProjectDelta", "projectDelta", children.toArray(new IValue[0]));
  }

  private IConstructor createConstructor(String adtName, String constructorName, IValue... children) {
	Type cons = store.lookupConstructor(store.lookupAbstractDataType(adtName), constructorName, TF.tupleType(children));
	
	return values.constructor(cons, children);
  }
  
  private IDateTime convert(Date date) {
	long time = date.toJavaDate().getTime();
	
	if (time != 0L) {
		return values.datetime(time);
	}
	else {
		throw new RuntimeException("VcsDelta produces epoch time (0L) unexpectedly");
	}
  }
  
  private IConstructor convert(Project project) {
	List<IValue> children = new ArrayList<>();
	
	children.add(convert(project.getName()));
	children.add(convert(project.getVcsRepositories()));
	
	return createConstructor("Project", "project", children.toArray(new IValue[0]));
  }

  private IList convert(List<?> repoDeltas) {
	IListWriter parameter = values.listWriter();

	for (Iterator<?> it = repoDeltas.iterator(); it.hasNext(); ) {
	  Object nextItem = it.next();
	  if (nextItem instanceof VcsRepository) {
		parameter.append(convert((VcsRepository) nextItem));
	  } else if (nextItem instanceof VcsRepositoryDelta) {
		parameter.append(convert((VcsRepositoryDelta) nextItem));
	  } else if (nextItem instanceof VcsCommit) {
		parameter.append(convert((VcsCommit) nextItem));
	  } else if (nextItem instanceof VcsCommitItem) {
		parameter.append(convert((VcsCommitItem) nextItem));
	  }
	}
	
	return parameter.done();
  }
  
  private IConstructor convert(VcsRepository repo) {
	return values.constructor(store.lookupConstructor(store.lookupAbstractDataType("VcsRepository"), "vcsRepository", TF.tupleType(TF.sourceLocationType())), convertToLocation(repo.getUrl()));
  }
  
  private IConstructor convert(VcsRepositoryDelta vcsRepoDelta) {
	List<IValue> children = new ArrayList<>();
		
	children.add(convert(vcsRepoDelta.getRepository()));
	children.add(convert(vcsRepoDelta.getCommits()));
	children.add(convert(vcsRepoDelta.getLatestRevision()));
		
	return createConstructor("VcsRepositoryDelta", "vcsRepositoryDelta", children.toArray(new IValue[0]));
  }
  
  private IConstructor convert(VcsCommit commit) {
	List<IValue> children = new ArrayList<>();
		
	children.add(values.datetime(commit.getJavaDate().getTime()));
	children.add(convert(commit.getAuthor()));
	children.add(convert(commit.getAuthorEmail()));
	children.add(convert(commit.getMessage()));
	children.add(convert(commit.getItems()));
	children.add(convert(commit.getRevision()));
			
	return createConstructor("VcsCommit", "vcsCommit", children.toArray(new IValue[0]));
  }
  
  private IString convert(String toConvert) {
	if (toConvert == null) {
	  return values.string("null");
	}
	return values.string(toConvert);
  }
  
  private ISourceLocation convertToLocation(String url) {
	  if (url == null) {
		  try {
			return values.sourceLocation("unknown", null, null, null, null);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  return values.sourceLocation(URIUtil.assumeCorrect(url));
  }
  
  private IConstructor convert(VcsCommitItem commitItem) {
	List<IValue> children = new ArrayList<>();
	
//	children.add(convertToLocation(commitItem.getCommit().getDelta().getRepository().getUrl() + "/" + commitItem.getPath()));
	children.add(convert(commitItem.getPath()));
	children.add(convert(commitItem.getChangeType()));
	List<Churn> commitChurns = churns.get(commitItem.getCommit());
	if (commitChurns != null) {
		children.add(createChurn(commitChurns, commitItem.getPath()));
	}
	else {
		children.add(values.listWriter().done());
	}
				
	return createConstructor("VcsCommitItem", "vcsCommitItem", children.toArray(new IValue[0]));
  }

  private IConstructor convert(VcsChangeType changeType) {
	return values.constructor(store.lookupConstructor(store.lookupAbstractDataType("VcsChangeType"), changeType.name().toLowerCase(), TF.tupleEmpty()));
  }

  public IList createChurn(List<Churn> commitChurns, String itemPath) {
	IListWriter result = values.listWriter();
	
	for (Churn c : commitChurns) {
		if (c.getPath().equals(itemPath)) {
			result.append(createConstructor("Churn", "linesAdded", values.integer(c.getLinesAdded())));
			result.append(createConstructor("Churn", "linesDeleted", values.integer(c.getLinesDeleted())));
			break;
		}
	}
	
	return result.done();
  }
}
