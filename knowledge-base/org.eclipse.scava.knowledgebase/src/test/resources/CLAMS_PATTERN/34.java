{
    StatefulKnowledgeSession ksession;
    Person person;
    ksession.insert(person);
    ksession.fireAllRules();
    ksession.insert(person);
    ksession.fireAllRules();
}
