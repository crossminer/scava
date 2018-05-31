{
    Map<String, Object> params;
    final HornetQHTWorkItemHandler hornetQHTWorkItemHandler;
    KnowledgeBase kbase;
    try {
        StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
        final KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newThreadedFileLogger(ksession,  " a string " , number);
        ksession.getWorkItemManager().registerWorkItemHandler( " a string " , hornetQHTWorkItemHandler);
        ksession.startProcess( " a string " , params);
        // Do something with logger

    } catch (Throwable t) {
        // Do something
    }
}        final KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newThreadedFileLogger(ksession,  "  " a string "  " , number);        ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , hornetQHTWorkItemHandler);        ksession.startProcess( "  " a string "  " , params);        final KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newThreadedFileLogger(ksession,  "  " a string "  " , number);        ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , hornetQHTWorkItemHandler);        ksession.startProcess( "  " a string "  " , params);}        final KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newThreadedFileLogger(ksession,  "  "  " a string "  "  " , number);        ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , hornetQHTWorkItemHandler);        ksession.startProcess( "  "  " a string "  "  " , params);
