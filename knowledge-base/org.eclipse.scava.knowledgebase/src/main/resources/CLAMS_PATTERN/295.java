{
    Hour hour;
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    kbuilder.add(ResourceFactory.newClassPathResource( " a string " ), ResourceType.DRL);
    KnowledgeBase kbase = kbuilder.newKnowledgeBase();
    StatelessKnowledgeSession ksession = kbase.newStatelessKnowledgeSession();
    ksession.execute(hour);
}    kbuilder.add(ResourceFactory.newClassPathResource( "  " a string "  " ), ResourceType.DRL);    kbuilder.add(ResourceFactory.newClassPathResource( "  " a string "  " ), ResourceType.DRL);}    kbuilder.add(ResourceFactory.newClassPathResource( "  "  " a string "  "  " ), ResourceType.DRL);
