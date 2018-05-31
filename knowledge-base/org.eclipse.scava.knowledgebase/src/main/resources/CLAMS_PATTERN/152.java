{
    StatefulKnowledgeSession ksession;
    Environment env;
    int id = ksession.getId();
    KnowledgeBase kbase = ksession.getKnowledgeBase();
    KnowledgeSessionConfiguration config = ksession.getSessionConfiguration();
    StatefulKnowledgeSession result = JPAKnowledgeService.loadStatefulKnowledgeSession(id, kbase, config, env);
    // Do something with result
}
