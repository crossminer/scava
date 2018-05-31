{
    long processInstanceId;
    StatefulKnowledgeSession ksession;
    String processId;
    Map<String, Object> parameters;
    ProcessInstance process = ksession.startProcess(processId, parameters);
    this.processInstanceId = process.getId();
    ksession.dispose();
}
