package org.eclipse.scava.business.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.scava.business.ICodeCloneDetector;
import org.eclipse.scava.business.dto.CodeCloneResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harukizaemon.simian.Checker;
import com.harukizaemon.simian.FileLoader;
import com.harukizaemon.simian.Options;
import com.harukizaemon.simian.StreamLoader;

@Service
public class SimianCodeCloneDetector implements ICodeCloneDetector {

	private static final Logger logger = Logger.getLogger(SimianCodeCloneDetector.class);
	@Autowired
	CodeListenerImpl aulist;

	@Override
	public CodeCloneResult checkClone(String left, String right, Options options) {
		List<String> results = new ArrayList<String>();
		Checker checker = new Checker(aulist, options);
		StreamLoader streamLoader = new StreamLoader(checker);
		FileLoader fileLoader = new FileLoader(streamLoader);
		try {
			fileLoader.load(createTempFile(left));
			fileLoader.load(createTempFile(right));
			checker.check();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		CodeCloneResult pattern = new CodeCloneResult();

		pattern.setTime(aulist.getTotalTime());
		pattern.setDuplicatedLines(aulist.getDuplicatedLines());
		if (aulist.getNumClonedBlock() > 0 && aulist.getNumClonedFiles() == 2) {
			results = getRecommendedlines(aulist.getClonedCode(), right);
			pattern.setCodeLines(results);
		}
		return pattern;
	}

	private List<String> getRecommendedlines(ArrayList<String> blocks, String pattern){
		List<String> patternLines = Arrays.asList(pattern.split("\n"));
		patternLines.removeAll(blocks);	
		return patternLines;
	}

	private File createTempFile(String devSnippet) throws IOException {
		File recFile = File.createTempFile("temp", ".java");
		FileWriter writer = new FileWriter(recFile, true);
		writer.write(devSnippet);
		writer.flush();
		writer.close();
		recFile.deleteOnExit();
		return recFile;
	}

}
