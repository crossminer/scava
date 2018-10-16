package org.eclipse.scava.crossflow.restmule.client.github.test.mde;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.scava.crossflow.restmule.client.github.model.Commits;
import org.eclipse.scava.crossflow.restmule.client.github.model.SearchCode;
import org.eclipse.scava.crossflow.restmule.client.github.model.SearchCode.Repository;
import org.eclipse.scava.crossflow.restmule.client.github.util.GitHubUtils;
import org.eclipse.scava.crossflow.restmule.core.data.IDataSet;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

public class FileToCommits implements ObservableSource<Entry<String, Entry<String, Commits>>>, Observer<SearchCode> {

	private static final Logger LOG = LogManager.getLogger(FileToCommits.class);

	protected PublishSubject<Entry<String, Entry<String, Commits>>> commits = PublishSubject.create();
	// notifications to tools interested in progress info
	protected Collection<Observer<? super Entry<String, Entry<String, Commits>>>> subscribers = new LinkedList<>();

	public Observable<Entry<String, Entry<String, Commits>>> commits() {
		return commits;
	}

	private HashSet<String> cache = new HashSet<>();

	@Override
	public void onNext(SearchCode o) {

		if (!cache.contains(o.getPath())) {

			Repository r = o.getRepository();

			String repo = r.getFullName();
			String file = o.getPath();

			//
			// System.err.println(r.getOwner());
			// System.err.println(r.getOwner().getLogin());
			// System.err.println(r.getOwner().getHtmlUrl());
			// System.err.println(r.getOwner().getReposUrl());
			//
			// IDataSet<Commits> ret =
			// GitHubTestUtil.getOAuthClient().getReposCommits(r.getOwner().getLogin(),r.getFullName(),
			// "1018-01-24T17:50:00Z", "master", o.getName(), "",
			// "2018-01-24T17:50:00Z");

			IDataSet<Commits> ret = GitHubUtils.getOAuthClient().getReposCommits(r.getOwner().getLogin(),
					r.getName(), null, null, o.getPath(), null, null);
			//
			ret.observe().map(c -> entry(repo, file, c)).subscribe(commits);

			cache.add(o.getPath());

		}

	}

	private Entry<String, Entry<String, Commits>> entry(String r ,String f,Commits c) {
		
		Entry<String, Entry<String, Commits>> ret = new Entry<String, Map.Entry<String,Commits>>() {
			String key=r;
			Map.Entry<String,Commits> value=new Entry<String, Commits>() {
				String key=f;
				Commits value =c;
				@Override
				public Commits setValue(Commits value) {
					//NYI
					return null;
				}
				
				@Override
				public Commits getValue() {
					return value;
				}
				
				@Override
				public String getKey() {
					return key;
				}
			};
			
			@Override
			public Entry<String, Commits> setValue(Entry<String, Commits> value) {
				// NYI
				return null;
			}
			
			@Override
			public Entry<String, Commits> getValue() {
				return value;
			}
			
			@Override
			public String getKey() {
				return key;
			}
		};
		return ret;
	}

	@Override
	public void onSubscribe(Disposable d) {
		//
	}

	@Override
	public void onError(Throwable e) {
		e.printStackTrace();
	}

	@Override
	public void onComplete() {
		commits.onComplete();
	}

	@Override
	public void subscribe(Observer<? super Entry<String, Entry<String, Commits>>> observer) {
		subscribers.add(observer);
	}

}
