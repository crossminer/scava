{
    Map<String, Object> params;
    StatefulKnowledgeSession ksession;
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " , new SystemOutWorkItemHandler());
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " , new SystemOutWorkItemHandler());
    ProcessInstance processInstance = ksession.startProcess( " a string " , params);
    assertTrue(processInstance.getState() == ProcessInstance.STATE_COMPLETED);
}    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new SystemOutWorkItemHandler());    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new SystemOutWorkItemHandler());    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " , params);    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new SystemOutWorkItemHandler());    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new SystemOutWorkItemHandler());    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " , params);}    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , new SystemOutWorkItemHandler());    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , new SystemOutWorkItemHandler());    ProcessInstance processInstance = ksession.startProcess( "  "  " a string "  "  " , params);
