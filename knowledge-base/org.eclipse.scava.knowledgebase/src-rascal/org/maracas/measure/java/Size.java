package org.maracas.measure.java;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.uri.URIResolverRegistry;

import io.usethesource.vallang.IInteger;
import io.usethesource.vallang.ISourceLocation;
import io.usethesource.vallang.IValueFactory;

public class Size {
	private final IValueFactory factory;
	private URIResolverRegistry registry;
	
	public Size(IValueFactory factory) {
		this.factory = factory;
		this.registry = URIResolverRegistry.getInstance();
	}
	
	public IInteger countBytes(ISourceLocation source, IEvaluatorContext eval) {
		return countBytes(source.getPath());
	}
	
	private IInteger countBytes(String path) {
		int bytes = 0;
		
		try {
			Path dir = Paths.get(path);

			if (Files.isDirectory(dir)) {
				bytes = (int) Files.walk(dir)
						.filter(p -> p.toFile().isFile())
						.mapToLong(p -> p.toFile().length())
						.sum();
			}
			else {
				bytes = (int) dir.toFile().length();
			}
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return factory.integer(bytes);
	}
}
