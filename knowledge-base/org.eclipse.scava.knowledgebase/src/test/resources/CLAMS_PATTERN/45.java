{
    HashMap<String, Object> input;
    StatefulKnowledgeSession session;
    session.getWorkItemManager().registerWorkItemHandler( " a string " , new ServiceTaskHandler());

    WorkflowProcessInstance pI = (WorkflowProcessInstance) session.startProcess( " a string " , input);

    assertEquals(ProcessInstance.STATE_COMPLETED, pI.getState());
    assertEquals(Boolean.TRUE, pI.getVariable( " a string " ));
    System.out.println( " a string "  + pI.getVariable( " a string " ));
}    session.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new ServiceTaskHandler());    WorkflowProcessInstance pI = (WorkflowProcessInstance) session.startProcess( "  " a string "  " , input);    assertEquals(Boolean.TRUE, pI.getVariable( "  " a string "  " ));    System.out.println( "  " a string "  "  + pI.getVariable( "  " a string "  " ));    session.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new ServiceTaskHandler());    WorkflowProcessInstance pI = (WorkflowProcessInstance) session.startProcess( "  " a string "  " , input);    assertEquals(Boolean.TRUE, pI.getVariable( "  " a string "  " ));    System.out.println( "  " a string "  "  + pI.getVariable( "  " a string "  " ));}    session.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , new ServiceTaskHandler());    WorkflowProcessInstance pI = (WorkflowProcessInstance) session.startProcess( "  "  " a string "  "  " , input);    assertEquals(Boolean.TRUE, pI.getVariable( "  "  " a string "  "  " ));    System.out.println( "  "  " a string "  "  "  + pI.getVariable( "  "  " a string "  "  " ));
