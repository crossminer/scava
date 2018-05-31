{
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

    kbuilder.add(new ClassPathResource( " a string " ), ResourceType.BPMN2);
    if (kbuilder.hasErrors()) {
        if (kbuilder.getErrors().size() > number) {
            for (KnowledgeBuilderError error : kbuilder.getErrors()) {
                System.out.println( " a string "  + error.getMessage());
            }
        }
    }

    KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
    kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

    return kbase.newStatefulKnowledgeSession();
}    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.BPMN2);                System.out.println( "  " a string "  "  + error.getMessage());    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.BPMN2);                System.out.println( "  " a string "  "  + error.getMessage());}    kbuilder.add(new ClassPathResource( "  "  " a string "  "  " ), ResourceType.BPMN2);                System.out.println( "  "  " a string "  "  "  + error.getMessage());
