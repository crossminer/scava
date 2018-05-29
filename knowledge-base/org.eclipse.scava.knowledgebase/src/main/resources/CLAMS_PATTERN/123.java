{
    Logger logger;
    Map<String, Object> params;
    StatefulKnowledgeSession ksession;
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
    .newKnowledgeBuilder();
    kbuilder.add(ResourceFactory
    .newClassPathResource( " a string " ),
    ResourceType.BPMN2);
    kbuilder.add(ResourceFactory
    .newClassPathResource( " a string " ),
    ResourceType.BPMN2);
    if (!kbuilder.getErrors().isEmpty()) {
        for (KnowledgeBuilderError error : kbuilder.getErrors()) {
            logger.error(error.toString());
        }
    }
    KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
    kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
    ProcessInstance processInstance = ksession.startProcess(
                                           " a string " , params);
    assertTrue(processInstance.getState() == ProcessInstance.STATE_COMPLETED);
    assertEquals( " a string " ,
                 ((WorkflowProcessInstance) processInstance).getVariable( " a string " ));
}    .newClassPathResource( "  " a string "  " ),    .newClassPathResource( "  " a string "  " ),                                           "  " a string "  " , params);    assertEquals( "  " a string "  " ,                 ((WorkflowProcessInstance) processInstance).getVariable( "  " a string "  " ));    .newClassPathResource( "  " a string "  " ),    .newClassPathResource( "  " a string "  " ),                                           "  " a string "  " , params);    assertEquals( "  " a string "  " ,                 ((WorkflowProcessInstance) processInstance).getVariable( "  " a string "  " ));}    .newClassPathResource( "  "  " a string "  "  " ),    .newClassPathResource( "  "  " a string "  "  " ),                                           "  "  " a string "  "  " , params);    assertEquals( "  "  " a string "  "  " ,                 ((WorkflowProcessInstance) processInstance).getVariable( "  "  " a string "  "  " ));
