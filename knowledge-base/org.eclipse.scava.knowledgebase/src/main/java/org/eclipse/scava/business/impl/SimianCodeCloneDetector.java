package org.eclipse.scava.business.impl;

/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.compress.utils.Lists;
import org.eclipse.scava.business.ICodeCloneDetector;
import org.eclipse.scava.business.dto.ApiCallResult;
import org.springframework.stereotype.Service;

import com.harukizaemon.simian.Checker;
import com.harukizaemon.simian.FileLoader;
import com.harukizaemon.simian.Options;
import com.harukizaemon.simian.StreamLoader;

@Service
public class SimianCodeCloneDetector implements ICodeCloneDetector {

	private static final Logger logger = LoggerFactory.getLogger(SimianCodeCloneDetector.class);

	@Override
	public ApiCallResult checkClone(String left, String right, Options options) throws IOException {
		CodeListenerImpl aulist = new CodeListenerImpl();
		List<String> results = new ArrayList<String>();
		Checker checker = new Checker(aulist, options);
		StreamLoader streamLoader = new StreamLoader(checker);
		FileLoader fileLoader = new FileLoader(streamLoader);

		fileLoader.load(createTempFile(left, "left"));
		fileLoader.load(createTempFile(right, "right"));
		if (checker.check()) {
			CodeListenerImpl auListL = checkClone(left, options);
			CodeListenerImpl auListR = checkClone(right, options);
			ArrayList<String> tbd = Lists.newArrayList();
			aulist.getClonedCode().forEach(z -> {
				if (auListL.getClonedCode().contains(z))
					tbd.add(z);
				if (auListR.getClonedCode().contains(z))
					tbd.add(z);
			});
			aulist.getClonedCode().removeAll(tbd);
			ApiCallResult pattern = new ApiCallResult();
			pattern.setTime(aulist.getTotalTime());
			pattern.setDuplicatedLines(aulist.getDuplicatedLines());
			if (aulist.getClonedCode().size() > 0 && aulist.getNumClonedBlock() > 0
					&& aulist.getNumClonedFiles() == 2) {
				logger.info("a patter is discoverd");
				results = getRecommendedlines(aulist.getClonedCode(), right);
				pattern.setCodeLines(results);
				return pattern;
			} else
				return null;
		} else
			return null;
	}

	private CodeListenerImpl checkClone(String part, Options options) throws IOException {
		CodeListenerImpl aulist = new CodeListenerImpl();
		Checker checker = new Checker(aulist, options);
		StreamLoader streamLoader = new StreamLoader(checker);
		FileLoader fileLoader = new FileLoader(streamLoader);
		fileLoader.load(createTempFile(part, "left"));
		checker.check();
		return aulist;
	}

	private List<String> getRecommendedlines(ArrayList<String> blocks, String pattern) {
		List<String> patternLines = Arrays.asList(pattern.split("\n"));
		patternLines.removeAll(blocks);
		return patternLines;
	}

	private File createTempFile(String devSnippet, String filename) throws IOException {
		File recFile = File.createTempFile(filename, ".java");
		FileWriter writer = new FileWriter(recFile, true);
		writer.write(devSnippet);
		writer.flush();
		writer.close();
		recFile.deleteOnExit();
		return recFile;
	}

}
