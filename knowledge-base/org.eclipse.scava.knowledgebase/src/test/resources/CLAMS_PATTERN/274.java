{
    Map<String, Object> params;
    Person person;
    RatesToday ratesToday;
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    kbuilder.add(new ClassPathResource( " a string " ), ResourceType.DRL);
    kbuilder.add(new ClassPathResource( " a string " ), ResourceType.DRL);
    kbuilder.add(new ClassPathResource( " a string " ), ResourceType.BPMN2);

    if (kbuilder.hasErrors()) {
        for (KnowledgeBuilderError error : kbuilder.getErrors()) {
            System.out.println( " a string "  + error.getMessage());
        }
    }

    KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();

    kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

    final StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
    KnowledgeRuntimeLoggerFactory.newConsoleLogger(ksession);

    ksession.addEventListener(
    new DefaultAgendaEventListener() {
        // Do something
    });

    ksession.addEventListener(new DefaultProcessEventListener() {
        // Do something
    });


    ProcessInstance processInstance = ksession.createProcessInstance( " a string " , params);
    assertEquals(processInstance.getState(), ProcessInstance.STATE_PENDING);
    FactHandle processtHandle = ksession.insert(processInstance);

    ksession.startProcessInstance(processInstance.getId());

    assertEquals(processInstance.getState(), ProcessInstance.STATE_COMPLETED);
    QueryResults queryResults = ksession.getQueryResults( " a string " , new Object[]{
                                    // Do something
                                });
    Iterator<QueryResultsRow> iterator = queryResults.iterator();

    QueryResultsRow ratesRow = iterator.next();
    assertEquals(ratesToday, ((ProcessVariable) ratesRow.get( " a string " )).getValue());

    QueryResultsRow personRow = iterator.next();
    assertEquals(person, ((ProcessVariable) personRow.get( " a string " )).getValue());

    ksession.retract(processtHandle);


}    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.DRL);    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.DRL);    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.BPMN2);            System.out.println( "  " a string "  "  + error.getMessage());    ProcessInstance processInstance = ksession.createProcessInstance( "  " a string "  " , params);    QueryResults queryResults = ksession.getQueryResults( "  " a string "  " , new Object[]{    assertEquals(ratesToday, ((ProcessVariable) ratesRow.get( "  " a string "  " )).getValue());    assertEquals(person, ((ProcessVariable) personRow.get( "  " a string "  " )).getValue());    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.DRL);    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.DRL);    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.BPMN2);            System.out.println( "  " a string "  "  + error.getMessage());    ProcessInstance processInstance = ksession.createProcessInstance( "  " a string "  " , params);    QueryResults queryResults = ksession.getQueryResults( "  " a string "  " , new Object[]{    assertEquals(ratesToday, ((ProcessVariable) ratesRow.get( "  " a string "  " )).getValue());    assertEquals(person, ((ProcessVariable) personRow.get( "  " a string "  " )).getValue());}    kbuilder.add(new ClassPathResource( "  "  " a string "  "  " ), ResourceType.DRL);    kbuilder.add(new ClassPathResource( "  "  " a string "  "  " ), ResourceType.DRL);    kbuilder.add(new ClassPathResource( "  "  " a string "  "  " ), ResourceType.BPMN2);            System.out.println( "  "  " a string "  "  "  + error.getMessage());    ProcessInstance processInstance = ksession.createProcessInstance( "  "  " a string "  "  " , params);    QueryResults queryResults = ksession.getQueryResults( "  "  " a string "  "  " , new Object[]{    assertEquals(ratesToday, ((ProcessVariable) ratesRow.get( "  "  " a string "  "  " )).getValue());    assertEquals(person, ((ProcessVariable) personRow.get( "  "  " a string "  "  " )).getValue());
