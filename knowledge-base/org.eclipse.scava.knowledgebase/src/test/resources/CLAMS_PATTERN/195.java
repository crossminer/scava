{
    String... process;
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    for (String p: process) {
        kbuilder.add(ResourceFactory.newClassPathResource(p), ResourceType.BPMN2);
    }
    return kbuilder.newKnowledgeBase();
}
