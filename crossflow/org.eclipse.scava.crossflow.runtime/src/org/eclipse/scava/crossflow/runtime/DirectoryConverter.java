package org.eclipse.scava.crossflow.runtime;

import java.io.File;

import com.beust.jcommander.IStringConverter;

public class DirectoryConverter implements IStringConverter<File> {

	@Override
	public File convert(String s) {
		return new File(s).getAbsoluteFile();
	}

}