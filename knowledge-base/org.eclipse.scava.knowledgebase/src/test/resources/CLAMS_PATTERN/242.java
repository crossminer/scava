{
    ProcessBag processBag;
    Map<String, Object> params;
    KnowledgeRuntime session;
    String processId;
    ProcessInstance processInstance = session.createProcessInstance(processId, params);
    session.insert(processInstance);
    processBag.setProcessId(String.valueOf(processInstance.getId()));
    session.startProcessInstance(processInstance.getId());
    return processInstance.getId();
}
