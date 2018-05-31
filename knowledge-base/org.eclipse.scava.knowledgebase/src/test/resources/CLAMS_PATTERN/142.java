{
    Environment env;
    String ruleString;
    List<?> list;
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    kbuilder.add( ResourceFactory.newByteArrayResource( ruleString.getBytes() ),
    ResourceType.DRL );
    KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();

    if ( kbuilder.hasErrors() ) {
        fail( kbuilder.getErrors().toString() );
    }

    kbase.addKnowledgePackages( kbuilder.getKnowledgePackages() );

    StatefulKnowledgeSession ksession = JPAKnowledgeService.newStatefulKnowledgeSession( kbase, null, env );
    ksession.setGlobal(  " a string " ,
    list );

    ksession.insert( number );
    ksession.insert( number );
    ksession.insert( number );

    ksession.fireAllRules();
}    ksession.setGlobal(  "  " a string "  " ,    ksession.setGlobal(  "  " a string "  " ,}    ksession.setGlobal(  "  "  " a string "  "  " ,
