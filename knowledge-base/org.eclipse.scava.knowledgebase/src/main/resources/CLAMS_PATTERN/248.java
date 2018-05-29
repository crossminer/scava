{

    Properties droolsProperties;
    CompleteProcessEventListener processEventListener;
    final String url;
    final String processId;
    KnowledgeBuilderConfiguration config = KnowledgeBuilderFactory
    .newKnowledgeBuilderConfiguration(droolsProperties);

    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
    .newKnowledgeBuilder(config);

    kbuilder.add(ResourceFactory.newUrlResource(url), ResourceType.BPMN2);
    if (kbuilder.hasErrors()) {
        Logger.log(kbuilder.getErrors().toString());
    }

    KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
    kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

    StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();

    ksession.getWorkItemManager().registerWorkItemHandler( " a string " ,
    new JenkinsJobWorkItemHandler());
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " ,
    new HornetQHTWorkItemHandler(ksession));

    ksession.addEventListener(processEventListener);

    ksession.startProcess(processId);
}    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " ,    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " ,    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " ,    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " ,}    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " ,    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " ,
