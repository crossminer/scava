{
    StatefulKnowledgeSession ksession;
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    kbuilder.add(new ClassPathResource( " a string " , getClass()), ResourceType.DSL);
    kbuilder.add(new ClassPathResource( " a string " , getClass()), ResourceType.DSLR);
    KnowledgeBuilderErrors errors = kbuilder.getErrors();
    if (errors.size() > number) {
        // Do something
    }
    KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
    kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

    ksession = kbase.newStatefulKnowledgeSession();
    // Do something with ksession
}    kbuilder.add(new ClassPathResource( "  " a string "  " , getClass()), ResourceType.DSL);    kbuilder.add(new ClassPathResource( "  " a string "  " , getClass()), ResourceType.DSLR);    kbuilder.add(new ClassPathResource( "  " a string "  " , getClass()), ResourceType.DSL);    kbuilder.add(new ClassPathResource( "  " a string "  " , getClass()), ResourceType.DSLR);}    kbuilder.add(new ClassPathResource( "  "  " a string "  "  " , getClass()), ResourceType.DSL);    kbuilder.add(new ClassPathResource( "  "  " a string "  "  " , getClass()), ResourceType.DSLR);
