{
    StatefulKnowledgeSession ksession;
    ksession.addEventListener(new DefaultProcessEventListener() {
        // Do something
    });
    ProcessInstance processInstance = ksession.startProcess( " a string " );
    assertTrue(processInstance.getState() == ProcessInstance.STATE_COMPLETED);
}    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " );    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " );}    ProcessInstance processInstance = ksession.startProcess( "  "  " a string "  "  " );
