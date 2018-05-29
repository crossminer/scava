{
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    kbuilder.add(new ClassPathResource( " a string " ), ResourceType.DRL);

    if (kbuilder.hasErrors()) {
        for (KnowledgeBuilderError error : kbuilder.getErrors()) {
            System.out.println( " a string "  + error.getMessage());
        }
    }

    KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
    kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

    return kbase.newStatelessKnowledgeSession();
}    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.DRL);            System.out.println( "  " a string "  "  + error.getMessage());    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.DRL);            System.out.println( "  " a string "  "  + error.getMessage());}    kbuilder.add(new ClassPathResource( "  "  " a string "  "  " ), ResourceType.DRL);            System.out.println( "  "  " a string "  "  "  + error.getMessage());
