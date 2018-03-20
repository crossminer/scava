package org.eclipse.scava.dependency.model.OSGi.language.internal;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.IMapWriter;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.interpreter.utils.RuntimeExceptionFactory;
import org.rascalmpl.uri.URIResolverRegistry;


/**
 * Notes to self:
 *   - Where to retrieve .jar/.MF identified by
 *     Bundle-SymbolicName "remotely"?
 *     (e.g. Tycho / p2 / Maven / whatever)
 */
public class ManifestIO {
	
	private final static String MANIFEST_RELATIVE_PATH = "META-INF/MANIFEST.MF";
	private final IValueFactory factory;
	private URIResolverRegistry registry;
	
	public ManifestIO(IValueFactory factory) {
		this.factory = factory;
		this.registry = new URIResolverRegistry();
	}

	public IMap loadManifest(ISourceLocation loc, IEvaluatorContext ctx) {
		try {
			InputStream is = registry.getInputStream(loc.getURI());
	        ZipInputStream jarStream = new ZipInputStream(is);
	        ZipEntry entry = jarStream.getNextEntry();
	        boolean found = false;

	        while (entry != null && !found) {
	        	    //ctx.getStdErr().println(entry.getName());
	        		if(entry.getName().equalsIgnoreCase(MANIFEST_RELATIVE_PATH)) {
	        			return getManifestAttributes(jarStream);
	        		}
	            entry = jarStream.getNextEntry();
	        }
		} 
		catch (IOException e) {
			throw RuntimeExceptionFactory.io(factory.string(e.getMessage()), null, null);
		}
		return null;
	}
	
	public IMap loadManifest(IString content, IEvaluatorContext ctx) {
		InputStream is = new ByteArrayInputStream(content.getValue().getBytes(StandardCharsets.UTF_8));
		try {
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
