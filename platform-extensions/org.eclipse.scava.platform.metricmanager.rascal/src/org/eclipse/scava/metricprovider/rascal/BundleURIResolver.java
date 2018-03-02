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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;

import org.eclipse.core.runtime.FileLocator;
import org.rascalmpl.uri.IURIInputStreamResolver;
import org.rascalmpl.uri.IURIOutputStreamResolver;
import org.rascalmpl.uri.URIResolverRegistry;
import org.rascalmpl.uri.URIUtil;

public class BundleURIResolver implements IURIOutputStreamResolver,
		IURIInputStreamResolver {
	private URIResolverRegistry registry;

	public BundleURIResolver(URIResolverRegistry registry) {
		this.registry = registry;
	}
	
	public URI getResourceURI(URI uri) throws IOException {
		return resolve(uri);
	}

	public OutputStream getOutputStream(URI uri, boolean append)
			throws IOException {
		URI parent = resolve(URIUtil.getParentURI(uri));
		parent = resolve(parent);
		return registry.getOutputStream(URIUtil.getChildURI(parent, URIUtil.getURIName(uri)), append);
	}

	public void remove(URI uri) throws IOException {
//	  registry.remove(resolve(uri));
	}
	
  public void mkDirectory(URI uri) throws IOException {
		URI parent = resolve(URIUtil.getParentURI(uri));
		parent = resolve(parent);
		registry.mkDirectory(URIUtil.getChildURI(parent, URIUtil.getURIName(uri)));
	}

	public String scheme() {
		return "bundleresource";
	}

	public boolean exists(URI uri) {
		try {
			return registry.exists(resolve(uri));
		} catch (IOException e) {
			return false;
		}
	}

	public InputStream getInputStream(URI uri) throws IOException {
		return registry.getInputStream(resolve(uri));
	}

	private URI resolve(URI uri) throws IOException {
		try {
			URL resolved = FileLocator.resolve(uri.toURL());
			URI result = null;
			try {
				result = URIUtil.fixUnicode(resolved.toURI()); 
			}
			catch (URISyntaxException e) {
				// lets try to make a URI out of the URL.
				String path = resolved.getPath();
				if (path.startsWith("file:")) {
					path = path.substring(5);
				}
				result = URIUtil.create(resolved.getProtocol(), resolved.getAuthority(), path);
			}
			if (result == uri) {
				throw new IOException("could not resolve " + uri);
			}
			
			return result;
		} catch (URISyntaxException e) {
			throw new IOException("unexpected URI syntax exception: " + e.getMessage(), e);
		}
	}

	public boolean isDirectory(URI uri) {
		try {
			return registry.isDirectory(resolve(uri));
		} catch (IOException e) {
			return false;
		}
	}

	public boolean isFile(URI uri) {
		try {
			return registry.isFile(resolve(uri));
		} catch (IOException e) {
			return false;
		}
	}

	public long lastModified(URI uri) throws IOException {
		return registry.lastModified(resolve(uri));
	}

	public String[] listEntries(URI uri) throws IOException {
		return registry.listEntries(resolve(uri));
	}

	@Override
	public boolean supportsHost() {
		return false;
	}

	@Override
	public Charset getCharset(URI uri) {
		// TODO need to check if a JAR actually stores the charset
		return null;
	}

}
