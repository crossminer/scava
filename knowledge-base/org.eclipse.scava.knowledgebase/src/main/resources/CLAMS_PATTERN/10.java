{
    StatefulKnowledgeSession ksession;
    ProcessInstance processInstance = ksession.startProcess( " a string " );
    assertProcessInstanceCompleted(processInstance.getId(), ksession);
    assertNodeTriggered(processInstance.getId(),  " a string " ,  " a string " ,  " a string " );
}    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " );    assertNodeTriggered(processInstance.getId(),  "  " a string "  " ,  "  " a string "  " ,  "  " a string "  " );    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " );    assertNodeTriggered(processInstance.getId(),  "  " a string "  " ,  "  " a string "  " ,  "  " a string "  " );}    ProcessInstance processInstance = ksession.startProcess( "  "  " a string "  "  " );    assertNodeTriggered(processInstance.getId(),  "  "  " a string "  "  " ,  "  "  " a string "  "  " ,  "  "  " a string "  "  " );
