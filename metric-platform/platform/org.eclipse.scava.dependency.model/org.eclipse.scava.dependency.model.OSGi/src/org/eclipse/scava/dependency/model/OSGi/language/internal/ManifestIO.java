package org.eclipse.scava.dependency.model.OSGi.language.internal;


import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.IMapWriter;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.interpreter.utils.RuntimeExceptionFactory;
import org.rascalmpl.uri.URIResolverRegistry;
import org.rascalmpl.uri.URIUtil;


public class ManifestIO {
	
	private final static String MANIFEST_RELATIVE_PATH = "META-INF/MANIFEST.MF";
	private final IValueFactory factory;
	private URIResolverRegistry registry;
	
	public ManifestIO(IValueFactory factory) {
		this.factory = factory;
		this.registry = new URIResolverRegistry();
	}


	public IMap loadManifest(ISourceLocation manifest, IEvaluatorContext ctx) {
		try {
			InputStream is = new FileInputStream(new File(manifest.getURI()));
			return getManifestAttributes(is);
		} 
		catch (IOException e) {
			throw RuntimeExceptionFactory.io(factory.string(e.getMessage()), null, null);
		}
	}
	
	private IMap getManifestAttributes(InputStream is) throws IOException {
		IMapWriter mWriter = factory.mapWriter();
		Manifest mf = new Manifest(is);
		
		mf.getMainAttributes()
		.forEach((k, v) -> {
			String key = k.toString();
			String val = v.toString() + "\n";
			
			mWriter.put(
				factory.string(key),
				factory.string(val)
			);
		});
		
		return mWriter.done();
	}
}
