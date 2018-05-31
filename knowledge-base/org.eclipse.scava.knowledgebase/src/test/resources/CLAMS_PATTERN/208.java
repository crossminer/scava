{
    boolean persistence;
    WorkingMemoryInMemoryLogger logger;
    KnowledgeBase kbase;
    Environment env;
    if (persistence) {
        StatefulKnowledgeSession result = JPAKnowledgeService.newStatefulKnowledgeSession(kbase, null, env);
        JPAProcessInstanceDbLog.setEnvironment(result.getEnvironment());
    } else {
        StatefulKnowledgeSession result = kbase.newStatefulKnowledgeSession();
        logger = new WorkingMemoryInMemoryLogger(result);
        // Do something with logger
    }
}
