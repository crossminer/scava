{
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    kbuilder.add(new ClassPathResource( " a string " ), ResourceType.DRL);

    KnowledgeBuilderErrors errors = kbuilder.getErrors();
    if (errors.size() > number) {
        // Do something
    }

    KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
    kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

    StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
    // Do something with ksession
}    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.DRL);    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.DRL);}    kbuilder.add(new ClassPathResource( "  "  " a string "  "  " ), ResourceType.DRL);
