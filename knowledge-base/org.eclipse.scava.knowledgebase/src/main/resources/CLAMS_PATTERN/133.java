{
    Person jack;
    RuleFlowProcess process;
    ObjectDataType personDataType = new ObjectDataType();
    personDataType.setClassName( " a string " );
    KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
    ((AbstractRuleBase) ((InternalKnowledgeBase) kbase).getRuleBase()).addProcess(process);
    StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();

    ProcessInstance processInstance = ksession.startProcess( " a string " );
    processInstance.signalEvent( " a string " , jack);
    assertEquals(ProcessInstance.STATE_COMPLETED, processInstance.getState());
}    personDataType.setClassName( "  " a string "  " );    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " );    processInstance.signalEvent( "  " a string "  " , jack);    personDataType.setClassName( "  " a string "  " );    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " );    processInstance.signalEvent( "  " a string "  " , jack);}    personDataType.setClassName( "  "  " a string "  "  " );    ProcessInstance processInstance = ksession.startProcess( "  "  " a string "  "  " );    processInstance.signalEvent( "  "  " a string "  "  " , jack);
