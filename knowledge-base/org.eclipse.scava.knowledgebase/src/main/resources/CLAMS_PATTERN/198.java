{
    Long processInstanceId;
    StatefulKnowledgeSession session;
    if ( session.getProcessInstance(processInstanceId) != null) {
        session.abortProcessInstance(processInstanceId);
    } else {
        // Do something
    }

}
