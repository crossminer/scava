{
    StringBuilder errorMessage;
    String flowFile;
    KnowledgeBuilderConfiguration conf = KnowledgeBuilderFactory.newKnowledgeBuilderConfiguration();
    conf.setProperty( " a string " ,  " a string " );
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder(conf);
    kbuilder.add(new ClassPathResource(flowFile), ResourceType.BPMN2);
    if (kbuilder.hasErrors()) {
        for (KnowledgeBuilderError error : kbuilder.getErrors()) {
            errorMessage.append(error.getMessage());
        }
    }

    KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
    kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
}    conf.setProperty( "  " a string "  " ,  "  " a string "  " );    conf.setProperty( "  " a string "  " ,  "  " a string "  " );}    conf.setProperty( "  "  " a string "  "  " ,  "  "  " a string "  "  " );
