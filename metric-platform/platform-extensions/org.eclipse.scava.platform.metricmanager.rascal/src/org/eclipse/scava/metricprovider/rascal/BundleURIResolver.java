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
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.rascalmpl.uri.ILogicalSourceLocationResolver;
import org.rascalmpl.uri.ISourceLocationInput;
import org.rascalmpl.uri.ISourceLocationOutput;
import org.rascalmpl.uri.URIResolverRegistry;
import org.rascalmpl.uri.URIUtil;
import org.rascalmpl.values.ValueFactoryFactory;

import io.usethesource.vallang.ISourceLocation;
import io.usethesource.vallang.IValueFactory;

public class BundleURIResolver implements ISourceLocationOutput,
ISourceLocationInput, ILogicalSourceLocationResolver {

	private URIResolverRegistry registry;
	private final static IValueFactory VF = ValueFactoryFactory.getValueFactory();

	public BundleURIResolver(URIResolverRegistry registry) {
		this.registry = registry;
	}

	public ISourceLocation getResourceURI(ISourceLocation loc) throws IOException {
		return registry.logicalToPhysical(loc);
	}

	public String scheme() {
		return "bundleresource";
	}

	@Override
	public boolean supportsHost() {
		return false;
	}

	@Override
	public boolean exists(ISourceLocation loc) {
		return registry.exists(loc);
	}

	@Override
	public InputStream getInputStream(ISourceLocation loc) throws IOException {
		return registry.getInputStream(loc);
	}

	@Override
	public boolean isDirectory(ISourceLocation loc) {
		return registry.isDirectory(loc);
	}

	@Override
	public boolean isFile(ISourceLocation loc) {
		return registry.isFile(loc);
	}

	@Override
	public long lastModified(ISourceLocation loc) throws IOException {
		return registry.lastModified(loc);
	}

	@Override
	public String[] list(ISourceLocation loc) throws IOException {
		return registry.listEntries(loc);
	}

	@Override
	public OutputStream getOutputStream(ISourceLocation loc, boolean append) throws IOException {
		return registry.getOutputStream(loc, append);
	}

	@Override
	public void mkDirectory(ISourceLocation loc) throws IOException {
		registry.mkDirectory(loc);
	}

	@Override
	public void remove(ISourceLocation loc) throws IOException {
		registry.remove(loc);
	}

	@Override
	public String authority() {
		return null;
	}

	@Override
	public ISourceLocation resolve(ISourceLocation uri) {
		try {
			ISourceLocation result;
			URL resolved = FileLocator.resolve(uri.getURI().toURL());
			try {
				if (resolved.getProtocol().equals("jar") && resolved.getPath().startsWith("file:/")) {
					result = VF.sourceLocation("jar", null, resolved.getPath().substring("file:".length()));
				} else {
					result = VF.sourceLocation(URIUtil.fixUnicode(resolved.toURI()));
				}
			}
			catch (URISyntaxException e) {
				// lets try to make a URI out of the URL.
				String path = resolved.getPath();
				if (path.startsWith("file:")) {
					path = path.substring(5);
				}
				result = VF.sourceLocation(URIUtil.create(resolved.getProtocol(), resolved.getAuthority(), path));
			}
			if (result == uri) {
				throw new IOException("could not resolve " + uri);
			}
			return result;
		} 
		catch (URISyntaxException|IOException e) {
			//"unexpected URI syntax exception: " + e.getMessage()
		}
		return null;
	}
}
