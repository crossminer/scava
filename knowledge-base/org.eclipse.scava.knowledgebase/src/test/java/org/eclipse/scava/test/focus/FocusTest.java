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

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
//@TestPropertySource(locations = "classpath:application.properties")
public class FocusTest {

//	@Autowired 
//	private DataReader dr;
//
//	private MockMvc mockMvc;
//	@Mock
//    private ArtifactRepository artifactRepository;
//	@Mock
//	private SimilarityManager simManager;
//    @Mock
//    private FOCUSSimilarityCalculator fsc;
//	@InjectMocks
//	private FocusContexAwareRecommender car;
//	
//	private static final Logger logger = LoggerFactory.getLogger(FocusTest.class);
//	
//	
//	private List<Artifact> trainings;
//	private Artifact testing;
//	private Map<Artifact, Float> simRes = new HashMap<Artifact, Float>();
//	
//	@Before
//	public void init() throws IOException {
//		
//		MockitoAnnotations.initMocks(this);
//        this.mockMvc = MockMvcBuilders.standaloneSetup(car).build();
//        Resource resource = new ClassPathResource("FOCUS/");
//        trainings = dr.readArtifactsFromPath(resource.getFile().getAbsolutePath());
//        logger.info("ARTIFACTS: " + trainings.size());
//        resource = new ClassPathResource("FOCUS/000a7d6989abec22bf0a8336d350d8a97ccda5fa.txt");
//        testing = dr.readArtifactFromFile(resource.getFile().getAbsolutePath());
//		
//		 
//		for (Artifact art : trainings) {
//			simRes.put(art, new Float(Math.random()));
//		}
//	}
//
//
//    
//    @Test
//    public void testRecommendationWithMockito() throws Exception{
//    	when(artifactRepository.findAll()).thenReturn(
//        		trainings);
//    	String va = testing.getMethodDeclarations().stream().filter(z -> z.getMethodInvocations().size() >4).findFirst().get().getName();
//    	Map<String, Float> res = car.recommends(trainings, testing, va);
//    	
//    	assertNotNull(res);
//    	int count = 0;
//    	for(String reco : res.keySet()) {
//        	if(count<10)
//        		logger.info(String.format("%s with value %f", reco, res.get(reco)));
//        	count++;
//        }
//    }
//
//    @Test
//    public void testFullsRecommendation() throws Exception{
//    	when(simManager.appliableProjects(any())).thenReturn(
//        		trainings);
//    	
//    	when(fsc.computeSimilarity(any(), any())).thenReturn(
//    			simRes);
//    	Query q = new Query();
//    	q.setFocusInput(new FocusInput());
//    	String va = testing.getMethodDeclarations().stream().filter(z -> z.getMethodInvocations().size() >4).findFirst().get().getName();
//    	q.getFocusInput().setActiveDeclaration(va);
//    	q.getFocusInput().setMethodDeclarations(testing.getMethodDeclarations());
//    	Recommendation res = car.getRecommendation(q); 
//				
//		int count = 0;
//        for(RecommendationItem reco : res.getRecommendationItems()) {
//        	if(count>10) break;
//    		for(Entry<String, Float> val : reco.getApiFunctionCallFOCUS().entrySet()) 
//    			logger.info(String.format("%s with value %f",val.getKey(), val.getValue()));
//        	count++;
//        }
//    	assertNotNull(res);
//    }
}
