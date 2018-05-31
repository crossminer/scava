{
    KnowledgeEngineInitializer initializer;
    StatefulKnowledgeSession session;
    session.addEventListener(initializer.getTrackingAgendaEventListener());
    session.fireAllRules();
}
