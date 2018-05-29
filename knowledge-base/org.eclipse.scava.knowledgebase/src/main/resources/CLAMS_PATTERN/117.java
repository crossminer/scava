{
    KnowledgeRuntimeLogger fileLogger;
    File logFile;
    StatefulKnowledgeSession ksession;
    SyncWSHumanTaskHandler humanTaskHandler;
    KnowledgeRuntimeLoggerFactory.newConsoleLogger(ksession);

    fileLogger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession,logFile.getAbsolutePath());

    this.ksession.getWorkItemManager().registerWorkItemHandler( " a string " , humanTaskHandler);
    // Do something with fileLogger


}    this.ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , humanTaskHandler);    this.ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , humanTaskHandler);}    this.ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , humanTaskHandler);
