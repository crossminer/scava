{
    Admission admission;
    TestWorkItemHandler testHandler;
    final String DOMAIN;
    StatefulKnowledgeSession knowledgeSession;
    knowledgeSession.getWorkItemManager().registerWorkItemHandler( " a string " , testHandler);
    knowledgeSession.getWorkItemManager().registerWorkItemHandler( " a string " , testHandler);

    knowledgeSession.startProcess(DOMAIN +  " a string " , getProcessParameters(admission));

    for (int i = number; i < number; i++) {
        knowledgeSession.getWorkItemManager().completeWorkItem(testHandler.getWorkItem().getId(), null);
    }
}    knowledgeSession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , testHandler);    knowledgeSession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , testHandler);    knowledgeSession.startProcess(DOMAIN +  "  " a string "  " , getProcessParameters(admission));    knowledgeSession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , testHandler);    knowledgeSession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , testHandler);    knowledgeSession.startProcess(DOMAIN +  "  " a string "  " , getProcessParameters(admission));}    knowledgeSession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , testHandler);    knowledgeSession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , testHandler);    knowledgeSession.startProcess(DOMAIN +  "  "  " a string "  "  " , getProcessParameters(admission));
