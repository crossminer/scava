{
    Person person;
    Reader source;
    List<String> list;
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    kbuilder.add( ResourceFactory.newReaderResource( source ), ResourceType.DRF );
    KnowledgeBase kbase = kbuilder.newKnowledgeBase();
    StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
    ksession.setGlobal( " a string " , list);
    ProcessInstance processInstance = ksession.startProcess( " a string " );
    assertEquals(ProcessInstance.STATE_ACTIVE, processInstance.getState());
    ksession.insert(person);
    assertEquals(ProcessInstance.STATE_COMPLETED, processInstance.getState());
}    ksession.setGlobal( "  " a string "  " , list);    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " );    ksession.setGlobal( "  " a string "  " , list);    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " );}    ksession.setGlobal( "  "  " a string "  "  " , list);    ProcessInstance processInstance = ksession.startProcess( "  "  " a string "  "  " );
