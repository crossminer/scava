{
    Properties properties;
    Environment env;
    KnowledgeBase kbase;
    KnowledgeSessionConfiguration config = KnowledgeBaseFactory.newKnowledgeSessionConfiguration(properties);
    StatefulKnowledgeSession session = JPAKnowledgeService.newStatefulKnowledgeSession(kbase, config, env);
    session.getWorkItemManager().registerWorkItemHandler( " a string " , new SystemOutWorkItemHandler());

    long processInstanceId = session.startProcess( " a string " ).getId();
    // Do something with processInstanceId
}    session.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new SystemOutWorkItemHandler());    long processInstanceId = session.startProcess( "  " a string "  " ).getId();    session.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new SystemOutWorkItemHandler());    long processInstanceId = session.startProcess( "  " a string "  " ).getId();}    session.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , new SystemOutWorkItemHandler());    long processInstanceId = session.startProcess( "  "  " a string "  "  " ).getId();
