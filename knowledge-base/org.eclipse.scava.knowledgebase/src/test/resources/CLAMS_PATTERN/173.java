{
    final List<String> list;
    RuleBase ruleBase;
    final PackageBuilder builder = new PackageBuilder();
    builder.addPackageFromDrl( new InputStreamReader( getClass().getResourceAsStream(  " a string "  ) ) );
    builder.addRuleFlow( new InputStreamReader( getClass().getResourceAsStream(  " a string "  ) ) );
    final Package pkg = builder.getPackage();

    ruleBase.addPackage( pkg );
    final WorkingMemory workingMemory = ruleBase.newStatefulSession();
    workingMemory.setGlobal(  " a string " ,
    list );

    workingMemory.fireAllRules();
    final ProcessInstance processInstance = workingMemory.startProcess(  " a string "  );
    assertEquals( ProcessInstance.STATE_ACTIVE,
    processInstance.getState() );
    workingMemory.fireAllRules();
    assertEquals( ProcessInstance.STATE_COMPLETED,
    processInstance.getState() );

}    builder.addPackageFromDrl( new InputStreamReader( getClass().getResourceAsStream(  "  " a string "  "  ) ) );    builder.addRuleFlow( new InputStreamReader( getClass().getResourceAsStream(  "  " a string "  "  ) ) );    workingMemory.setGlobal(  "  " a string "  " ,    final ProcessInstance processInstance = workingMemory.startProcess(  "  " a string "  "  );    builder.addPackageFromDrl( new InputStreamReader( getClass().getResourceAsStream(  "  " a string "  "  ) ) );    builder.addRuleFlow( new InputStreamReader( getClass().getResourceAsStream(  "  " a string "  "  ) ) );    workingMemory.setGlobal(  "  " a string "  " ,    final ProcessInstance processInstance = workingMemory.startProcess(  "  " a string "  "  );}    builder.addPackageFromDrl( new InputStreamReader( getClass().getResourceAsStream(  "  "  " a string "  "  "  ) ) );    builder.addRuleFlow( new InputStreamReader( getClass().getResourceAsStream(  "  "  " a string "  "  "  ) ) );    workingMemory.setGlobal(  "  "  " a string "  "  " ,    final ProcessInstance processInstance = workingMemory.startProcess(  "  "  " a string "  "  "  );
