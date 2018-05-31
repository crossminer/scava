{
    StatefulKnowledgeSession ksession;
    TestWorkItemHandler workItemHandler;
    Map<String, Object> res;
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " ,
    workItemHandler);
    ProcessInstance processInstance = ksession.startProcess( " a string " );
    assertTrue(processInstance.getState() == ProcessInstance.STATE_ACTIVE);

    ksession.getWorkItemManager().completeWorkItem(
        workItemHandler.getWorkItem().getId(), res);

    assertProcessInstanceCompleted(processInstance.getId(), ksession);
}    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " ,    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " );    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " ,    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " );}    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " ,    ProcessInstance processInstance = ksession.startProcess( "  "  " a string "  "  " );
