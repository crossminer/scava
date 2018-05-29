{
    Logger logger;
    Environment env;
    WorkItem workItem;
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    kbuilder.add( new ClassPathResource(  " a string "  ),
    ResourceType.DRF );
    KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
    kbase.addKnowledgePackages( kbuilder.getKnowledgePackages() );

    StatefulKnowledgeSession ksession = JPAKnowledgeService.newStatefulKnowledgeSession( kbase, null, env );
    int origNumObjects = ksession.getObjects().size();
    int id = ksession.getId();

    ProcessInstance processInstance = ksession.startProcess(  " a string "  );
    ksession.insert(  " a string "  );
    logger.debug(  " a string "  + processInstance.getId() );

    ksession = JPAKnowledgeService.loadStatefulKnowledgeSession( id, kbase, null, env );
    processInstance = ksession.getProcessInstance( processInstance.getId() );
    ksession = JPAKnowledgeService.loadStatefulKnowledgeSession( id, kbase, null, env );
    ksession.getWorkItemManager().completeWorkItem( workItem.getId(),
    null );

    ksession = JPAKnowledgeService.loadStatefulKnowledgeSession( id, kbase, null, env );
    processInstance = ksession.getProcessInstance( processInstance.getId() );
    ksession = JPAKnowledgeService.loadStatefulKnowledgeSession( id, kbase, null, env );
    ksession.getWorkItemManager().completeWorkItem( workItem.getId(),
    null );

    ksession = JPAKnowledgeService.loadStatefulKnowledgeSession( id, kbase, null, env );
    processInstance = ksession.getProcessInstance( processInstance.getId() );
    ksession = JPAKnowledgeService.loadStatefulKnowledgeSession( id, kbase, null, env );
    ksession.getWorkItemManager().completeWorkItem( workItem.getId(),
    null );

    ksession = JPAKnowledgeService.loadStatefulKnowledgeSession( id, kbase, null, env );
    processInstance = ksession.getProcessInstance( processInstance.getId() );
    assertEquals( origNumObjects + number,
    ksession.getObjects().size() );
    for ( Object o : ksession.getObjects() ) {
        // Do something
    }
    // Do something with processInstance
}    
