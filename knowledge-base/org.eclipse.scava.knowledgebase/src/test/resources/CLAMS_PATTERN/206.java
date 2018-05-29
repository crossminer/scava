{
    Chance.initialize();

    KnowledgeBuilder kBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder(Chance.getChanceKBuilderConfiguration());
    kBuilder.add( new ClassPathResource(  " a string "  ), ResourceType.CHANGE_SET );
    if ( kBuilder.hasErrors() ) {
        fail( kBuilder.getErrors().toString() );
    }

    KnowledgeBaseConfiguration kbConf = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
    kbConf.setOption( EventProcessingOption.STREAM );
    KnowledgeBase kBase = KnowledgeBaseFactory.newKnowledgeBase( kbConf );
    kBase.addKnowledgePackages( kBuilder.getKnowledgePackages() );

    KnowledgeSessionConfiguration ksConf = KnowledgeBaseFactory.newKnowledgeSessionConfiguration();
    ksConf.setOption( ClockTypeOption.get(  " a string "  ) );
    StatefulKnowledgeSession kSession = kBase.newStatefulKnowledgeSession( ksConf, null );
    kSession.fireAllRules();
}    kBuilder.add( new ClassPathResource(  "  " a string "  "  ), ResourceType.CHANGE_SET );    ksConf.setOption( ClockTypeOption.get(  "  " a string "  "  ) );    kBuilder.add( new ClassPathResource(  "  " a string "  "  ), ResourceType.CHANGE_SET );    ksConf.setOption( ClockTypeOption.get(  "  " a string "  "  ) );}    kBuilder.add( new ClassPathResource(  "  "  " a string "  "  "  ), ResourceType.CHANGE_SET );    ksConf.setOption( ClockTypeOption.get(  "  "  " a string "  "  "  ) );
