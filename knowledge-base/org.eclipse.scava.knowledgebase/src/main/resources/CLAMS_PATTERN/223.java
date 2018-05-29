{
    StatefulKnowledgeSession ksession;
    TestWorkItemHandler handler;
    Person person;
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " ,
    handler);
    ProcessInstance processInstance = ksession.startProcess( " a string " );

    ksession.getWorkItemManager().completeWorkItem(handler.getWorkItem().getId(), null);
    ksession.insert(person);
    ksession.getWorkItemManager().completeWorkItem(handler.getWorkItem().getId(), null);
    assertTrue(processInstance.getState() == ProcessInstance.STATE_COMPLETED);
    assertNodeTriggered(processInstance.getId(),  " a string " ,  " a string " ,  " a string " ,  " a string " );
}    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " ,    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " );    assertNodeTriggered(processInstance.getId(),  "  " a string "  " ,  "  " a string "  " ,  "  " a string "  " ,  "  " a string "  " );    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " ,    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " );    assertNodeTriggered(processInstance.getId(),  "  " a string "  " ,  "  " a string "  " ,  "  " a string "  " ,  "  " a string "  " );}    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " ,    ProcessInstance processInstance = ksession.startProcess( "  "  " a string "  "  " );    assertNodeTriggered(processInstance.getId(),  "  "  " a string "  "  " ,  "  "  " a string "  "  " ,  "  "  " a string "  "  " ,  "  "  " a string "  "  " );
