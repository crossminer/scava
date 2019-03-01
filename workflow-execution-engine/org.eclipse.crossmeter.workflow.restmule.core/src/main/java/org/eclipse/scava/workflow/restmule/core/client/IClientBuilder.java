package org.eclipse.scava.workflow.restmule.core.client;

import org.eclipse.scava.workflow.restmule.core.session.ISession;

/**
 * 
 * {@link IClientBuilder}
 * <p>
 * @version 1.0.0
 *
 */
public interface IClientBuilder<T> {

	T build();
	IClientBuilder<T> setSession(ISession session);
	IClientBuilder<T> setActiveCaching(boolean activeCaching);

}
