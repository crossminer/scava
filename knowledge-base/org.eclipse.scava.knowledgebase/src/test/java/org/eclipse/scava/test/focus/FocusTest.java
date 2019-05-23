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

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.scava.business.dto.FocusInput;
import org.eclipse.scava.business.dto.Query;
import org.eclipse.scava.business.dto.Recommendation;
import org.eclipse.scava.business.dto.RecommendationItem;
import org.eclipse.scava.business.impl.DataReader;
import org.eclipse.scava.business.impl.FOCUSSimilarityCalculator;
import org.eclipse.scava.business.impl.FocusContexAwareRecommender;
import org.eclipse.scava.business.impl.SimilarityManager;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.model.Artifact;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class FocusTest {

	@Autowired 
	private DataReader dr;

	private MockMvc mockMvc;
	@Mock
    private ArtifactRepository artifactRepository;
	@Mock
	private SimilarityManager simManager;
    @Mock
    private FOCUSSimilarityCalculator fsc;
	@InjectMocks
	private FocusContexAwareRecommender car;
	
	private static final Logger logger = LoggerFactory.getLogger(FocusTest.class);
	
	
	private List<Artifact> trainings;
	private Artifact testing;
	private Map<Artifact, Float> simRes = new HashMap<Artifact, Float>();
	
	@Before
	public void init() throws IOException {
		
		MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(car).build();
        Resource resource = new ClassPathResource("FOCUS/");
        List<Artifact> artifacts = dr.readArtifactsFromPath(resource.getFile().getAbsolutePath());
        logger.info("ARTIFACTS: " + artifacts.size());
        testing = artifacts.get(4);
		artifacts.remove(testing);
		trainings = artifacts;
		 
		for (Artifact art : trainings) {
			simRes.put(art, new Float(Math.random()));
		}
	}


    
    @Test
    public void testRecommendationWithMockito() throws Exception{
    	when(artifactRepository.findAll()).thenReturn(
        		trainings);
    	String va = testing.getMethodDeclarations().stream().filter(z -> z.getMethodInvocations().size() >4).findFirst().get().getName();
    	Map<String, Float> res = car.recommends(trainings, testing, va);
    	
    	assertNotNull(res);
    	int count = 0;
    	for(String reco : res.keySet()) {
        	if(count<10)
        		logger.info(String.format("%s with value %f", reco, res.get(reco)));
        	count++;
        }
    }

    @Test
    public void testFullsRecommendation() throws Exception{
    	when(simManager.appliableProjects(any())).thenReturn(
        		trainings);
    	
    	when(fsc.computeSimilarity(any(), any())).thenReturn(
    			simRes);
    	Query q = new Query();
    	q.setFocusInput(new FocusInput());
    	String va = testing.getMethodDeclarations().stream().filter(z -> z.getMethodInvocations().size() >4).findFirst().get().getName();
    	q.getFocusInput().setActiveDeclaration(va);
    	q.getFocusInput().setMethodDeclarations(testing.getMethodDeclarations());
    	Recommendation res = car.getRecommendation(q); 
				
		int count = 0;
        for(RecommendationItem reco : res.getRecommendationItems()) {
        	if(count>10) break;
    		for(Entry<String, Float> val : reco.getApiFunctionCallFOCUS().entrySet()) 
    			logger.info(String.format("%s with value %f",val.getKey(), val.getValue()));
        	count++;
        }
    	assertNotNull(res);
    }
}
