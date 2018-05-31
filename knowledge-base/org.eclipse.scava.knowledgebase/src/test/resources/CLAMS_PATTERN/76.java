{
    StatefulKnowledgeSession ksession;
    ProcessInstance process = ksession.startProcess( " a string " );

    Assert.assertEquals(ProcessInstance.STATE_ACTIVE, process.getState());

    Assert.assertEquals(ProcessInstance.STATE_ACTIVE, process.getState());


    Assert.assertEquals(ProcessInstance.STATE_COMPLETED, process.getState());
}    ProcessInstance process = ksession.startProcess( "  " a string "  " );    ProcessInstance process = ksession.startProcess( "  " a string "  " );}    ProcessInstance process = ksession.startProcess( "  "  " a string "  "  " );
