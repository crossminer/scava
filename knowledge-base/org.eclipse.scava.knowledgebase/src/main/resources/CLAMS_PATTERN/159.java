{
    Map<String, Object> params;
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

    kbuilder.add(new ClassPathResource( " a string " ), ResourceType.BPMN2);
    if (kbuilder.hasErrors()) {
        for (KnowledgeBuilderError error : kbuilder.getErrors()) {
            System.out.println( " a string "  + error.getMessage());
        }
    }

    KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();

    kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

    StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();

    ksession.getWorkItemManager().registerWorkItemHandler( " a string " , new RankCarWorkItemHandler());

    ksession.getWorkItemManager().registerWorkItemHandler( " a string " , new DefineCarPriceWorkItemHandler());

    ProcessInstance processInstance = ksession.createProcessInstance( " a string " , params);

    assertEquals(processInstance.getState(), ProcessInstance.STATE_PENDING);

    ksession.startProcessInstance(processInstance.getId());
}    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.BPMN2);            System.out.println( "  " a string "  "  + error.getMessage());    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new RankCarWorkItemHandler());    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new DefineCarPriceWorkItemHandler());    ProcessInstance processInstance = ksession.createProcessInstance( "  " a string "  " , params);    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.BPMN2);            System.out.println( "  " a string "  "  + error.getMessage());    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new RankCarWorkItemHandler());    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new DefineCarPriceWorkItemHandler());    ProcessInstance processInstance = ksession.createProcessInstance( "  " a string "  " , params);}    kbuilder.add(new ClassPathResource( "  "  " a string "  "  " ), ResourceType.BPMN2);            System.out.println( "  "  " a string "  "  "  + error.getMessage());    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , new RankCarWorkItemHandler());    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , new DefineCarPriceWorkItemHandler());    ProcessInstance processInstance = ksession.createProcessInstance( "  "  " a string "  "  " , params);
