{
    Map<String, Object> params;
    StatefulKnowledgeSession ksession;
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " , new ServiceTaskHandler());
    WorkflowProcessInstance processInstance = (WorkflowProcessInstance)
    ksession.startProcess( " a string " , params);
    assertProcessInstanceCompleted(processInstance.getId(), ksession);
    assertEquals( " a string " , processInstance.getVariable( " a string " ));
}    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new ServiceTaskHandler());    ksession.startProcess( "  " a string "  " , params);    assertEquals( "  " a string "  " , processInstance.getVariable( "  " a string "  " ));    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new ServiceTaskHandler());    ksession.startProcess( "  " a string "  " , params);    assertEquals( "  " a string "  " , processInstance.getVariable( "  " a string "  " ));}    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , new ServiceTaskHandler());    ksession.startProcess( "  "  " a string "  "  " , params);    assertEquals( "  "  " a string "  "  " , processInstance.getVariable( "  "  " a string "  "  " ));
