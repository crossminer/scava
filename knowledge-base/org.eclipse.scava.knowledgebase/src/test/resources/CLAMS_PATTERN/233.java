{
    Map<String, Object> params;
    MockExternalServiceWorkItemHandler mockExternalServiceWorkItemHandler;
    MockHTWorkItemHandler mockHTWorkItemHandler;
    EntityManagerFactory emf;
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    kbuilder.add(new ClassPathResource( " a string " ), ResourceType.BPMN2);

    if (kbuilder.hasErrors()) {
        for (KnowledgeBuilderError error : kbuilder.getErrors()) {
            System.out.println( " a string "  + error.getMessage());
        }
    }

    KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();

    kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
    Environment env = EnvironmentFactory.newEnvironment();
    env.set(EnvironmentName.ENTITY_MANAGER_FACTORY, emf);
    env.set(EnvironmentName.TRANSACTION_MANAGER, TransactionManagerServices.getTransactionManager());
    final StatefulKnowledgeSession ksession = JPAKnowledgeService.newStatefulKnowledgeSession(kbase, null, env);
    int sessionId = ksession.getId();
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " , mockHTWorkItemHandler);
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " , mockExternalServiceWorkItemHandler);
    ProcessInstance processInstance = ksession.createProcessInstance( " a string " , params);
    long processInstanceOne = processInstance.getId();
    ksession.startProcessInstance(processInstanceOne);


    ksession.dispose();

    StatefulKnowledgeSession loadedKsession = JPAKnowledgeService.loadStatefulKnowledgeSession(sessionId, kbase, null, env);
    loadedKsession.getWorkItemManager().registerWorkItemHandler( " a string " , mockHTWorkItemHandler);
    loadedKsession.getWorkItemManager().registerWorkItemHandler( " a string " , mockExternalServiceWorkItemHandler);
    processInstance = loadedKsession.createProcessInstance( " a string " , params);
    long processInstanceTwo = processInstance.getId();
    loadedKsession.startProcessInstance(processInstanceTwo);
    loadedKsession.dispose();

    StatefulKnowledgeSession checkKsession = JPAKnowledgeService.loadStatefulKnowledgeSession(sessionId, kbase, null, env);
    assertEquals(number, checkKsession.getProcessInstances().size());
    checkKsession.dispose();



}    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.BPMN2);            System.out.println( "  " a string "  "  + error.getMessage());    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , mockHTWorkItemHandler);    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , mockExternalServiceWorkItemHandler);    ProcessInstance processInstance = ksession.createProcessInstance( "  " a string "  " , params);    loadedKsession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , mockHTWorkItemHandler);    loadedKsession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , mockExternalServiceWorkItemHandler);    processInstance = loadedKsession.createProcessInstance( "  " a string "  " , params);    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.BPMN2);            System.out.println( "  " a string "  "  + error.getMessage());    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , mockHTWorkItemHandler);    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , mockExternalServiceWorkItemHandler);    ProcessInstance processInstance = ksession.createProcessInstance( "  " a string "  " , params);    loadedKsession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , mockHTWorkItemHandler);    loadedKsession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , mockExternalServiceWorkItemHandler);    processInstance = loadedKsession.createProcessInstance( "  " a string "  " , params);}    kbuilder.add(new ClassPathResource( "  "  " a string "  "  " ), ResourceType.BPMN2);            System.out.println( "  "  " a string "  "  "  + error.getMessage());    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , mockHTWorkItemHandler);    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , mockExternalServiceWorkItemHandler);    ProcessInstance processInstance = ksession.createProcessInstance( "  "  " a string "  "  " , params);    loadedKsession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , mockHTWorkItemHandler);    loadedKsession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , mockExternalServiceWorkItemHandler);    processInstance = loadedKsession.createProcessInstance( "  "  " a string "  "  " , params);
