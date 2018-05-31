{
    Properties properties;
    final List<URL> rulesUrls;
    PackageBuilderConfiguration conf = new PackageBuilderConfiguration(properties);

    PackageBuilder builder = new PackageBuilder(conf);
    RuleBase ruleBase = RuleBaseFactory.newRuleBase();

    for (URL rulesUrl : rulesUrls) {
        try {
            builder.addPackageFromDrl(new InputStreamReader(rulesUrl.openStream()));
            Package pkg = builder.getPackage();
            ruleBase.addPackage(pkg);
        } catch (Exception e) {
            // Do something
        }
    }
}
