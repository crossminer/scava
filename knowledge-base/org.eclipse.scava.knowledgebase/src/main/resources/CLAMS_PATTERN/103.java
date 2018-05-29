{
    StatefulKnowledgeSession ksession;
    KnowledgeRuntimeLogger logger;
    try {
        logger.close();
        ksession.dispose();

    } catch (Throwable t) {
        // Do something
    }
}
