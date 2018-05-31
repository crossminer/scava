{
    RemoteServiceEvent event;
    WorkingMemoryEntryPoint provisionEventsStream;
    StatefulKnowledgeSession session;
    provisionEventsStream.insert(event);
    session.fireAllRules();
}
