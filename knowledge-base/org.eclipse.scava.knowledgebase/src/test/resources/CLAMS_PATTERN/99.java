{
    String drl;
    PackageBuilder builder = new PackageBuilder();
    builder.addPackageFromDrl( new StringReader( drl ) );

    RuleBase ruleBase = RuleBaseFactory.newRuleBase();
    ruleBase.addPackage( builder.getPackage() );
}
