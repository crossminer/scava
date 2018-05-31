{
    Properties properties;
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    kbuilder.add(ResourceFactory.newClassPathResource( " a string " ), ResourceType.BPMN2);
    KnowledgeBase kbase = kbuilder.newKnowledgeBase();
    KnowledgeSessionConfiguration config = KnowledgeBaseFactory.newKnowledgeSessionConfiguration(properties);
    StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession(config, EnvironmentFactory.newEnvironment());
    ksession.startProcess( " a string " );
}    kbuilder.add(ResourceFactory.newClassPathResource( "  " a string "  " ), ResourceType.BPMN2);    ksession.startProcess( "  " a string "  " );    kbuilder.add(ResourceFactory.newClassPathResource( "  " a string "  " ), ResourceType.BPMN2);    ksession.startProcess( "  " a string "  " );}    kbuilder.add(ResourceFactory.newClassPathResource( "  "  " a string "  "  " ), ResourceType.BPMN2);    ksession.startProcess( "  "  " a string "  "  " );
