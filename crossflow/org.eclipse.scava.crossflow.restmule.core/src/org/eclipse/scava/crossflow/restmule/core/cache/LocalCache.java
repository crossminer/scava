package org.eclipse.scava.crossflow.restmule.core.cache;

import java.io.File;

import okhttp3.Cache;
import okhttp3.Request;
import okhttp3.Response;

public class LocalCache implements ICache {

	private final static int cacheSize = 10 * 1024 * 1024; // 10 MiB
	private Cache cache;

	@Override
	public Cache initializeLocal() {
		if (cache == null) {
			File cachefolder = new File("okhttp_cache");
			if (!cachefolder.exists())
				cachefolder.mkdir();
			cache = new Cache(cachefolder, cacheSize);
		}
		return cache;
	}

	@Override
	public Response load(Request request) {
		return null;
	}

	@Override
	public void put(Request request, Response response) {
	}

	@Override
	public void clear() {
	}

	@Override
	public void tearDown() {
	}

	@Override
	public boolean isDistributed() {
		return false;
	}

}
