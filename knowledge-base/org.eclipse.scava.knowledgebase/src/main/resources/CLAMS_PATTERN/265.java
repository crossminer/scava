{
    KnowledgeBase kbase;
    StatelessKnowledgeSession session;
    final Logger log;
    if (kbase != null) {
        synchronized (kbase) {
            session = kbase.newStatelessKnowledgeSession();
            if (log.isLoggable(Level.FINEST)) {
                KnowledgeRuntimeLoggerFactory.newConsoleLogger(session);
            }
        }
    }
}
