{
    TestWorkItemHandler handler;
    StatefulKnowledgeSession ksession;
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " , handler);
    ksession.addEventListener(new DefaultProcessEventListener() {
        // Do something
    });

    ProcessInstance processInstance2 = ksession.startProcess( " a string " );
    assertProcessInstanceCompleted(processInstance2.getId(), ksession);
}    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , handler);    ProcessInstance processInstance2 = ksession.startProcess( "  " a string "  " );    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , handler);    ProcessInstance processInstance2 = ksession.startProcess( "  " a string "  " );}    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , handler);    ProcessInstance processInstance2 = ksession.startProcess( "  "  " a string "  "  " );
