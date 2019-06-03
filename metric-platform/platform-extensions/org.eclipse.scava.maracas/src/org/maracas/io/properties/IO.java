package org.maracas.io.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Properties;

import org.eclipse.core.runtime.Platform;
import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.uri.URIResolverRegistry;

import io.usethesource.vallang.IMap;
import io.usethesource.vallang.IMapWriter;
import io.usethesource.vallang.ISourceLocation;
import io.usethesource.vallang.IValueFactory;


public class IO {
	private final IValueFactory factory;
	private URIResolverRegistry registry;

	public IO(IValueFactory factory) {
		this.factory = factory;
		this.registry = URIResolverRegistry.getInstance();
	}

	// loc is a project:// location
	public IMap readProperties(ISourceLocation loc, IEvaluatorContext eval) {
		IMapWriter writer = factory.mapWriter();
		
		try {
			Properties prop = new Properties();
			InputStream stream;

			// Argh.
			if (Platform.isRunning())
				stream = registry.getInputStream(loc);
			else
				stream = new FileInputStream(loc.toString().replaceAll("project://maracas", System.getProperty("user.dir"))
															.replaceAll("\\|", ""));

			prop.load(stream);
			for(Object keyObj : prop.keySet()) {
				String key = keyObj.toString();
				writer.put(factory.string(key), factory.string(prop.getProperty(key)));
			}

			stream.close();
		} 
		catch (IOException e) {
			eval.getStdErr().println(e.getMessage());
		}
		
		return writer.done();
	} 
}
