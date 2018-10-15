/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.test.codeclone;

import static org.junit.Assert.assertEquals;

import org.eclipse.scava.business.ICodeCloneDetector;
import org.eclipse.scava.business.dto.ApiCallResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.harukizaemon.simian.Language;
import com.harukizaemon.simian.Option;
import com.harukizaemon.simian.Options;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application.properties")
public class CodeCloneTest {
	
	private static final Logger logger = LoggerFactory.getLogger(CodeCloneTest.class);

	@Autowired
	private ICodeCloneDetector cloneDetector;

	private static final String left = "numFiles=sum.getDuplicateFileCount();\n" + 
			"		numBlock=sum.getDuplicateBlockCount()";
	private static final String right = "		numFiles=sum.getDuplicateFileCount();\n" + 
			"blocks=sum.getDuplicateBlockCount()";
	
	
	
	@Test
	public void crossSimCommutativeTest() {
		Options options = new Options();
		options.setThreshold(2);
		options.setOption(Option.REPORT_DUPLICATE_TEXT, true);
		options.setOption(Option.IGNORE_STRINGS, true);
		options.setOption(Option.IGNORE_STRING_CASE, true);
		options.setOption(Option.IGNORE_VARIABLE_NAMES, true);
		options.setOption(Option.IGNORE_CHARACTER_CASE, true);
		options.setOption(Option.IGNORE_IDENTIFIER_CASE, true);
		options.setOption(Option.IGNORE_MODIFIERS, true);
		options.setOption(Option.IGNORE_LITERALS, true);
		options.setOption(Option.LANGUAGE, Language.JAVA);
		options.setOption(Option.IGNORE_SUBTYPE_NAMES, true);
		ApiCallResult result = cloneDetector.checkClone(left, right, options);
		assertEquals(result.getDuplicatedLines(), 4);
		
	}
}
