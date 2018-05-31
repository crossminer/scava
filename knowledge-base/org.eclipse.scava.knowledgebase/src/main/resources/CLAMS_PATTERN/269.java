{
    KnowledgeRuntimeLogger logger;
    StatefulKnowledgeSession ksession;
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

    kbuilder.add(new ClassPathResource( " a string " ), ResourceType.BPMN2);
    if (kbuilder.hasErrors()) {
        if (kbuilder.getErrors().size() > number) {
            for (KnowledgeBuilderError error: kbuilder.getErrors()) {
                System.out.println( " a string " +error.getMessage());
            }
        }
    }

    KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
    kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

    ksession = kbase.newStatefulKnowledgeSession();
    logger = KnowledgeRuntimeLoggerFactory.newConsoleLogger(ksession);
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " , new WSHumanTaskHandler());
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " , new GenerateReportWorkItem());
    // Do something with logger


}    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.BPMN2);                System.out.println( "  " a string "  " +error.getMessage());    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new WSHumanTaskHandler());    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new GenerateReportWorkItem());    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.BPMN2);                System.out.println( "  " a string "  " +error.getMessage());    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new WSHumanTaskHandler());    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new GenerateReportWorkItem());}    kbuilder.add(new ClassPathResource( "  "  " a string "  "  " ), ResourceType.BPMN2);                System.out.println( "  "  " a string "  "  " +error.getMessage());    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , new WSHumanTaskHandler());    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , new GenerateReportWorkItem());
