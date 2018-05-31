{
    StatefulKnowledgeSession ksession;
    Person person;
    ProcessInstance processInstance = ksession.startProcess( " a string " );
    assertTrue(processInstance.getState() == ProcessInstance.STATE_ACTIVE);
    ksession.insert(person);
    assertProcessInstanceCompleted(processInstance.getId(), ksession);
}    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " );    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " );}    ProcessInstance processInstance = ksession.startProcess( "  "  " a string "  "  " );
