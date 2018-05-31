{
    Reader source;
    List<String> list;
    PackageBuilder builder = new PackageBuilder();
    builder.addRuleFlow(source);
    Package pkg = builder.getPackage();
    for (DroolsError error: builder.getErrors().getErrors()) {
        // Do something
    }
    RuleBase ruleBase = RuleBaseFactory.newRuleBase();
    ruleBase.addPackage( pkg );
    WorkingMemory workingMemory = ruleBase.newStatefulSession();
    workingMemory.setGlobal( " a string " , list);
    ProcessInstance processInstance = ( ProcessInstance )
    workingMemory.startProcess( " a string " );
    // Do something with processInstance
}    workingMemory.setGlobal( "  " a string "  " , list);    workingMemory.startProcess( "  " a string "  " );    workingMemory.setGlobal( "  " a string "  " , list);    workingMemory.startProcess( "  " a string "  " );}    workingMemory.setGlobal( "  "  " a string "  "  " , list);    workingMemory.startProcess( "  "  " a string "  "  " );
