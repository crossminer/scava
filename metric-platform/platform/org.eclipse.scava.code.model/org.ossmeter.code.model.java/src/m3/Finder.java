package m3;

import java.io.*;
import java.nio.file.attribute.*;
import static java.nio.file.FileVisitResult.*;
import java.nio.file.*;

public class Finder extends SimpleFileVisitor<Path> {
	private final PathMatcher matcher;
	private int numMatches = 0;
	
	public Finder(String pattern) {
		matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
	}
	
	public boolean done() {
		return numMatches > 0;
	}
	
	private void find(Path file) {
		Path name = file.getFileName();
		if (name != null && matcher.matches(name)) {
			numMatches++;
		}
	}

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        find(file);
        return CONTINUE;
    }

    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        find(dir);
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        System.err.println(exc);
        return CONTINUE;
    }
}
