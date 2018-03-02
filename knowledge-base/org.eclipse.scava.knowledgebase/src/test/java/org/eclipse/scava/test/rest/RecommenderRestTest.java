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

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.eclipse.scava.Application;
import org.eclipse.scava.business.ISimilarityCalculator;
import org.eclipse.scava.business.impl.RecommenderManager;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.presentation.rest.RecommenderRestController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
@TestPropertySource(locations="classpath:application.properties")
public class RecommenderRestTest {

	private MockMvc mockMvc;

    @Mock
    private ArtifactRepository artifactRepository;
    
    @Mock
    private RecommenderManager recommenderManager;

    @InjectMocks
    private RecommenderRestController recommenderController;
    
    @Autowired
    @Qualifier("Dependency")
    private ISimilarityCalculator simCalc;

    @Before
    public void init(){
        mockMvc = MockMvcBuilders
                .standaloneSetup(recommenderController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers(new ViewResolver() {
                    @Override
                    public View resolveViewName(String viewName, Locale locale) throws Exception {
                        return new MappingJackson2JsonView();
                    }
                })
                .build();
    }

    @Test
    public void getArtifactsOkTest() throws Exception {
    	Artifact art1 = new Artifact();
    	art1.setName("Name1");
    	Artifact art2 = new Artifact();
    	art2.setName("Name2");
        List<Artifact> artifacts = Arrays.asList(art1,art2);
        Page<Artifact> foundPage = new PageImpl<>(artifacts);
        when(artifactRepository.findAll(any(Pageable.class)))
        	.thenReturn(foundPage);
        ResultActions v = mockMvc.perform(get("/api/recommendation/artifacts"));
        MvcResult r = v.andReturn();
        mockMvc.perform(get("/api/recommendation/artifacts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.content", hasSize(2)));
    }
    
    
    @Test
    public void getSimilarProjectTest() throws Exception {
        List<Artifact> artifacts = Arrays.asList(
                new Artifact(),
                new Artifact(),
        		new Artifact(),
        		new Artifact(),
        		new Artifact());
        when(recommenderManager.getSimilarProjects(anyString(), anyString(), anyInt())).thenReturn(
        		artifacts);
        mockMvc.perform(get("/api/recommendation/similar/p/{id}/m/{sim_method}/n/{num}","id","methodName",5))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(5)));
    }
    
    @Test
    public void getArtifactByQuery() throws Exception {
    	List<Artifact> artifacts = Arrays.asList(
                new Artifact(),
                new Artifact(),
        		new Artifact(),
        		new Artifact(),
        		new Artifact());
    	when(recommenderManager.getArtifactsByQuery(anyString())).thenReturn(artifacts);
    	mockMvc.perform(get("/api/recommendation/search/{text}","tetx"))
    			.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(5)));
    }
//    @Test
//    public void getRecommendationTest() throws Exception {
//		Query q = defineQuery();
//		List<Artifact> artifacts = Arrays.asList(
//                new Artifact(),
//                new Artifact(),
//        		new Artifact(),
//        		new Artifact(),
//        		new Artifact());
//        when(recommenderManager.getSimilarProjects(anyString(), anyString(), anyInt())).thenReturn(
//        		artifacts);
//        ResultActions s = mockMvc.perform(post("/api/recommendation/")
//    			.contentType(MediaType.APPLICATION_JSON)
//    			.content(new ObjectMapper().writeValueAsString(q)));
//        System.out.println(s.andReturn().getResponse().getContentAsString());
//        mockMvc.perform(post("/api/recommendation/")
//        			.contentType(MediaType.APPLICATION_JSON)
//        			.content(new ObjectMapper().writeValueAsString(q)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.recommendationItems", hasSize(0)));
//    }
//
//	private Query defineQuery() {
//		Query q = new Query();
//		q.setCompilationUnit("package org.scava.business.dto;\n"
//				+"public class Parameter {\n"
//				+"private String name;\n"
//				+"private String type;\n"
//				+"private boolean isCollection;\n"
//				+"public String getName() {\n"
//				+"	return name;\n"
//				+"}\n"
//				+"public void setName(String name) {\n"
//				+"	this.name = name;\n"
//				+"}\n"
//				+"public String getType() {\n"
//				+"	return type;\n"
//				+"}\n"
//				+"public void setType(String type) {\n"
//				+"	this.type = type;\n"
//				+"}\n"
//				+"public boolean isCollection() {\n"
//				+"	return isCollection;\n"
//				+"}\n"
//				+"public void setCollection(boolean isCollection) {\n"
//				+"	this.isCollection = isCollection;\n"
//				+"}\n"
//				+"}\n");
//		q.setCodeSupportRecommendation(false);
//		q.setUpdateVersionRecommendation(false);
//		q.setProjectAlternativesRecommendation(true);
//		q.setComments(new ArrayList<String>());
//		q.getComments().add("TODO");
//		q.getComments().add("connect to Api with gsoup");
//		Dependency d1 = new Dependency();
//		d1.setName("org.springframework.data.spring-data-mongodb");
//		d1.setVersion("1.10.4.RELEASE");
//		d1.setUrl("");
//		d1.setArtifactID("59415e3e3d6deb663c95b58e");
//		Dependency d2 = new Dependency();
//		d2.setName("org.apache.lucene.lucene-core");
//		d2.setVersion("6.0.0");
//		d2.setUrl("");
//		d2.setArtifactID("59415e403d6deb663c95b590");
//		Dependency d3 = new Dependency();
//		d3.setName("log4j.log4j");
//		d3.setVersion("1.2.17");
//		d3.setUrl("");
//		d3.setArtifactID("59415e3f3d6deb663c95b58f");
//		q.setProjectDependencies(new ArrayList<Dependency>());
//		q.getProjectDependencies().add(d1);
//		q.getProjectDependencies().add(d2);
//		q.getProjectDependencies().add(d3);
//		q.setClassDependencies(new ArrayList<Dependency>());
//		q.getClassDependencies().add(d2);
//		q.setMethodInvocation("addDocument");
//		q.setRefClassInvocation("IndexWriter");
//		q.setProjectName("MDEForge");
//		q.setTextOffset(1000);
//		return q;
//	}
}
