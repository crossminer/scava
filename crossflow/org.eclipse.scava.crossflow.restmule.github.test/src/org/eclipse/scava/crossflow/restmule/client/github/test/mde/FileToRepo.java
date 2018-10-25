package org.eclipse.scava.crossflow.restmule.client.github.test.mde;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.scava.crossflow.restmule.client.github.model.SearchCode;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

public class FileToRepo implements ObservableSource<SearchCode.Repository>, Observer<SearchCode> {

	private static final Logger LOG = LogManager.getLogger(FileToRepo.class);

	protected PublishSubject<SearchCode.Repository> repoObs = PublishSubject.create();
	// notifications to tools interested in progress info
	protected Collection<Observer<? super SearchCode.Repository>> subscribers = new LinkedList<>();

	public Observable<SearchCode.Repository> repos() {
		return repoObs;
	}

	private HashSet<String> cache = new HashSet<>();

	@Override
	public void onNext(SearchCode o) {
		if (!cache.contains(o.getPath())) {
			try {

				SearchCode.Repository r = o.getRepository();

				// LOG.info(r.getId());
				
				//FIXME doing only some
				//
				//if(cache.size()<1)
				//if(r.getFullName().equals("DevBoost/EMFText-Zoo"))
				//
				repoObs.onNext(r);

			} catch (Exception e) {
				System.err.println("Error in onNext() of GeneratedGithubRepoToFiles:");
				e.printStackTrace();
			}
			cache.add(o.getPath());
		}
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
		repoObs.onComplete();
	}

	@Override
	public void subscribe(Observer<? super SearchCode.Repository> observer) {
		subscribers.add(observer);
	}

}
