{
    AUSLOANAPPLICATIONType application;
    Package pkg;
    RuleBase ruleBase = RuleBaseFactory.newRuleBase();
    ruleBase.addPackage( pkg );

    StatelessSession session = ruleBase.newStatelessSession();
    session.addEventListener( new DebugAgendaEventListener() );

    StatelessSessionResult results = session.executeWithResults( new Object[] { application }  );

    AUSLOANAPPLICATIONType returnedApplication = ( AUSLOANAPPLICATIONType ) results.iterateObjects().next();
    // Do something with returnedApplication
}
