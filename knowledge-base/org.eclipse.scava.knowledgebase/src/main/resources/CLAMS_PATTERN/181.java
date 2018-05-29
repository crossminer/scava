{
    StatefulKnowledgeSession ksession;
    List<String> list;
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    kbuilder.add(ResourceFactory.newClassPathResource( " a string " ), ResourceType.BPMN2);
    kbuilder.add(ResourceFactory.newClassPathResource( " a string " ), ResourceType.DRL);
    KnowledgeBase kbase = kbuilder.newKnowledgeBase();
    ksession.setGlobal( " a string " , list);
    ProcessInstance processInstance = ksession.startProcess( " a string " );
    assertTrue(processInstance.getState() == ProcessInstance.STATE_ACTIVE);
    ksession.fireAllRules();
    assertProcessInstanceCompleted(processInstance.getId(), ksession);
    // Do something with kbase

}    kbuilder.add(ResourceFactory.newClassPathResource( "  " a string "  " ), ResourceType.BPMN2);    kbuilder.add(ResourceFactory.newClassPathResource( "  " a string "  " ), ResourceType.DRL);    ksession.setGlobal( "  " a string "  " , list);    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " );    kbuilder.add(ResourceFactory.newClassPathResource( "  " a string "  " ), ResourceType.BPMN2);    kbuilder.add(ResourceFactory.newClassPathResource( "  " a string "  " ), ResourceType.DRL);    ksession.setGlobal( "  " a string "  " , list);    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " );}    kbuilder.add(ResourceFactory.newClassPathResource( "  "  " a string "  "  " ), ResourceType.BPMN2);    kbuilder.add(ResourceFactory.newClassPathResource( "  "  " a string "  "  " ), ResourceType.DRL);    ksession.setGlobal( "  "  " a string "  "  " , list);    ProcessInstance processInstance = ksession.startProcess( "  "  " a string "  "  " );
