{
    StatefulKnowledgeSession ksession;
    ksession.addEventListener(new DefaultProcessEventListener() {
        // Do something
    });
    for (int i = number; i < number; i++) {
        ksession.fireAllRules();
    }
}
