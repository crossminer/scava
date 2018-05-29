{
    Environment env;
    int sessionId;
    KnowledgeBase kbase;
    return JPAKnowledgeService.loadStatefulKnowledgeSession(sessionId, kbase, null, env);
}
