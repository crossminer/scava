{
    List<Process> processes;
    RuleFlowProcess ruleFlowProcess;
    Logger logger;
    String process;
    KnowledgeBaseFactory
    .setKnowledgeBaseServiceFactory(new KnowledgeBaseFactoryServiceImpl());
    KnowledgeBuilderConfiguration conf = KnowledgeBuilderFactory
    .newKnowledgeBuilderConfiguration();
    ((PackageBuilderConfiguration) conf).initSemanticModules();
    ((PackageBuilderConfiguration) conf)
    .addSemanticModule(new BPMNSemanticModule());
    ((PackageBuilderConfiguration) conf)
    .addSemanticModule(new BPMNDISemanticModule());
    ((PackageBuilderConfiguration) conf)
    .addSemanticModule(new BPMNExtensionsSemanticModule());
    XmlProcessReader processReader = new XmlProcessReader(
        ((PackageBuilderConfiguration) conf).getSemanticModules(),
        getClass().getClassLoader());
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
    .newKnowledgeBuilder(conf);
    for (Process p : processes) {
        kbuilder.add(ResourceFactory.newReaderResource(new StringReader(
            XmlBPMNProcessDumper.INSTANCE.dump(ruleFlowProcess))),
        ResourceType.BPMN2);
    }
    kbuilder.add(ResourceFactory
    .newReaderResource(new InputStreamReader(
        SimpleBPMNProcessTest.class.getResourceAsStream( " a string " 
    + process))), ResourceType.BPMN2);
    if (!kbuilder.getErrors().isEmpty()) {
        for (KnowledgeBuilderError error : kbuilder.getErrors()) {
            logger.error(error.toString());
            System.out.println(error.toString());
        }
    }
    KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
    kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
    // Do something with processReader
}        SimpleBPMNProcessTest.class.getResourceAsStream( "  " a string "  "         SimpleBPMNProcessTest.class.getResourceAsStream( "  " a string "  " }        SimpleBPMNProcessTest.class.getResourceAsStream( "  "  " a string "  "  " 
