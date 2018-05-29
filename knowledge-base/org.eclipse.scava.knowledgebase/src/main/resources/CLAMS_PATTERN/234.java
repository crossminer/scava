{
    Threshold avgThreshold;
    Task task;
    List<TaskSummary> tasks;
    SessionPseudoClock clock;
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

    kbuilder.add(new ClassPathResource( " a string " ), ResourceType.DRL);
    KnowledgeBuilderErrors errors = kbuilder.getErrors();

    if (errors.size() > number) {
        for (KnowledgeBuilderError error : errors) {
            System.out.println(error.getMessage());
        }
    }

    KnowledgeBaseConfiguration kbaseConf = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
    kbaseConf.setOption(EventProcessingOption.STREAM);
    KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase(kbaseConf);
    kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
    KnowledgeSessionConfiguration ksessionConf = KnowledgeBaseFactory.newKnowledgeSessionConfiguration();
    ksessionConf.setOption(ClockTypeOption.get( " a string " ));
    final StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession(ksessionConf, null);
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " , new WSHumanTaskHandler());
    KnowledgeRuntimeLoggerFactory.newConsoleLogger(ksession);
    ksession.insert(task);
    ksession.insert(new Average(number));

    ksession.insert(avgThreshold);

    ksession.insert(new User( " a string " ));
    clock = ksession.getSessionClock();

    System.out.println( " a string " +clock.getCurrentTime());
    ksession.getWorkingMemoryEntryPoint( " a string " ).insert(new WaterFlowingEvent(number));
    ksession.fireAllRules();
    clock.advanceTime(number, TimeUnit.MILLISECONDS);
    ksession.getWorkingMemoryEntryPoint( " a string " ).insert(new WaterFlowingEvent(number));
    ksession.fireAllRules();
    clock.advanceTime(number, TimeUnit.MILLISECONDS);
    ksession.getWorkingMemoryEntryPoint( " a string " ).insert(new WaterFlowingEvent(number));
    ksession.fireAllRules();
    clock.advanceTime(number, TimeUnit.MILLISECONDS);
    ksession.getWorkingMemoryEntryPoint( " a string " ).insert(new WaterFlowingEvent(number));
    ksession.fireAllRules();
    clock.advanceTime(number, TimeUnit.MILLISECONDS);
    ksession.getWorkingMemoryEntryPoint( " a string " ).insert(new WaterFlowingEvent(number));
    ksession.fireAllRules();
    clock.advanceTime(number, TimeUnit.MILLISECONDS);
    System.out.println( " a string " +clock.getCurrentTime());
    ksession.getWorkingMemoryEntryPoint( " a string " ).insert(new WaterFlowingEvent(number));
    ksession.fireAllRules();
    clock.advanceTime(number, TimeUnit.MILLISECONDS);
    ksession.getWorkingMemoryEntryPoint( " a string " ).insert(new WaterFlowingEvent(number));
    ksession.fireAllRules();
    clock.advanceTime(number, TimeUnit.MILLISECONDS);
    ksession.getWorkingMemoryEntryPoint( " a string " ).insert(new WaterFlowingEvent(number));
    ksession.fireAllRules();
    clock.advanceTime(number, TimeUnit.MILLISECONDS);
    ksession.getWorkingMemoryEntryPoint( " a string " ).insert(new WaterFlowingEvent(number));
    ksession.fireAllRules();
    clock.advanceTime(number, TimeUnit.MILLISECONDS);
    ksession.getWorkingMemoryEntryPoint( " a string " ).insert(new WaterFlowingEvent(number));
    ksession.fireAllRules();
    clock.advanceTime(number, TimeUnit.MILLISECONDS);
    System.out.println( " a string " +clock.getCurrentTime());


    org.drools.runtime.rule.QueryResults results =
        ksession.getQueryResults( " a string " , new Object[]{
                                     // Do something
                                 });

    for (QueryResultsRow row : results) {
        System.out.println( " a string " +((Average) row.get( " a string " )).getValue().toString());
        assertEquals( " a string " , ((Average) row.get( " a string " )).getValue().toString());
    }

    assertEquals(number, tasks.size());
    System.out.println( " a string " +tasks.get(number).getName());

    System.out.println( " a string " +clock.getCurrentTime());
    ksession.getWorkingMemoryEntryPoint( " a string " ).insert(new WaterFlowingEvent(number));
    ksession.fireAllRules();
    clock.advanceTime(number, TimeUnit.MILLISECONDS);
    ksession.getWorkingMemoryEntryPoint( " a string " ).insert(new WaterFlowingEvent(number));
    ksession.fireAllRules();
    clock.advanceTime(number, TimeUnit.MILLISECONDS);
    ksession.getWorkingMemoryEntryPoint( " a string " ).insert(new WaterFlowingEvent(number));
    ksession.fireAllRules();
    clock.advanceTime(number, TimeUnit.MILLISECONDS);
    ksession.getWorkingMemoryEntryPoint( " a string " ).insert(new WaterFlowingEvent(number));
    ksession.fireAllRules();
    clock.advanceTime(number, TimeUnit.MILLISECONDS);
    ksession.getWorkingMemoryEntryPoint( " a string " ).insert(new WaterFlowingEvent(number));
    ksession.fireAllRules();
    clock.advanceTime(number, TimeUnit.MILLISECONDS);
    System.out.println( " a string " +clock.getCurrentTime());
    ksession.getWorkingMemoryEntryPoint( " a string " ).insert(new WaterFlowingEvent(number));
    ksession.fireAllRules();
    clock.advanceTime(number, TimeUnit.MILLISECONDS);
    ksession.getWorkingMemoryEntryPoint( " a string " ).insert(new WaterFlowingEvent(number));
    ksession.fireAllRules();
    clock.advanceTime(number, TimeUnit.MILLISECONDS);
    ksession.getWorkingMemoryEntryPoint( " a string " ).insert(new WaterFlowingEvent(number));
    ksession.fireAllRules();
    clock.advanceTime(number, TimeUnit.MILLISECONDS);
    ksession.getWorkingMemoryEntryPoint( " a string " ).insert(new WaterFlowingEvent(number));
    ksession.fireAllRules();
    clock.advanceTime(number, TimeUnit.MILLISECONDS);
    ksession.getWorkingMemoryEntryPoint( " a string " ).insert(new WaterFlowingEvent(number));
    ksession.fireAllRules();
    clock.advanceTime(number, TimeUnit.MILLISECONDS);
    System.out.println( " a string " +clock.getCurrentTime());

    results = ksession.getQueryResults( " a string " , new Object[]{
                                           // Do something
                                       });

    for (QueryResultsRow row : results) {
        System.out.println( " a string " +((Average) row.get( " a string " )).getValue().toString());
        assertEquals( " a string " , ((Average) row.get( " a string " )).getValue().toString());
    }

    assertEquals(number, tasks.size());
}    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.DRL);    ksessionConf.setOption(ClockTypeOption.get( "  " a string "  " ));    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new WSHumanTaskHandler());    ksession.insert(new User( "  " a string "  " ));    System.out.println( "  " a string "  " +clock.getCurrentTime());    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    System.out.println( "  " a string "  " +clock.getCurrentTime());    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    System.out.println( "  " a string "  " +clock.getCurrentTime());        ksession.getQueryResults( "  " a string "  " , new Object[]{        System.out.println( "  " a string "  " +((Average) row.get( "  " a string "  " )).getValue().toString());        assertEquals( "  " a string "  " , ((Average) row.get( "  " a string "  " )).getValue().toString());    System.out.println( "  " a string "  " +tasks.get(number).getName());    System.out.println( "  " a string "  " +clock.getCurrentTime());    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    System.out.println( "  " a string "  " +clock.getCurrentTime());    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    System.out.println( "  " a string "  " +clock.getCurrentTime());    results = ksession.getQueryResults( "  " a string "  " , new Object[]{        System.out.println( "  " a string "  " +((Average) row.get( "  " a string "  " )).getValue().toString());        assertEquals( "  " a string "  " , ((Average) row.get( "  " a string "  " )).getValue().toString());    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.DRL);    ksessionConf.setOption(ClockTypeOption.get( "  " a string "  " ));    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new WSHumanTaskHandler());    ksession.insert(new User( "  " a string "  " ));    System.out.println( "  " a string "  " +clock.getCurrentTime());    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    System.out.println( "  " a string "  " +clock.getCurrentTime());    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    System.out.println( "  " a string "  " +clock.getCurrentTime());        ksession.getQueryResults( "  " a string "  " , new Object[]{        System.out.println( "  " a string "  " +((Average) row.get( "  " a string "  " )).getValue().toString());        assertEquals( "  " a string "  " , ((Average) row.get( "  " a string "  " )).getValue().toString());    System.out.println( "  " a string "  " +tasks.get(number).getName());    System.out.println( "  " a string "  " +clock.getCurrentTime());    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    System.out.println( "  " a string "  " +clock.getCurrentTime());    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  " a string "  " ).insert(new WaterFlowingEvent(number));    System.out.println( "  " a string "  " +clock.getCurrentTime());    results = ksession.getQueryResults( "  " a string "  " , new Object[]{        System.out.println( "  " a string "  " +((Average) row.get( "  " a string "  " )).getValue().toString());        assertEquals( "  " a string "  " , ((Average) row.get( "  " a string "  " )).getValue().toString());}    kbuilder.add(new ClassPathResource( "  "  " a string "  "  " ), ResourceType.DRL);    ksessionConf.setOption(ClockTypeOption.get( "  "  " a string "  "  " ));    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , new WSHumanTaskHandler());    ksession.insert(new User( "  "  " a string "  "  " ));    System.out.println( "  "  " a string "  "  " +clock.getCurrentTime());    ksession.getWorkingMemoryEntryPoint( "  "  " a string "  "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  "  " a string "  "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  "  " a string "  "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  "  " a string "  "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  "  " a string "  "  " ).insert(new WaterFlowingEvent(number));    System.out.println( "  "  " a string "  "  " +clock.getCurrentTime());    ksession.getWorkingMemoryEntryPoint( "  "  " a string "  "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  "  " a string "  "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  "  " a string "  "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  "  " a string "  "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  "  " a string "  "  " ).insert(new WaterFlowingEvent(number));    System.out.println( "  "  " a string "  "  " +clock.getCurrentTime());        ksession.getQueryResults( "  "  " a string "  "  " , new Object[]{        System.out.println( "  "  " a string "  "  " +((Average) row.get( "  "  " a string "  "  " )).getValue().toString());        assertEquals( "  "  " a string "  "  " , ((Average) row.get( "  "  " a string "  "  " )).getValue().toString());    System.out.println( "  "  " a string "  "  " +tasks.get(number).getName());    System.out.println( "  "  " a string "  "  " +clock.getCurrentTime());    ksession.getWorkingMemoryEntryPoint( "  "  " a string "  "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  "  " a string "  "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  "  " a string "  "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  "  " a string "  "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  "  " a string "  "  " ).insert(new WaterFlowingEvent(number));    System.out.println( "  "  " a string "  "  " +clock.getCurrentTime());    ksession.getWorkingMemoryEntryPoint( "  "  " a string "  "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  "  " a string "  "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  "  " a string "  "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  "  " a string "  "  " ).insert(new WaterFlowingEvent(number));    ksession.getWorkingMemoryEntryPoint( "  "  " a string "  "  " ).insert(new WaterFlowingEvent(number));    System.out.println( "  "  " a string "  "  " +clock.getCurrentTime());    results = ksession.getQueryResults( "  "  " a string "  "  " , new Object[]{        System.out.println( "  "  " a string "  "  " +((Average) row.get( "  "  " a string "  "  " )).getValue().toString());        assertEquals( "  "  " a string "  "  " , ((Average) row.get( "  "  " a string "  "  " )).getValue().toString());
