{
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    kbuilder.add(ResourceFactory.newClassPathResource( " a string " ), ResourceType.DRL);
    kbuilder.add(ResourceFactory.newClassPathResource( " a string " ), ResourceType.DRL);
    kbuilder.add(ResourceFactory.newClassPathResource( " a string " ), ResourceType.DRL);
    kbuilder.add(ResourceFactory.newClassPathResource( " a string " ), ResourceType.DRL);
    kbuilder.add(ResourceFactory.newClassPathResource( " a string " ), ResourceType.DRL);
    kbuilder.add(ResourceFactory.newClassPathResource( " a string " ), ResourceType.DRL);
    KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
    kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
}   