{
    Set<Long> processIds;
    Event event;
    StatefulKnowledgeSession session;
    FactHandle factHandle;
    try {
        factHandle = session.insert(event);
        try {
            session.fireAllRules();
        } catch (ConsequenceException e) {
            throw new WorkflowException( " a string " , e.getCause());
        } finally {
            // Do something
        }

        if (processIds.isEmpty()) {
            for (ProcessInstance p : session.getProcessInstances()) {
                p.signalEvent(event.getClass().getSimpleName(), event);
            }
        } else {
            // Do something
        }
    } finally {
        session.retract(factHandle);
    }
}            throw new WorkflowException( "  " a string "  " , e.getCause());            throw new WorkflowException( "  " a string "  " , e.getCause());}            throw new WorkflowException( "  "  " a string "  "  " , e.getCause());
