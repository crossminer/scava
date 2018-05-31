{
    Patient salaboy;
    HashMap<String, Object> input;
    ExecutorServiceEntryPoint executor;
    StatefulKnowledgeSession session;
    input.put( " a string " , salaboy.getId());
    SessionStoreUtil.sessionCache.put( " a string " +session.getId(), session);
    AsyncGenericWorkItemHandler genericHandler = new AsyncGenericWorkItemHandler(executor, session.getId());

    session.getWorkItemManager().registerWorkItemHandler( " a string " , genericHandler);
    session.getWorkItemManager().registerWorkItemHandler( " a string " , genericHandler);
    session.getWorkItemManager().registerWorkItemHandler( " a string " , genericHandler);
    session.getWorkItemManager().registerWorkItemHandler( " a string " , genericHandler);
    session.getWorkItemManager().registerWorkItemHandler( " a string " , genericHandler);

    WorkflowProcessInstance pI = (WorkflowProcessInstance) session.startProcess( " a string " , input);
    assertEquals(ProcessInstance.STATE_COMPLETED, pI.getState());
    assertEquals(Boolean.TRUE, pI.getVariable( " a string " ));
    assertEquals(number, ((BigDecimal)pI.getVariable( " a string " )).intValue());

}    input.put( "  " a string "  " , salaboy.getId());    SessionStoreUtil.sessionCache.put( "  " a string "  " +session.getId(), session);    session.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , genericHandler);    session.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , genericHandler);    session.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , genericHandler);    session.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , genericHandler);    session.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , genericHandler);    WorkflowProcessInstance pI = (WorkflowProcessInstance) session.startProcess( "  " a string "  " , input);    assertEquals(Boolean.TRUE, pI.getVariable( "  " a string "  " ));    assertEquals(number, ((BigDecimal)pI.getVariable( "  " a string "  " )).intValue());    input.put( "  " a string "  " , salaboy.getId());    SessionStoreUtil.sessionCache.put( "  " a string "  " +session.getId(), session);    session.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , genericHandler);    session.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , genericHandler);    session.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , genericHandler);    session.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , genericHandler);    session.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , genericHandler);    WorkflowProcessInstance pI = (WorkflowProcessInstance) session.startProcess( "  " a string "  " , input);    assertEquals(Boolean.TRUE, pI.getVariable( "  " a string "  " ));    assertEquals(number, ((BigDecimal)pI.getVariable( "  " a string "  " )).intValue());}    input.put( "  "  " a string "  "  " , salaboy.getId());    SessionStoreUtil.sessionCache.put( "  "  " a string "  "  " +session.getId(), session);    session.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , genericHandler);    session.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , genericHandler);    session.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , genericHandler);    session.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , genericHandler);    session.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , genericHandler);    WorkflowProcessInstance pI = (WorkflowProcessInstance) session.startProcess( "  "  " a string "  "  " , input);    assertEquals(Boolean.TRUE, pI.getVariable( "  "  " a string "  "  " ));    assertEquals(number, ((BigDecimal)pI.getVariable( "  "  " a string "  "  " )).intValue());
