{
    Object fact;
    StatefulKnowledgeSession session;
    FactHandle handle = session.getFactHandle(fact);
    if (handle != null) {
        session.retract(handle);
        session.fireAllRules();
    } else
    {
        // Do something
    }
}
