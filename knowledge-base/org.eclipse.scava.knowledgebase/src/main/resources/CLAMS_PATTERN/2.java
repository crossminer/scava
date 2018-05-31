{
    KnowledgeBase kbase;
    Collection<KnowledgePackage> knowledgePackages;
    if (!knowledgePackages.isEmpty()) {
        kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages(knowledgePackages);
    }
}
