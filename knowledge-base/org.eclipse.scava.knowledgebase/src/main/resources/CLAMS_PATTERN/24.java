{
    StatefulKnowledgeSession ksession;
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " , new DoNothingWorkItemHandler());
    ProcessInstance processInstance = ksession.startProcess( " a string " );
    assertTrue(processInstance.getState() == ProcessInstance.STATE_ACTIVE);
    ksession.signalEvent( " a string " ,  " a string " , processInstance.getId());
    assertProcessInstanceCompleted(processInstance.getId(), ksession);
}    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new DoNothingWorkItemHandler());    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " );    ksession.signalEvent( "  " a string "  " ,  "  " a string "  " , processInstance.getId());    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new DoNothingWorkItemHandler());    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " );    ksession.signalEvent( "  " a string "  " ,  "  " a string "  " , processInstance.getId());}    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , new DoNothingWorkItemHandler());    ProcessInstance processInstance = ksession.startProcess( "  "  " a string "  "  " );    ksession.signalEvent( "  "  " a string "  "  " ,  "  "  " a string "  "  " , processInstance.getId());
