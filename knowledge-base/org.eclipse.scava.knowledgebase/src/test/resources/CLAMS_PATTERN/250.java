{
    Reader source;
    List<String> list;
    TestWorkItemHandler testHandler;
    WorkItem workItem;
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
    workingMemory.getWorkItemManager().registerWorkItemHandler( " a string " , testHandler);
    ProcessInstance processInstance = ( ProcessInstance )
    workingMemory.startProcess( " a string " );
    workingMemory.getWorkItemManager().completeWorkItem(workItem.getId(), null);
    // Do something with processInstance
}    workingMemory.setGlobal( "  " a string "  " , list);    workingMemory.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , testHandler);    workingMemory.startProcess( "  " a string "  " );    workingMemory.setGlobal( "  " a string "  " , list);    workingMemory.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , testHandler);    workingMemory.startProcess( "  " a string "  " );}    workingMemory.setGlobal( "  "  " a string "  "  " , list);    workingMemory.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , testHandler);    workingMemory.startProcess( "  "  " a string "  "  " );
