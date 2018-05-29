{
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    DecisionTableConfiguration config = KnowledgeBuilderFactory.newDecisionTableConfiguration();
    config.setInputType(DecisionTableInputType.XLS);
    kbuilder.add(ResourceFactory.newClassPathResource( " a string " ), ResourceType.DTABLE, config);
    KnowledgeBuilderErrors errors = kbuilder.getErrors();
    if (errors.size() > number) {
        // Do something
    }
    KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
    kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
}    kbuilder.add(ResourceFactory.newClassPathResource( "  " a string "  " ), ResourceType.DTABLE, config);    kbuilder.add(ResourceFactory.newClassPathResource( "  " a string "  " ), ResourceType.DTABLE, config);}    kbuilder.add(ResourceFactory.newClassPathResource( "  "  " a string "  "  " ), ResourceType.DTABLE, config);
