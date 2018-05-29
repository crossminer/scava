{
    final StatefulKnowledgeSession session;
    synchronized (session) {
        session.notifyAll();
    }
}
