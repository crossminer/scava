{
    TestWorkItemHandler workItemHandler;
    RuleFlowProcess p;
    StatefulKnowledgeSession ksession;
    WorkItem workItem;
    Logger logger;
    KnowledgeBuilderConfiguration conf = KnowledgeBuilderFactory
    .newKnowledgeBuilderConfiguration();
    ((PackageBuilderConfiguration) conf).initSemanticModules();
    ((PackageBuilderConfiguration) conf)
    .addSemanticModule(new BPMNSemanticModule());
    ((PackageBuilderConfiguration) conf)
    .addSemanticModule(new BPMNDISemanticModule());
    XmlProcessReader processReader = new XmlProcessReader(
        ((PackageBuilderConfiguration) conf).getSemanticModules(),
        getClass().getClassLoader());
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
    .newKnowledgeBuilder(conf);
    kbuilder.add(ResourceFactory.newReaderResource(new StringReader(
        XmlBPMNProcessDumper.INSTANCE.dump(p))), ResourceType.BPMN2);
    kbuilder.add(ResourceFactory
    .newClassPathResource( " a string " ),
    ResourceType.DRL);
    if (!kbuilder.getErrors().isEmpty()) {
        for (KnowledgeBuilderError error : kbuilder.getErrors()) {
            logger.error(error.toString());
        }
    }
    KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
    kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " ,
            workItemHandler);
    ProcessInstance processInstance = ksession
                                      .startProcess( " a string " );
    assertTrue(processInstance.getState() == ProcessInstance.STATE_ACTIVE);
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " ,
            workItemHandler);
    ksession.fireAllRules();
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " ,
            workItemHandler);
    ksession.getWorkItemManager().completeWorkItem(workItem.getId(), null);
    assertProcessInstanceCompleted(processInstance.getId(), ksession);
    // Do something with processReader

}    .newClassPathResource( "  " a string "  " ),    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " ,                                      .startProcess( "  " a string "  " );    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " ,    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " ,    .newClassPathResource( "  " a string "  " ),    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " ,                                      .startProcess( "  " a string "  " );    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " ,    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " ,}    .newClassPathResource( "  "  " a string "  "  " ),    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " ,                                      .startProcess( "  "  " a string "  "  " );    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " ,    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " ,
