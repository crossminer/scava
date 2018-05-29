{

    Map<String, Object> inputParameters;
    StatefulKnowledgeSession ksession;
    ProcessInstance process = ksession.startProcess( " a string " , inputParameters);

    Assert.assertEquals(ProcessInstance.STATE_ACTIVE, process.getState());

    Assert.assertEquals(ProcessInstance.STATE_COMPLETED, process.getState());
}    ProcessInstance process = ksession.startProcess( "  " a string "  " , inputParameters);    ProcessInstance process = ksession.startProcess( "  " a string "  " , inputParameters);}    ProcessInstance process = ksession.startProcess( "  "  " a string "  "  " , inputParameters);
