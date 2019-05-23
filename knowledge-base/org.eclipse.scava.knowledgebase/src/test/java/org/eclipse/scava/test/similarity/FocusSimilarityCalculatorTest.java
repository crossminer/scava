/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.test.similarity;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.scava.business.IAggregatedSimilarityCalculator;
import org.eclipse.scava.business.impl.DataReader;
import org.eclipse.scava.business.model.Artifact;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Table;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class FocusSimilarityCalculatorTest {
	@Autowired
	@Qualifier("Focus")
	private IAggregatedSimilarityCalculator focus;
	@Autowired
	private DataReader dr;

	private static final Logger logger = LoggerFactory.getLogger(FocusSimilarityCalculatorTest.class);
	List<Artifact> artifacts;

	@Before
	public void init() throws IOException {
		Resource resource = new ClassPathResource("FOCUS");
        artifacts = dr.readArtifactsFromPath(resource.getFile().getAbsolutePath());
	}

	@Test
	public void focusCommutativeTest() {
		Map<String, String> parameters = new HashMap<>();
		Table<String, String, Double> table = focus.calculateAggregatedSimilarityValues(artifacts, parameters);
		double val1 = table.get(artifacts.get(0).getFullName(), artifacts.get(1).getFullName());
		double val2 = table.get(artifacts.get(1).getFullName(), artifacts.get(0).getFullName());
		assertEquals(val1, val2, 0.0);
	}

	@Test
	public void focusSimIdentityTest() {
		Map<String, String> parameters = new HashMap<>();
		Table<String, String, Double> table = focus.calculateAggregatedSimilarityValues(artifacts, parameters);
		double val1 = table.get(artifacts.get(0).getFullName(), artifacts.get(0).getFullName());
		assertEquals(val1, 1, 0.0);
	}
}
