{
    KnowledgeBuilder kbuilder;
    InputStream inputStream;
    DecisionTableConfiguration config = KnowledgeBuilderFactory
    .newDecisionTableConfiguration();
    config.setInputType(DecisionTableInputType.XLS);
    kbuilder.add(ResourceFactory.newInputStreamResource(inputStream),
    ResourceType.DTABLE, config);
}
