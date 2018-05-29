{
    StatefulKnowledgeSession crmPersistentSession;
    String processId;
    KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
    ((AbstractRuleBase) ((InternalKnowledgeBase) kbase).getRuleBase())
    .addProcess( ProcessCreatorForHelp.newShortestProcess( processId ) );

    crmPersistentSession.startProcess(processId);

    crmPersistentSession.dispose();
}
