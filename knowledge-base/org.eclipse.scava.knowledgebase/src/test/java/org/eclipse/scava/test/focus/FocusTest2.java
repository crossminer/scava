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
import java.util.List;

import org.eclipse.scava.business.dto.FocusInput;
import org.eclipse.scava.business.dto.Query;
import org.eclipse.scava.business.dto.Recommendation;
import org.eclipse.scava.business.impl.DataReader;
import org.eclipse.scava.business.impl.FocusCodeSnippetRecommender;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.model.Artifact;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class FocusTest2 {

	@Autowired 
	private DataReader dr;

	@Autowired
	private FocusCodeSnippetRecommender car;
	
	private static final Logger logger = LoggerFactory.getLogger(FocusTest2.class);
	
	@Autowired
	private ArtifactRepository artRepo;
	
	private Artifact testing;
	
	@Before
	public void init() throws IOException {
        artRepo.deleteAll();
		dr.readArtifactsFromFolder("/Users/juri/Desktop/focusImport");
	}

	
    @Test
    @Ignore
    public void testRecommendationWithMockito() throws Exception{
    	logger.info("start");
    	Resource resource = new ClassPathResource("FOCUS/castor-xml-schema-1.3.3-rc1.jar.focus");
        testing = dr.readArtifactFromFile(resource.getFile().getAbsolutePath());		
        Query q = new Query();
    	q.setFocusInput(new FocusInput());
    	String va = "org/exolab/castor/xml/dtd/Converter/main(java.lang.String%5B%5D)";//testing.getMethodDeclarations().stream().filter(z -> z.getMethodInvocations().size() >4).collect(Collectors.toList()).get(15).getName();//findFirst().get().getName();
    	q.getFocusInput().setActiveDeclaration(va);
    	q.getFocusInput().setMethodDeclarations(testing.getMethodDeclarations());
    	List<String> result = car.focusCodeSmippetRecommender(q);
    	result.forEach(z -> logger.info(z));
    	
    }
    @Test
    @Ignore
    public void testRecommendation() throws Exception{
    	logger.info("start");
    	Resource resource = new ClassPathResource("FOCUS/castor-xml-schema-1.3.3-rc1.jar.focus");
        testing = dr.readArtifactFromFile(resource.getFile().getAbsolutePath());		
        Query q = new Query();
    	q.setFocusInput(new FocusInput());
    	String va = "org/exolab/castor/xml/dtd/Converter/main(java.lang.String%5B%5D)";//testing.getMethodDeclarations().stream().filter(z -> z.getMethodInvocations().size() >4).collect(Collectors.toList()).get(15).getName();//findFirst().get().getName();
    	q.getFocusInput().setActiveDeclaration(va);
    	q.getFocusInput().setMethodDeclarations(testing.getMethodDeclarations());
    	Recommendation s = car.getRecommendation(q);
    	s.getRecommendationItems().forEach(ri->	logger.info(ri.getCodeSnippet()));
    }
    
    
    @Test
    public void test() throws Exception {
    	logger.info("JJ");
//    	logger.info(car.getCode("/home/juri/Desktop/sourcesJar2/castor-xml-schema-1.3.3-rc1-sources.jar", "|java+method:///org/exolab/castor/xml/schema/reader/SchemaUnmarshaller/endElement(java.lang.String,java.lang.String)|"));
    }
    	
}
