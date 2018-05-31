{
    HashMap<String, Object> input;
    ExecutorServiceEntryPoint executor;
    StatefulKnowledgeSession session;
    AsyncGenericWorkItemHandler asyncHandler = new AsyncGenericWorkItemHandler(executor, session.getId());
    session.getWorkItemManager().registerWorkItemHandler( " a string " , asyncHandler);

    WorkflowProcessInstance pI = (WorkflowProcessInstance) session.startProcess( " a string " , input);

    assertEquals(ProcessInstance.STATE_COMPLETED, pI.getState());
}    session.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , asyncHandler);    WorkflowProcessInstance pI = (WorkflowProcessInstance) session.startProcess( "  " a string "  " , input);    session.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , asyncHandler);    WorkflowProcessInstance pI = (WorkflowProcessInstance) session.startProcess( "  " a string "  " , input);}    session.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , asyncHandler);    WorkflowProcessInstance pI = (WorkflowProcessInstance) session.startProcess( "  "  " a string "  "  " , input);
