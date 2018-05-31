{
    Message message;
    KnowledgeBase kbase;
    try {
        StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
        KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession,  " a string " );
        ksession.insert(message);
        ksession.fireAllRules();
        logger.close();
    } catch (Throwable t) {
        // Do something
    }
}        KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession,  "  " a string "  " );        KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession,  "  " a string "  " );}        KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession,  "  "  " a string "  "  " );
