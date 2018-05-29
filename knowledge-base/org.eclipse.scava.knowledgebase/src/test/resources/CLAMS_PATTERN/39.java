{

    StatefulKnowledgeSession ksession;
    Map<String, Object> parameters;
    ProcessInstance process = ksession.startProcess( " a string " ,parameters);

    Assert.assertEquals(ProcessInstance.STATE_COMPLETED, process.getState());
    List errorList = (List) ((WorkflowProcessInstance)process).getVariable( " a string " );
    // Do something with errorList
}    ProcessInstance process = ksession.startProcess( "  " a string "  " ,parameters);    List errorList = (List) ((WorkflowProcessInstance)process).getVariable( "  " a string "  " );    ProcessInstance process = ksession.startProcess( "  " a string "  " ,parameters);    List errorList = (List) ((WorkflowProcessInstance)process).getVariable( "  " a string "  " );}    ProcessInstance process = ksession.startProcess( "  "  " a string "  "  " ,parameters);    List errorList = (List) ((WorkflowProcessInstance)process).getVariable( "  "  " a string "  "  " );
