{
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    kbuilder.add(new ClassPathResource( " a string " ), ResourceType.DRL);

    if(kbuilder.hasErrors()) {
        throw new IllegalStateException( " a string " + kbuilder.getErrors().iterator().next().getMessage());
    }

    KnowledgeBase kbase = kbuilder.newKnowledgeBase();
    kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

    StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
    KnowledgeRuntimeLoggerFactory.newConsoleLogger(ksession);
    ksession.insert(new Person( " a string " , number));

    int fired = ksession.fireAllRules();
    // Do something with fired
}    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.DRL);        throw new IllegalStateException( "  " a string "  " + kbuilder.getErrors().iterator().next().getMessage());    ksession.insert(new Person( "  " a string "  " , number));    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.DRL);        throw new IllegalStateException( "  " a string "  " + kbuilder.getErrors().iterator().next().getMessage());    ksession.insert(new Person( "  " a string "  " , number));}    kbuilder.add(new ClassPathResource( "  "  " a string "  "  " ), ResourceType.DRL);        throw new IllegalStateException( "  "  " a string "  "  " + kbuilder.getErrors().iterator().next().getMessage());    ksession.insert(new Person( "  "  " a string "  "  " , number));
