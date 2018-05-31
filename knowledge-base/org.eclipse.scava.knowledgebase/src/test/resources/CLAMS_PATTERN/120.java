{
    ProcessMarshallerFactory.setProcessMarshallerFactoryService(new ProcessMarshallerFactoryServiceImpl());
    ProcessRuntimeFactory.setProcessRuntimeFactoryService(new ProcessRuntimeFactoryServiceImpl());
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    for(String  " a string " :getProcessLocations()) {
        kbuilder.add(ResourceFactory.newClassPathResource( " a string " ), ResourceType.DRF);
    }
    KnowledgeBase kb = kbuilder.newKnowledgeBase();
    // Do something with kb
}    for(String  "  " a string "  " :getProcessLocations()) {        kbuilder.add(ResourceFactory.newClassPathResource( "  " a string "  " ), ResourceType.DRF);    for(String  "  " a string "  " :getProcessLocations()) {        kbuilder.add(ResourceFactory.newClassPathResource( "  " a string "  " ), ResourceType.DRF);}    for(String  "  "  " a string "  "  " :getProcessLocations()) {        kbuilder.add(ResourceFactory.newClassPathResource( "  "  " a string "  "  " ), ResourceType.DRF);
