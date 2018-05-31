{
    StatefulKnowledgeSession session;
    SessionManager manager = new MVELSingleSessionManager(KnowledgeBaseFactory.newKnowledgeBase());

    assertEquals(number, session.getAgendaEventListeners().size());
    assertEquals(number, session.getProcessEventListeners().size());
    assertEquals(number, session.getWorkingMemoryEventListeners().size());
    // Do something with manager

}
