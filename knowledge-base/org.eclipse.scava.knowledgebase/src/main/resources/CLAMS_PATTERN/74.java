{
    StatefulKnowledgeSession session;
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

    kbuilder.add(new ClassPathResource( " a string " ), ResourceType.BPMN2);
    if (kbuilder.hasErrors()) {
        KnowledgeBuilderErrors errors = kbuilder.getErrors();
        for (KnowledgeBuilderError error : errors) {
            System.out.println( " a string "  + error.getMessage());
        }
    }

    KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();

    kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

    session = kbase.newStatefulKnowledgeSession();
    KnowledgeRuntimeLoggerFactory.newConsoleLogger(session);

    session.addEventListener(new DefaultAgendaEventListener() {
        // Do something
    });

}    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.BPMN2);            System.out.println( "  " a string "  "  + error.getMessage());    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.BPMN2);            System.out.println( "  " a string "  "  + error.getMessage());}    kbuilder.add(new ClassPathResource( "  "  " a string "  "  " ), ResourceType.BPMN2);            System.out.println( "  "  " a string "  "  "  + error.getMessage());
