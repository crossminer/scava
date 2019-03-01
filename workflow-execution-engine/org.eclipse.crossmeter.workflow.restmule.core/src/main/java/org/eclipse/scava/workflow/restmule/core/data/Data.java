package org.eclipse.scava.workflow.restmule.core.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.reactivex.Observable;
import io.reactivex.subjects.ReplaySubject;

/**
 * 
 * {@link Data}
 * <p>
 * @version 1.0.0
 *
 */
public class Data<T> implements IData<T>{

	@SuppressWarnings("unused")
	private static final Logger LOG = LogManager.getLogger(Data.class);

	private ReplaySubject<T> subject; 
	private Status status;

	public Data(){
		this.status = Status.CREATED;
		this.subject = ReplaySubject.create();
	}

	@Override
	public Status status(){
		return status;
	};

	@Override
	public Observable<T> observe(){
		return subject;
	};

	public synchronized void addElement(final Observable<T> element) {
		T value = element.blockingSingle();
		if (value != null){
			status = Status.ADDING;
			subject.onNext(value);
			status = Status.COMPLETED;
			subject.onComplete();
		} else {
			subject.onError(new NullPointerException());
		}
	}
}
