{
    Person person;
    StatefulKnowledgeSession ksession;
    KnowledgeRuntimeLoggerFactory.newConsoleLogger(ksession);

    for (Address address : person.getAddresses()) {
        ksession.insert(address);
    }

    for (Phone phone : person.getPhones()) {
        ksession.insert(phone);
    }

    ksession.insert(person);


    int fired = ksession.fireAllRules();
    org.drools.runtime.rule.QueryResults results =
    ksession.getQueryResults( " a string " , new Object[]{
        // Do something
    });
    // Do something with fired

    // Do something with results
}    ksession.getQueryResults( "  " a string "  " , new Object[]{    ksession.getQueryResults( "  " a string "  " , new Object[]{}    ksession.getQueryResults( "  "  " a string "  "  " , new Object[]{
