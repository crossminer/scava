package org.eclipse.scava.dependency.model.osgi.language.internal;


import io.usethesource.vallang.IMap;
import io.usethesource.vallang.IMapWriter;
import io.usethesource.vallang.ISourceLocation;
import io.usethesource.vallang.IValueFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Manifest;

import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.interpreter.utils.RuntimeExceptionFactory;


public class ManifestIO {
	private final IValueFactory factory;
	
	public ManifestIO(IValueFactory factory) {
		this.factory = factory;
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
