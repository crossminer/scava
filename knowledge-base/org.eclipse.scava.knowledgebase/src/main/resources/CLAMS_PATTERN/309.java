{
    StatefulKnowledgeSession ksession;
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    kbuilder.add(ResourceFactory.newClassPathResource( " a string " ), ResourceType.DRL);
    ksession.getKnowledgeBase().addKnowledgePackages(kbuilder.getKnowledgePackages());
    ksession.fireAllRules();
}    kbuilder.add(ResourceFactory.newClassPathResource( "  " a string "  " ), ResourceType.DRL);    kbuilder.add(ResourceFactory.newClassPathResource( "  " a string "  " ), ResourceType.DRL);}    kbuilder.add(ResourceFactory.newClassPathResource( "  "  " a string "  "  " ), ResourceType.DRL);
