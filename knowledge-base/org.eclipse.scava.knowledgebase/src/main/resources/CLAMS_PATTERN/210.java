{
    Reader source;
    List<String> list;
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    kbuilder.add(new ReaderResource(source), ResourceType.DRF);
    KnowledgeBase kbase = kbuilder.newKnowledgeBase();
    StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
    ksession.setGlobal( " a string " , list);
    ProcessInstance processInstance =
    ksession.startProcess( " a string " );
    Collection<FactHandle> factHandles = ksession.getFactHandles(new ObjectFilter() {
        // Do something
    });
    assertEquals(ProcessInstance.STATE_COMPLETED, processInstance.getState());
    // Do something with factHandles

}    ksession.setGlobal( "  " a string "  " , list);    ksession.startProcess( "  " a string "  " );    ksession.setGlobal( "  " a string "  " , list);    ksession.startProcess( "  " a string "  " );}    ksession.setGlobal( "  "  " a string "  "  " , list);    ksession.startProcess( "  "  " a string "  "  " );
