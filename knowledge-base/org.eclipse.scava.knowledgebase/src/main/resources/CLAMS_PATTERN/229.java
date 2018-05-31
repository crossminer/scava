{


    StatefulKnowledgeSession ksession;
    PackageBuilderConfiguration pkgConf = new PackageBuilderConfiguration();
    pkgConf.addAccumulateFunction( " a string " , SongsWithALetterOnTheirTitlesFunction.class);

    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder(pkgConf);
    kbuilder.add(new ClassPathResource( " a string " , getClass()), ResourceType.DRL);
    KnowledgeBuilderErrors errors = kbuilder.getErrors();
    if (errors.size() > number) {
        // Do something
    }
    KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();

    kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
    ksession = kbase.newStatefulKnowledgeSession();
    KnowledgeRuntimeLoggerFactory.newConsoleLogger(ksession);
}    pkgConf.addAccumulateFunction( "  " a string "  " , SongsWithALetterOnTheirTitlesFunction.class);    kbuilder.add(new ClassPathResource( "  " a string "  " , getClass()), ResourceType.DRL);    pkgConf.addAccumulateFunction( "  " a string "  " , SongsWithALetterOnTheirTitlesFunction.class);    kbuilder.add(new ClassPathResource( "  " a string "  " , getClass()), ResourceType.DRL);}    pkgConf.addAccumulateFunction( "  "  " a string "  "  " , SongsWithALetterOnTheirTitlesFunction.class);    kbuilder.add(new ClassPathResource( "  "  " a string "  "  " , getClass()), ResourceType.DRL);
