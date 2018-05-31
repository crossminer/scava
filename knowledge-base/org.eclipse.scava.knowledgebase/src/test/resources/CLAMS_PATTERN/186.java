{
    StatefulKnowledgeSession session;
    List objects;
    Map<String, Object> globals;
    if (session != null) {
        try {
            for (Map.Entry<String, Object> e : globals.entrySet()) {
                session.setGlobal(e.getKey(), e.getValue());
            }
            for (Object o : objects) {
                synchronized (session) {
                    session.insert(o);
                }
                session.fireAllRules();
            }
            return session.getObjects();
        } finally {
            session.dispose();
        }
    }
}
