{
    RuleBase ruleBase;
    WorkingMemory workingMemory = ruleBase.newStatefulSession();
    ProcessInstance processInstance = ( ProcessInstance )
    workingMemory.startProcess( " a string " );
    assertEquals(ProcessInstance.STATE_ACTIVE, processInstance.getState());
    assertEquals(number, workingMemory.getProcessInstances().size());
    workingMemory.insert(new Person());
    assertEquals(ProcessInstance.STATE_COMPLETED, processInstance.getState());
    assertEquals(number, workingMemory.getProcessInstances().size());
}    workingMemory.startProcess( "  " a string "  " );    workingMemory.startProcess( "  " a string "  " );}    workingMemory.startProcess( "  "  " a string "  "  " );
