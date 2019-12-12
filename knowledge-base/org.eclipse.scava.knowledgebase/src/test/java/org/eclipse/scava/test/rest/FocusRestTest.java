/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.test.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.List;

import org.eclipse.scava.Application;
import org.eclipse.scava.business.dto.FocusInput;
import org.eclipse.scava.business.dto.Query;
import org.eclipse.scava.business.dto.Recommendation;
import org.eclipse.scava.business.dto.RecommendationType;
import org.eclipse.scava.business.impl.DataReader;
import org.eclipse.scava.business.impl.FocusContexAwareRecommender;
import org.eclipse.scava.business.impl.RecommenderManager;
import org.eclipse.scava.business.impl.SimilarityManager;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.config.SwaggerConfig;
import org.eclipse.scava.presentation.rest.RecommenderRestController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Application.class, SwaggerConfig.class })
@WebAppConfiguration
@TestPropertySource(locations="classpath:application.properties")
public class FocusRestTest {

	private MockMvc mockMvc;
	
	private static final Logger logger = LoggerFactory.getLogger(FocusRestTest.class);
	
    @Mock
    private RecommenderManager recommenderManager;
    
    @InjectMocks
    private FocusContexAwareRecommender focus;
    @InjectMocks
    private RecommenderRestController recController;


    @Autowired DataReader dr;
    
    @Mock
    private ArtifactRepository artifactRepo;
    
	private Artifact testing;


	@Mock
    private SimilarityManager simManager;
    @Before
    public void init() throws IOException{
    	MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(recController).build();
      
        Resource resource = new ClassPathResource("FOCUS/");
        List<Artifact> artifacts = dr.readArtifactsFromFolder(resource.getFile().getAbsolutePath());
        testing = artifacts.get(4);
		logger.info(testing.getFullName());
		artifacts.remove(testing);
   }

    @Test
    public void EmptyQueryTest() throws Exception {
        when(recommenderManager.getRecommendation(any(), eq(RecommendationType.FOCUS))).thenReturn(
        		new Recommendation());

        Query q = new Query();
        FocusInput fInput = new FocusInput();
        fInput.setActiveDeclaration(testing.getMethodDeclarations().stream().filter(z->z.getMethodInvocations().size()>4).findFirst().get().getName());
        fInput.setMethodDeclarations(testing.getMethodDeclarations());
        q.setFocusInput(fInput);
        logger.info(fInput.getActiveDeclaration());
        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(q);
        mockMvc.perform(post("/api/recommendation/focus/")
        			.contentType(MediaType.APPLICATION_JSON).content(json))
                	.andExpect(status().isOk())
                	.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }
}
