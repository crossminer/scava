{
    Map<String, Object> params;
    Reader source;
    List<Long> list;
    PackageBuilder builder = new PackageBuilder();
    builder.addRuleFlow(source);
    Package pkg = builder.getPackage();
    RuleBase ruleBase = RuleBaseFactory.newRuleBase();
    ruleBase.addPackage( pkg );
    WorkingMemory workingMemory = ruleBase.newStatefulSession();
    workingMemory.setGlobal( " a string " , list);

    ProcessInstance processInstance = ( ProcessInstance )
    workingMemory.startProcess( " a string " , params);

    assertEquals(ProcessInstance.STATE_COMPLETED, processInstance.getState());
}    workingMemory.setGlobal( "  " a string "  " , list);    workingMemory.startProcess( "  " a string "  " , params);    workingMemory.setGlobal( "  " a string "  " , list);    workingMemory.startProcess( "  " a string "  " , params);}    workingMemory.setGlobal( "  "  " a string "  "  " , list);    workingMemory.startProcess( "  "  " a string "  "  " , params);
