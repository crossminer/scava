{
    Map<String, Object> params;
    StatefulKnowledgeSession ksession;
    KnowledgeBaseFactory.setKnowledgeBaseServiceFactory(new KnowledgeBaseFactoryServiceImpl());
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " , new ServiceTaskHandler(ksession));
    WorkflowProcessInstance processInstance = (WorkflowProcessInstance) ksession.startProcess( " a string " , params);
    String variable = (String) processInstance.getVariable( " a string " );
    assertEquals(ProcessInstance.STATE_COMPLETED, processInstance.getState());
    // Do something with variable

}    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new ServiceTaskHandler(ksession));    WorkflowProcessInstance processInstance = (WorkflowProcessInstance) ksession.startProcess( "  " a string "  " , params);    String variable = (String) processInstance.getVariable( "  " a string "  " );    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new ServiceTaskHandler(ksession));    WorkflowProcessInstance processInstance = (WorkflowProcessInstance) ksession.startProcess( "  " a string "  " , params);    String variable = (String) processInstance.getVariable( "  " a string "  " );}    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , new ServiceTaskHandler(ksession));    WorkflowProcessInstance processInstance = (WorkflowProcessInstance) ksession.startProcess( "  "  " a string "  "  " , params);    String variable = (String) processInstance.getVariable( "  "  " a string "  "  " );
