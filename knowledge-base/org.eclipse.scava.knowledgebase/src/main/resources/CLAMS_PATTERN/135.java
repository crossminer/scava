{
    StringBuilder errorMessage;
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
    .newKnowledgeBuilder();

    for (String path : this.getProcessPaths()) {
        kbuilder.add(new ClassPathResource(path),
        ResourceType.BPMN2);
    }
    KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
    kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
    if (kbuilder.hasErrors()) {
        for (KnowledgeBuilderError error : kbuilder.getErrors()) {
            errorMessage.append(error.getMessage());
        }
    }
}
