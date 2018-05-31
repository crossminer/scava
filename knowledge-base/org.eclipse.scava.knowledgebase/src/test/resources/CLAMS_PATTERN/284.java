{
    StatefulKnowledgeSession ksession;
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " ,
    new DoNothingWorkItemHandler());
    ksession.addEventListener(new DefaultProcessEventListener() {
        // Do something
    });
    ProcessInstance processInstance = ksession
    .startProcess( " a string " );
    assertTrue(processInstance.getState() == ProcessInstance.STATE_ACTIVE);

    for (int i = number; i < number; i++) {
        ksession.fireAllRules();
    }
}    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " ,    .startProcess( "  " a string "  " );    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " ,    .startProcess( "  " a string "  " );}    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " ,    .startProcess( "  "  " a string "  "  " );
