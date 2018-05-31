{
    StatefulKnowledgeSession ksession;
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " ,
    new DoNothingWorkItemHandler());
    ProcessInstance processInstance = ksession
    .startProcess( " a string " );
    assertTrue(processInstance.getState() == ProcessInstance.STATE_ACTIVE);
    assertProcessInstanceActive(processInstance.getId(), ksession);
    assertProcessInstanceActive(processInstance.getId(), ksession);
    ksession.abortProcessInstance(processInstance.getId());
}    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " ,    .startProcess( "  " a string "  " );    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " ,    .startProcess( "  " a string "  " );}    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " ,    .startProcess( "  "  " a string "  "  " );
