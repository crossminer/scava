/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.test.focus;

import java.io.IOException;
import java.nio.file.Paths;

import org.eclipse.scava.business.impl.FocusCodeSnippetRecommender;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.model.Artifact;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class FocusTest3 {
	@Autowired
	private FocusCodeSnippetRecommender car;
	
	private static final Logger logger = LoggerFactory.getLogger(FocusTest3.class);
	@Value("${focus.extension}")	
	private String FOCUS_EXTENSION;
	@Value("${focus.code.snippet.path}")	
	private String jarPath;
	@Autowired
	private ArtifactRepository artRepo;
	
	
	@Before
	public void init() throws IOException {
        for (Artifact art : artRepo.findAll()) {
        	if (art.getMethodDeclarations()!=null &&
        			art.getMethodDeclarations().size()>0)
				try {
					String jarName = art.getName().replace("g_", "").replace(FOCUS_EXTENSION, "").replace(".jar", "-sources.jar");
					String invocation = art.getMethodDeclarations().get(0).getName();
					logger.info("\tlooking for {} in {}", invocation, jarName);
					String methodQuery = "|java+method:///" + invocation + "|";
					car.getCode(Paths.get(jarPath, jarName).toString(), methodQuery);
					logger.info("\tlooked for {} in {}", invocation, jarName);
				} catch (Exception e) {
					logger.error("ERROR {}", e.getMessage());
				}
		}
	}

	
    
    
    @Test
    public void test() throws Exception {
    	logger.info("JJ");
    }
    	
}
