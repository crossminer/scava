package org.eclipse.scava.platform.storage.communicationchannel;

import java.io.File;
import java.nio.file.Path;

public class CommunicationChannelDataPath {
	private boolean file;
	private Path path;
	
	public CommunicationChannelDataPath(Path path, boolean file)
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
