{
    StatefulKnowledgeSession ksession;
    ReceiveTaskHandler receiveTaskHandler;
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " ,
    receiveTaskHandler);
    WorkflowProcessInstance processInstance = (WorkflowProcessInstance) ksession
    .startProcess( " a string " );
    assertEquals(ProcessInstance.STATE_ACTIVE, processInstance.getState());
    assertProcessInstanceCompleted(processInstance.getId(), ksession);
}    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " ,    .startProcess( "  " a string "  " );    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " ,    .startProcess( "  " a string "  " );}    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " ,    .startProcess( "  "  " a string "  "  " );
