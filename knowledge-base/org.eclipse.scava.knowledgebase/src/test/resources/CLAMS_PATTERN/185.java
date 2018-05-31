{
    boolean buildStats;
    AgendaEventListener listener;
    String fileName;
    WorkingMemory workingMemory;
    PackageBuilder builder = new PackageBuilder();
    builder.addPackageFromDrl( new InputStreamReader( DroolsManners.class.getResourceAsStream( fileName ) ) );
    Package pkg = builder.getPackage();

    RuleBase ruleBase = RuleBaseFactory.newRuleBase();
    ruleBase.addPackage( pkg );
    workingMemory = ruleBase.newWorkingMemory();


    if( buildStats ) {
        workingMemory.addEventListener( listener );
    }
}
