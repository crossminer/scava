{


    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    kbuilder.add(new ClassPathResource( " a string " ), ResourceType.BPMN2);

    KnowledgeBuilderErrors errors = kbuilder.getErrors();
    if (errors != null && errors.size() > number) {
        for (KnowledgeBuilderError error : errors) {
            System.out.println( " a string "  + error.getMessage());
        }
    }
    KnowledgeBaseConfiguration kbaseConf = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
    kbaseConf.setOption(EventProcessingOption.STREAM);
    KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase(kbaseConf);
    kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

    StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
    // Do something with ksession
}    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.BPMN2);            System.out.println( "  " a string "  "  + error.getMessage());    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.BPMN2);            System.out.println( "  " a string "  "  + error.getMessage());}    kbuilder.add(new ClassPathResource( "  "  " a string "  "  " ), ResourceType.BPMN2);            System.out.println( "  "  " a string "  "  "  + error.getMessage());
