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

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.eclipse.scava.business.impl.ActiveDeclarationNotFoundException;
import org.eclipse.scava.business.impl.DataReader;
import org.eclipse.scava.business.impl.FocusContexAwareRecommender;
import org.eclipse.scava.business.model.Artifact;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class FocusTest {

	
	@Autowired DataReader dr;
	@Autowired
	private FocusContexAwareRecommender car;
	private static final Logger logger = LoggerFactory.getLogger(FocusTest.class);
	private List<Artifact> trainings;
	private Artifact testing;
	@Before
	public void init() throws IOException {
		List<Artifact> artifacts = dr.readArtifactsFromPath("FOCUS");
		testing = artifacts.get(0);
		logger.info(testing.getFullName());
		artifacts.remove(testing);
		trainings = artifacts;
	}


    @Ignore
    @Test
    public void testFewsRecommendation() throws IOException, ActiveDeclarationNotFoundException{
    	Map<String, Float> res = car.recommends(trainings, testing, "rss/dao/CustomerDAO/update(java.lang.String,rrs.model.Customer)");
    	assertNotNull(res);
    }
    @Test
    public void testFullsRecommendation() throws IOException, ActiveDeclarationNotFoundException{
		Map<String, Float> res = car.recommends(trainings, testing, 
				testing.getMethodDeclarations().stream().filter(z->z.getMethodInvocations().size()>4).findFirst().get().getName());//testing.getMethodDeclarations().get(1).getName());
		int count = 0;
        for(String reco : res.keySet()) {
        	if(count<10)
        		logger.info(String.format("%s with value %f", reco, res.get(reco)));
        	count++;
        }
    	assertNotNull(res);
    }
}
