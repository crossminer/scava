{
    StatefulKnowledgeSession ksession;
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " , new DoNothingWorkItemHandler());
    ProcessInstance processInstance = ksession.startProcess( " a string " );
    assertTrue(processInstance.getState() == ProcessInstance.STATE_ACTIVE);
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " , new DoNothingWorkItemHandler());
    ksession.fireAllRules();
    assertProcessInstanceCompleted(processInstance.getId(), ksession);
}    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new DoNothingWorkItemHandler());    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " );    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new DoNothingWorkItemHandler());    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new DoNothingWorkItemHandler());    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " );    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new DoNothingWorkItemHandler());}    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , new DoNothingWorkItemHandler());    ProcessInstance processInstance = ksession.startProcess( "  "  " a string "  "  " );    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , new DoNothingWorkItemHandler());
