{
    StatefulKnowledgeSession ksession;
    ProcessInstance processInstance = ksession.startProcess( " a string " );
    assertTrue(processInstance.getState() == ProcessInstance.STATE_ACTIVE);
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " , new DoNothingWorkItemHandler());
    ksession.signalEvent( " a string " , null, processInstance.getId());
    assertProcessInstanceActive(processInstance.getId(), ksession);
    ksession.signalEvent( " a string " , null, processInstance.getId());
    assertProcessInstanceActive(processInstance.getId(), ksession);
    ksession.insert(new Person());
    ksession.signalEvent( " a string " , null, processInstance.getId());
    assertProcessInstanceCompleted(processInstance.getId(), ksession);
}    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " );    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new DoNothingWorkItemHandler());    ksession.signalEvent( "  " a string "  " , null, processInstance.getId());    ksession.signalEvent( "  " a string "  " , null, processInstance.getId());    ksession.signalEvent( "  " a string "  " , null, processInstance.getId());    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " );    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new DoNothingWorkItemHandler());    ksession.signalEvent( "  " a string "  " , null, processInstance.getId());    ksession.signalEvent( "  " a string "  " , null, processInstance.getId());    ksession.signalEvent( "  " a string "  " , null, processInstance.getId());}    ProcessInstance processInstance = ksession.startProcess( "  "  " a string "  "  " );    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , new DoNothingWorkItemHandler());    ksession.signalEvent( "  "  " a string "  "  " , null, processInstance.getId());    ksession.signalEvent( "  "  " a string "  "  " , null, processInstance.getId());    ksession.signalEvent( "  "  " a string "  "  " , null, processInstance.getId());
