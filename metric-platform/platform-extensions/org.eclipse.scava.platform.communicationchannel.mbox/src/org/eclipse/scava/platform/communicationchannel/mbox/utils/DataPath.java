package org.eclipse.scava.platform.communicationchannel.mbox.utils;

import java.io.File;
import java.nio.file.Path;

public class DataPath {
	private boolean file;
	private Path path;
	
	public DataPath(Path path, boolean file)
	{
		this.file=file;
		this.path=path;
	}
	
	public Path getPath() {
		return path;
	}
	
	public boolean isFile() {
		return file;
	}
	
	public File getFile()
	{
		return path.toFile();
	}
}
