{

    StatefulKnowledgeSession ksession;
    Map<String,Object> parameters;
    ProcessInstance process = ksession.startProcess( " a string " ,parameters);

    Assert.assertEquals(ProcessInstance.STATE_ACTIVE, process.getState());

    Assert.assertEquals(ProcessInstance.STATE_COMPLETED, process.getState());

    List<String> emailService = (List<String>) ((WorkflowProcessInstance)process).getVariable( " a string " );
    // Do something with emailService
}    ProcessInstance process = ksession.startProcess( "  " a string "  " ,parameters);    List<String> emailService = (List<String>) ((WorkflowProcessInstance)process).getVariable( "  " a string "  " );    ProcessInstance process = ksession.startProcess( "  " a string "  " ,parameters);    List<String> emailService = (List<String>) ((WorkflowProcessInstance)process).getVariable( "  " a string "  " );}    ProcessInstance process = ksession.startProcess( "  "  " a string "  "  " ,parameters);    List<String> emailService = (List<String>) ((WorkflowProcessInstance)process).getVariable( "  "  " a string "  "  " );
