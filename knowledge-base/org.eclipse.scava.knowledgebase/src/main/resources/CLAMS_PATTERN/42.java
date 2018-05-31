{
    final StatefulKnowledgeSession clueSession;
    final TripleList possibilities;
    clueSession.addEventListener(new AfterActivationFiredEventListener());
    clueSession.insert(possibilities);
}
