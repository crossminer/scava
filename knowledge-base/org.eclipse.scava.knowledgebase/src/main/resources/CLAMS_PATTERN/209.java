{
    TestWorkItemHandler handler;
    Map<String, Object> variables;
    String process;
    final PackageBuilder builder = new PackageBuilder();
    builder.addProcessFromXml( new StringReader( process ));
    final Package pkg = builder.getPackage();

    final RuleBase ruleBase = RuleBaseFactory.newRuleBase();
    ruleBase.addPackage(pkg);

    StatefulSession session = ruleBase.newStatefulSession();
    session.getWorkItemManager().registerWorkItemHandler( " a string " , handler);
    session.startProcess( " a string " , variables);

    assertEquals(number, session.getProcessInstances().size());
    assertEquals(number, session.getProcessInstances().size());
    VariableScopeInstance variableScopeInstance = (VariableScopeInstance)
    (( ProcessInstance )session.getProcessInstances().iterator().next()).getContextInstance(VariableScope.VARIABLE_SCOPE);
    session.getWorkItemManager().completeWorkItem(handler.getWorkItem().getId(), null);

    assertEquals(number, session.getProcessInstances().size());
    // Do something with variableScopeInstance

}    session.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , handler);    session.startProcess( "  " a string "  " , variables);    session.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , handler);    session.startProcess( "  " a string "  " , variables);}    session.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , handler);    session.startProcess( "  "  " a string "  "  " , variables);
