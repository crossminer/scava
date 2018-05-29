{
    Properties properties;
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    kbuilder.add(ResourceFactory.newClassPathResource( " a string " ), ResourceType.BPMN2);
    KnowledgeBase kbase = kbuilder.newKnowledgeBase();
    KnowledgeSessionConfiguration config = KnowledgeBaseFactory.newKnowledgeSessionConfiguration(properties);
    StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession(config, EnvironmentFactory.newEnvironment());
    ProcessInstance processInstance = ksession.startProcess( " a string " );
    ProcessInstance processInstance2 = ksession.startProcess( " a string " );
    // Do something with processInstance

    // Do something with processInstance2
}    kbuilder.add(ResourceFactory.newClassPathResource( "  " a string "  " ), ResourceType.BPMN2);    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " );    ProcessInstance processInstance2 = ksession.startProcess( "  " a string "  " );    kbuilder.add(ResourceFactory.newClassPathResource( "  " a string "  " ), ResourceType.BPMN2);    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " );    ProcessInstance processInstance2 = ksession.startProcess( "  " a string "  " );}    kbuilder.add(ResourceFactory.newClassPathResource( "  "  " a string "  "  " ), ResourceType.BPMN2);    ProcessInstance processInstance = ksession.startProcess( "  "  " a string "  "  " );    ProcessInstance processInstance2 = ksession.startProcess( "  "  " a string "  "  " );
