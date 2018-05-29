{

    QueryResults queryResults;
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
    ksession.addEventListener(new DefaultAgendaEventListener() {
        // Do something
    });

    ksession.addEventListener(new DefaultWorkingMemoryEventListener() {
        // Do something
    });

    ksession.addEventListener(new DefaultProcessEventListener() {
        // Do something
    });
    try {
        ksession.insert(new Resources(number));

        ksession.insert(new Customer( " a string " , Customer.CustomerType.GOLD));

        queryResults = ksession.getQueryResults( " a string " , (Object[]) null);

        assertEquals(number, ((Resources) queryResults.iterator().next().get( " a string " )).getAvailable());

        ksession.insert(new Customer( " a string " , Customer.CustomerType.PLATINUM));

        queryResults = ksession.getQueryResults( " a string " , (Object[]) null);

        assertEquals(number, ((Resources) queryResults.iterator().next().get( " a string " )).getAvailable());

        ksession.insert(new Customer( " a string " , Customer.CustomerType.STARTER));

    } catch (ConsequenceException e) {
        assertEquals(boolean, e.getCause().getMessage().contains( " a string " ));
        queryResults = ksession.getQueryResults( " a string " , (Object[]) null);
        assertEquals(-number, ((Resources) queryResults.iterator().next().get( " a string " )).getAvailable());
    }

}    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.DRL);    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.DRL);    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.BPMN2);            System.out.println( "  " a string "  "  + error.getMessage());        ksession.insert(new Customer( "  " a string "  " , Customer.CustomerType.GOLD));        queryResults = ksession.getQueryResults( "  " a string "  " , (Object[]) null);        assertEquals(number, ((Resources) queryResults.iterator().next().get( "  " a string "  " )).getAvailable());        ksession.insert(new Customer( "  " a string "  " , Customer.CustomerType.PLATINUM));        queryResults = ksession.getQueryResults( "  " a string "  " , (Object[]) null);        assertEquals(number, ((Resources) queryResults.iterator().next().get( "  " a string "  " )).getAvailable());        ksession.insert(new Customer( "  " a string "  " , Customer.CustomerType.STARTER));        assertEquals(boolean, e.getCause().getMessage().contains( "  " a string "  " ));        queryResults = ksession.getQueryResults( "  " a string "  " , (Object[]) null);        assertEquals(-number, ((Resources) queryResults.iterator().next().get( "  " a string "  " )).getAvailable());    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.DRL);    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.DRL);    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.BPMN2);            System.out.println( "  " a string "  "  + error.getMessage());        ksession.insert(new Customer( "  " a string "  " , Customer.CustomerType.GOLD));        queryResults = ksession.getQueryResults( "  " a string "  " , (Object[]) null);        assertEquals(number, ((Resources) queryResults.iterator().next().get( "  " a string "  " )).getAvailable());        ksession.insert(new Customer( "  " a string "  " , Customer.CustomerType.PLATINUM));        queryResults = ksession.getQueryResults( "  " a string "  " , (Object[]) null);        assertEquals(number, ((Resources) queryResults.iterator().next().get( "  " a string "  " )).getAvailable());        ksession.insert(new Customer( "  " a string "  " , Customer.CustomerType.STARTER));        assertEquals(boolean, e.getCause().getMessage().contains( "  " a string "  " ));        queryResults = ksession.getQueryResults( "  " a string "  " , (Object[]) null);        assertEquals(-number, ((Resources) queryResults.iterator().next().get( "  " a string "  " )).getAvailable());}    kbuilder.add(new ClassPathResource( "  "  " a string "  "  " ), ResourceType.DRL);    kbuilder.add(new ClassPathResource( "  "  " a string "  "  " ), ResourceType.DRL);    kbuilder.add(new ClassPathResource( "  "  " a string "  "  " ), ResourceType.BPMN2);            System.out.println( "  "  " a string "  "  "  + error.getMessage());        ksession.insert(new Customer( "  "  " a string "  "  " , Customer.CustomerType.GOLD));        queryResults = ksession.getQueryResults( "  "  " a string "  "  " , (Object[]) null);        assertEquals(number, ((Resources) queryResults.iterator().next().get( "  "  " a string "  "  " )).getAvailable());        ksession.insert(new Customer( "  "  " a string "  "  " , Customer.CustomerType.PLATINUM));        queryResults = ksession.getQueryResults( "  "  " a string "  "  " , (Object[]) null);        assertEquals(number, ((Resources) queryResults.iterator().next().get( "  "  " a string "  "  " )).getAvailable());        ksession.insert(new Customer( "  "  " a string "  "  " , Customer.CustomerType.STARTER));        assertEquals(boolean, e.getCause().getMessage().contains( "  "  " a string "  "  " ));        queryResults = ksession.getQueryResults( "  "  " a string "  "  " , (Object[]) null);        assertEquals(-number, ((Resources) queryResults.iterator().next().get( "  "  " a string "  "  " )).getAvailable());
