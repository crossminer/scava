{
    Map<String, Object> params;
    Reader source;
    PackageBuilder builder = new PackageBuilder();
    builder.addRuleFlow(source);
    builder.addRuleFlow(source);
    Package pkg = builder.getPackage();
    RuleBase ruleBase = RuleBaseFactory.newRuleBase();
    ruleBase.addPackage( pkg );
    WorkingMemory workingMemory = ruleBase.newStatefulSession();
    ProcessInstance processInstance = ( ProcessInstance )
    workingMemory.startProcess( " a string " , params);
    assertEquals(number, workingMemory.getProcessInstances().size());
    assertEquals(number, workingMemory.getProcessInstances().size());
    // Do something with processInstance

}    workingMemory.startProcess( "  " a string "  " , params);    workingMemory.startProcess( "  " a string "  " , params);}    workingMemory.startProcess( "  "  " a string "  "  " , params);
