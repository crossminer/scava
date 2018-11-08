package org.eclipse.scava.crossflow.restmule.client.github.test.mde;

import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.scava.crossflow.restmule.client.github.model.Commit;
import org.eclipse.scava.crossflow.restmule.client.github.model.SearchCode;
import org.eclipse.scava.crossflow.restmule.client.github.model.SearchCode.Repository;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Trivial data consumer, printing to console
 * 
 * @author kb
 *
 */
public class RepoAndFileDataConsumer implements Observer<SearchCode> {

	HashMap<String, HashMap<String, HashMap<String, HashSet<String>>>> outputMap;

	public RepoAndFileDataConsumer(HashMap<String, HashMap<String, HashMap<String, HashSet<String>>>> outputMap) {
		this.outputMap = outputMap;
	}

	@Override
	public void onNext(SearchCode o) {

		System.err.println(o.getPath());

		Repository r = o.getRepository();

		String reponame = r.getFullName();

		HashMap<String, HashMap<String, HashSet<String>>> files = outputMap.get(reponame);

		if (files == null)
			files = new HashMap<String, HashMap<String, HashSet<String>>>();

		files.put(o.getPath(), null);
		outputMap.put(reponame, files);

		// o.getRepository().get

	}

	private String asString(Object o) {
		if (o instanceof Repository)
			return "repo: " + ((Repository) o).getFullName();
		if (o instanceof SearchCode)
			return "file: " + ((SearchCode) o).getName();
		if (o instanceof Commit)
			return "commit: " + ((Commit) o).getUrl();
		else
			return o.toString();
	}

	@Override
	public void onError(Throwable e) {
		e.printStackTrace();

	}

	@Override
	public void onComplete() {
		System.out.println("DATA STREAM ENDED");
		dumpData();
	}

	@Override
	public void onSubscribe(Disposable d) {
		//
	}

	// HashMap<String, HashMap<String, Entry<String, HashSet<String>>>>
	public void dumpData() {
		for (String s : outputMap.keySet()) {
			// repo
			System.out.println(s);
			for (String t : outputMap.get(s).keySet()) {
				// file
				System.out.println("\t" + t);
				for (String u : outputMap.get(s).get(t).keySet()) {
					// author
					System.out.println("\t\t" + u);
					for (String v : outputMap.get(s).get(t).get(u))
						System.out.println("\t\t\t" + v);
				}
			}

		}
	}
}
