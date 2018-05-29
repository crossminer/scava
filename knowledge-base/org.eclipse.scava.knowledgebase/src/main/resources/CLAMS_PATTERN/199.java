{
    long processInstanceId;
    StatefulKnowledgeSession ksession;
    WorkflowProcessInstance process = (WorkflowProcessInstance) ksession.getProcessInstance(this.processInstanceId);
    return process.getState();
}
