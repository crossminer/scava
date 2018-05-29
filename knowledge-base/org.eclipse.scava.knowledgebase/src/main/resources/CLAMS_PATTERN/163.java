{

    Map<String, Object> params;
    Person adultEval;
    StatefulKnowledgeSession ksession;
    ksession.insert(adultEval);

    WorkflowProcessInstance processInstance = (WorkflowProcessInstance) ksession.startProcess( " a string " , params);
    ksession.insert(processInstance);
    ksession.fireAllRules();

    assertProcessInstanceCompleted(processInstance.getId(), ksession);
    assertNodeTriggered(processInstance.getId(),  " a string " );
    ksession.dispose();
}    WorkflowProcessInstance processInstance = (WorkflowProcessInstance) ksession.startProcess( "  " a string "  " , params);    assertNodeTriggered(processInstance.getId(),  "  " a string "  " );    WorkflowProcessInstance processInstance = (WorkflowProcessInstance) ksession.startProcess( "  " a string "  " , params);    assertNodeTriggered(processInstance.getId(),  "  " a string "  " );}    WorkflowProcessInstance processInstance = (WorkflowProcessInstance) ksession.startProcess( "  "  " a string "  "  " , params);    assertNodeTriggered(processInstance.getId(),  "  "  " a string "  "  " );
