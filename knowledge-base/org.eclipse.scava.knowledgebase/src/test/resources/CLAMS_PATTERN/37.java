{
    KnowledgeRuntimeLogger fileLogger;
    File logFile;
    StatefulKnowledgeSession ksession;
    KnowledgeRuntimeLoggerFactory.newConsoleLogger(ksession);

    fileLogger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession,logFile.getAbsolutePath());
    // Do something with fileLogger

}
