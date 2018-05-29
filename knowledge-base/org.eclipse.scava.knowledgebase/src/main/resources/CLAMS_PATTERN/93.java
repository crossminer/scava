{
    StatefulKnowledgeSession ksession;
    List<String> myList;
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " ,
    new SystemOutWorkItemHandler());
    ksession.setGlobal( " a string " , myList);
    ProcessInstance processInstance = ksession
    .startProcess( " a string " );
    assertTrue(processInstance.getState() == ProcessInstance.STATE_COMPLETED);
}    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " ,    ksession.setGlobal( "  " a string "  " , myList);    .startProcess( "  " a string "  " );    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " ,    ksession.setGlobal( "  " a string "  " , myList);    .startProcess( "  " a string "  " );}    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " ,    ksession.setGlobal( "  "  " a string "  "  " , myList);    .startProcess( "  "  " a string "  "  " );
