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
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.business.IRecommendationProvider;
import org.eclipse.scava.business.dto.Query;
import org.eclipse.scava.business.dto.Recommendation;
import org.eclipse.scava.business.integration.PatternRepository;
import org.eclipse.scava.business.model.Pattern;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application.properties")
public class ApiCallRecommendationTest {
	
	private static final Logger logger = LoggerFactory.getLogger(ApiCallRecommendationTest.class);

	private static Pattern pattern;
	
	@Before
	public void init() {
		pattern = new Pattern();
		pattern.setId("5b2147e90d140147e8180c48");
		pattern.setPatternCode("{\n    DeserializationConfig config;\n    "
				+ "BeanProperty property;\n   "
				+ " DeserializerProvider p;\n    "
				+ "ArrayType type;\n    "
				+ "JsonDeserializer<?> deser;\n    "
				+ "JavaType elemType = type.getContentType();\n\n    "
				+ "JsonDeserializer<Object> contentDeser = elemType.getValueHandler();\n    "
				+ "if (contentDeser == null) {\n        "
				+ "if (deser != null) {\n            "
				+ "JsonDeserializer<?> custom = _findCustomArrayDeserializer(type, config, p, property, null, null);\n        "
				+ "}\n        "
				+ "if (elemType.isPrimitive()) "
				+ "{\n            "
				+ "// Do something\n        }\n    "
				+ "TypeDeserializer elemTypeDeser = elemType.getTypeHandler();\n   "
				+ " if (elemTypeDeser == null) {\n        "
				+ "elemTypeDeser = findTypeDeserializer(config, elemType, property);\n    }\n    "
				+ "JsonDeserializer<?> custom = _findCustomArrayDeserializer(type, config, p, property, elemTypeDeser, contentDeser);\n   "
				+ " return new ObjectArrayDeserializer(type, contentDeser, elemTypeDeser);\n    "
				+ "// Do something with custom\n\n}");;
//		
//		Pa
//		{
//		    "_id" : ObjectId("5b2147e90d140147e8180c48"),
//		    "_class" : "org.eclipse.scava.business.model.Pattern",
//		    "patternCode" : "{\n    DeserializationConfig config;\n    BeanProperty property;\n    DeserializerProvider p;\n    ArrayType type;\n    JsonDeserializer<?> deser;\n    JavaType elemType = type.getContentType();\n\n    JsonDeserializer<Object> contentDeser = elemType.getValueHandler();\n    if (contentDeser == null) {\n        if (deser != null) {\n            JsonDeserializer<?> custom = _findCustomArrayDeserializer(type, config, p, property, null, null);\n        }\n        if (elemType.isPrimitive()) {\n            // Do something\n        }\n    }\n    TypeDeserializer elemTypeDeser = elemType.getTypeHandler();\n    if (elemTypeDeser == null) {\n        elemTypeDeser = findTypeDeserializer(config, elemType, property);\n    }\n    JsonDeserializer<?> custom = _findCustomArrayDeserializer(type, config, p, property, elemTypeDeser, contentDeser);\n    return new ObjectArrayDeserializer(type, contentDeser, elemTypeDeser);\n    // Do something with custom\n\n}",
//		    "patternFileName" : "lucid-jackson-core-asl-1.9.10_83.java"
//		}
	}
	
	@Autowired
	private PatternRepository patternRepository;

	
	@Autowired
	@Qualifier("ApiCallRecommendation")
	private IRecommendationProvider apiCallRecommendation;
	
	private static final String method = "JavaType myType = oldType.getContentType();\n "
			+ "JsonDeserializer<Object> myDeserializer = myType.getValueHandler();\n "
			+ "TypeDeserializer myTypeDeserializer = myType.getTypeHandler();";
	
	@Test
	public void apiCallRecommendationTest() throws Exception {
		Query query = new Query();
		query.setCurrentMethodCode(method);
		List<Pattern> patterns = new ArrayList<Pattern>();
		patterns.add(pattern);
		
		when(patternRepository.findAll())
    	.thenReturn(patterns);
		
		Recommendation v = apiCallRecommendation.getRecommendation(query);
		assertEquals(1, v.getRecommendationItems().size());
	}
}
