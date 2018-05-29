{
    Map<String, Object> params;
    StatefulKnowledgeSession ksession;
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " , new SendTaskHandler());
    WorkflowProcessInstance processInstance = (WorkflowProcessInstance)
    ksession.startProcess( " a string " , params);
    assertProcessInstanceCompleted(processInstance.getId(), ksession);
}    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new SendTaskHandler());    ksession.startProcess( "  " a string "  " , params);    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new SendTaskHandler());    ksession.startProcess( "  " a string "  " , params);}    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , new SendTaskHandler());    ksession.startProcess( "  "  " a string "  "  " , params);
