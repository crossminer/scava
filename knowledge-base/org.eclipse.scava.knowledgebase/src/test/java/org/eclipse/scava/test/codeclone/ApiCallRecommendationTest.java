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

import org.apache.log4j.Logger;
import org.eclipse.scava.Application;
import org.eclipse.scava.business.ICodeCloneDetector;
import org.eclipse.scava.business.IRecommendationProvider;
import org.eclipse.scava.business.dto.ApiCallResult;
import org.eclipse.scava.business.dto.Query;
import org.eclipse.scava.business.dto.Recommendation;
import org.eclipse.scava.config.SwaggerConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.harukizaemon.simian.Language;
import com.harukizaemon.simian.Option;
import com.harukizaemon.simian.Options;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Application.class, SwaggerConfig.class })
@WebAppConfiguration
@TestPropertySource(locations="classpath:application.properties")
public class ApiCallRecommendationTest {
	
	private static final Logger logger = Logger.getLogger(ApiCallRecommendationTest.class);

	@Autowired
	@Qualifier("ApiCallRecommendation")
	private IRecommendationProvider apiCallRecommendation;
	
	private static final String method = "public static KnowledgeBase readKnowledgeBase(List<RuleResource> resources) {\r\n" + 
				    " KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();\r\n" + 
				    " for (RuleResource res: resources) {\r\n" + 
				    "  try {\r\n" + 
				    "   kbuilder.add(ResourceFactory.newClassPathResource(res.getRuleResourceFile()), res.getResType());\r\n" + 
				    "  } catch (Exception ex) {\r\n" + 
				    "   kbuilder.add(ResourceFactory.newFileResource(res.getRuleResourceFile()), res.getResType());\r\n" + 
				    "  }\r\n" + 
				    " }\r\n" + 
				    " KnowledgeBuilderErrors errors = kbuilder.getErrors();\r\n" + 
				    " if (errors.size() > 0) {\r\n" + 
				    "  for (KnowledgeBuilderError error: errors) {\r\n" + 
				    "   System.err.println(error);\r\n" + 
				    "  }\r\n" + 
				    "  throw new IllegalArgumentException(\"Could not parse knowledge.\");\r\n" + 
				    " }\r\n" + 
				    " KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();\r\n" + 
				    " kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());\r\n" + 
				    " return kbase;\r\n" + 
				    "}";;
	
	@Test
	public void crossSimCommutativeTest() throws Exception {
		Query query = new Query();
		query.setMethodInvocation(method);
		Recommendation v = apiCallRecommendation.getRecommendation(query);
		assertEquals(2, v.getRecommendationItems().size());
	}
}
