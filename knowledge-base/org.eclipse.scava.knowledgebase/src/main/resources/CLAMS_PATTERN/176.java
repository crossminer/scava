{
    long taskId;
    WorkItem workItem;
    TaskChangeDetails changeDetails = (TaskChangeDetails)workItem.getParameter(TaskChangeDetails.TASK_CHANGE_DETAILS);
    taskProxy.skipTask(taskId, ITaskService.ADMINISTRATOR, workItem.getParameters(), boolean);

    kSessionProxy.completeWorkItem(ksessionId, workItem.getId(), workItem.getParameters());
    // Do something with changeDetails

}
