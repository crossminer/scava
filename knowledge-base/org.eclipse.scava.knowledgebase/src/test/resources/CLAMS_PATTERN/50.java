{
    StatefulKnowledgeSession ksession;
    KnowledgeBase kbase;
    Environment env;
    int sessionId = ksession.getId();
    ksession.dispose();
    return JPAKnowledgeService.loadStatefulKnowledgeSession( sessionId, kbase, null, env);
}
