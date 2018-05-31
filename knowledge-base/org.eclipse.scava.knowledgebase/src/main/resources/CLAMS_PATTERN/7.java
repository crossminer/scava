{
    StatefulKnowledgeSession ksession;
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " , new DoNothingWorkItemHandler());
    ProcessInstance processInstance = ksession.startProcess( " a string " );
    assertProcessInstanceCompleted(processInstance.getId(), ksession);
}    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new DoNothingWorkItemHandler());    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " );    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new DoNothingWorkItemHandler());    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " );}    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , new DoNothingWorkItemHandler());    ProcessInstance processInstance = ksession.startProcess( "  "  " a string "  "  " );
