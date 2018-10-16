package org.eclipse.scava.crossflow.restmule.core.cache;

import okhttp3.Cache;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 
 * {@link ICache}
 * <p>
 * 
 * @version 1.0.0
 *
 */
public interface ICache {

	// boolean exists(Request request); // done on load(Request) to avoid
	// redundant cache hits

	default Cache initializeLocal() {
		throw new UnsupportedOperationException("this type of cache does not support local mode");
	}

	//FIXME distributed cache disabled for now
//	default DistributedCache initializeDistributed(String cacheServer, String user, String password) {
//		throw new UnsupportedOperationException("this type of cache does not support distributed mode");
//	}

	/**
	 * Returns {@link Response} of associated {@link Request} and {@code null}
	 * if no existing {@link Response} is available.
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	Response load(Request request);

	void put(Request request, Response response);

	void clear();

	void tearDown();

	boolean isDistributed();

}
