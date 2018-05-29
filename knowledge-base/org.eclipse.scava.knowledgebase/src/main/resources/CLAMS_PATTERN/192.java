{
    Reader source;
    PackageBuilder builder = new PackageBuilder();
    builder.addRuleFlow(source);
    Package pkg = builder.getPackage();
    RuleBase ruleBase = RuleBaseFactory.newRuleBase();
    ruleBase.addPackage( pkg );
    StatefulSession session = ruleBase.newStatefulSession();
    ProcessInstance processInstance = session.startProcess( " a string " );
    assertEquals(ProcessInstance.STATE_ACTIVE, processInstance.getState());

    processInstance = session.getProcessInstance(processInstance.getId());
    processInstance.signalEvent( " a string " ,  " a string " );
    assertEquals(ProcessInstance.STATE_COMPLETED, processInstance.getState());
}    ProcessInstance processInstance = session.startProcess( "  " a string "  " );    processInstance.signalEvent( "  " a string "  " ,  "  " a string "  " );    ProcessInstance processInstance = session.startProcess( "  " a string "  " );    processInstance.signalEvent( "  " a string "  " ,  "  " a string "  " );}    ProcessInstance processInstance = session.startProcess( "  "  " a string "  "  " );    processInstance.signalEvent( "  "  " a string "  "  " ,  "  "  " a string "  "  " );
