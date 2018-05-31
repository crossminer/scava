{
    StatefulKnowledgeSession ksession;
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " ,
    new TestWorkItemHandler());
    ksession.addEventListener(new DefaultProcessEventListener() {
        // Do something
    });
    ProcessInstance processInstance = ksession.startProcess( " a string " );
    ksession.signalEvent( " a string " ,  " a string " );
    assertProcessInstanceCompleted(processInstance.getId(), ksession);
    assertNodeTriggered(processInstance.getId(),  " a string " ,  " a string " ,  " a string " ,  " a string " ,  " a string " );
}    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " ,    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " );    ksession.signalEvent( "  " a string "  " ,  "  " a string "  " );    assertNodeTriggered(processInstance.getId(),  "  " a string "  " ,  "  " a string "  " ,  "  " a string "  " ,  "  " a string "  " ,  "  " a string "  " );    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " ,    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " );    ksession.signalEvent( "  " a string "  " ,  "  " a string "  " );    assertNodeTriggered(processInstance.getId(),  "  " a string "  " ,  "  " a string "  " ,  "  " a string "  " ,  "  " a string "  " ,  "  " a string "  " );}    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " ,    ProcessInstance processInstance = ksession.startProcess( "  "  " a string "  "  " );    ksession.signalEvent( "  "  " a string "  "  " ,  "  "  " a string "  "  " );    assertNodeTriggered(processInstance.getId(),  "  "  " a string "  "  " ,  "  "  " a string "  "  " ,  "  "  " a string "  "  " ,  "  "  " a string "  "  " ,  "  "  " a string "  "  " );
