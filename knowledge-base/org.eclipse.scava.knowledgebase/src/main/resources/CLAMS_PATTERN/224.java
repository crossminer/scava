{
    ProcessInstance processInstance;
    WorkItem workItem;
    return findWorkItemNodeInstance(workItem.getId(), ((WorkflowProcessInstance) processInstance).getNodeInstances());
}
