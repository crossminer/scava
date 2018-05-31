{
    TestWorkItemHandler workItemHandler;
    Map<String, Object> results;
    StatefulKnowledgeSession ksession;
    WorkItem workItem;
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " , workItemHandler);
    ProcessInstance processInstance = ksession.startProcess( " a string " );
    assertTrue(processInstance.getState() == ProcessInstance.STATE_ACTIVE);
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " , workItemHandler);
    assertEquals( " a string " , workItem.getParameter( " a string " ));
    ksession.getWorkItemManager().completeWorkItem(workItem.getId(), results);
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " , workItemHandler);
    assertEquals( " a string " , workItem.getParameter( " a string " ));
    ksession.getWorkItemManager().completeWorkItem(workItem.getId(), null);
    assertProcessInstanceCompleted(processInstance.getId(), ksession);
}    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , workItemHandler);    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " );    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , workItemHandler);    assertEquals( "  " a string "  " , workItem.getParameter( "  " a string "  " ));    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , workItemHandler);    assertEquals( "  " a string "  " , workItem.getParameter( "  " a string "  " ));    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , workItemHandler);    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " );    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , workItemHandler);    assertEquals( "  " a string "  " , workItem.getParameter( "  " a string "  " ));    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , workItemHandler);    assertEquals( "  " a string "  " , workItem.getParameter( "  " a string "  " ));}    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , workItemHandler);    ProcessInstance processInstance = ksession.startProcess( "  "  " a string "  "  " );    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , workItemHandler);    assertEquals( "  "  " a string "  "  " , workItem.getParameter( "  "  " a string "  "  " ));    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , workItemHandler);    assertEquals( "  "  " a string "  "  " , workItem.getParameter( "  "  " a string "  "  " ));
