package org.eclipse.scava.crossflow.restmule.client.github.test.mde;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.scava.crossflow.restmule.client.github.model.SearchCode;
import org.eclipse.scava.crossflow.restmule.client.github.model.SearchCode.Repository;
import org.eclipse.scava.crossflow.restmule.client.github.test.query.CodeSearchQuery;
import org.eclipse.scava.crossflow.restmule.client.github.util.GitHubUtils;
import org.eclipse.scava.crossflow.restmule.core.data.IDataSet;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

public class RepoToFile implements ObservableSource<SearchCode>, Observer<Repository> {

	private MDE mde;

	public RepoToFile(MDE mde) {
		this.mde = mde;
	}

	private static final Logger LOG = LogManager.getLogger(RepoToFile.class);

	protected PublishSubject<SearchCode> fileObs = PublishSubject.create();
	// notifications to tools interested in progress info
	protected Collection<Observer<? super SearchCode>> subscribers = new LinkedList<>();

	public Observable<SearchCode> files() {
		return fileObs;
	}

	private HashSet<String> cache = new HashSet<>();

	@Override
	public void onNext(Repository o) {

		if (!cache.contains(o.getFullName())) {

			try {

				String q = new CodeSearchQuery().create(mde.getKeyword()).extension(mde.getExtension())
						.repo(o.getFullName()).build().getQuery();
				System.err.println(q);
				IDataSet<SearchCode> ret = GitHubUtils.getOAuthClient().getSearchCode("asc", q, null);

				ret.observe().subscribe(fileObs);

			} catch (Exception e) {
				System.err.println("Error in onNext() of GeneratedGithubRepoToFiles:");
				e.printStackTrace();
			}

			cache.add(o.getFullName());
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
		fileObs.onComplete();
	}

	@Override
	public void subscribe(Observer<? super SearchCode> observer) {
		subscribers.add(observer);
	}

	public static void main(String[] a) {

		MDE mde = MDE.Eugenia;
		String q = new CodeSearchQuery().create(mde.getKeyword()).extension(mde.getExtension())
				.repo("https://github.com/GuanglongDu/GMFSVG").inFile().build().getQuery();
		System.out.println(q);

	}

}
