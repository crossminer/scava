{
    String... rulesUrls;
    MVELDialectConfiguration conf = (MVELDialectConfiguration) new PackageBuilderConfiguration().getDialectConfiguration( " a string " );
    conf.setStrict(boolean);
    conf.getPackageBuilderConfiguration().setDefaultDialect( " a string " );

    KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder(conf.getPackageBuilderConfiguration());
    for (String rulesUrl : rulesUrls) {
        builder.add(ResourceFactory.newUrlResource(rulesUrl), ResourceType.DRL);
    }
    if (builder.hasErrors()) {
        throw new RuntimeException(builder.getErrors().toString());
    }
    Collection<KnowledgePackage> knowledgePackages = builder.getKnowledgePackages();
    // Do something with knowledgePackages
}    MVELDialectConfiguration conf = (MVELDialectConfiguration) new PackageBuilderConfiguration().getDialectConfiguration( "  " a string "  " );    conf.getPackageBuilderConfiguration().setDefaultDialect( "  " a string "  " );    MVELDialectConfiguration conf = (MVELDialectConfiguration) new PackageBuilderConfiguration().getDialectConfiguration( "  " a string "  " );    conf.getPackageBuilderConfiguration().setDefaultDialect( "  " a string "  " );}    MVELDialectConfiguration conf = (MVELDialectConfiguration) new PackageBuilderConfiguration().getDialectConfiguration( "  "  " a string "  "  " );    conf.getPackageBuilderConfiguration().setDefaultDialect( "  "  " a string "  "  " );
