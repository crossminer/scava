{
    long contentId;
    long workItemId;
    Task task;
    WorkItemManager manager;
    Map<String, Object> results;
    if (task.getTaskData().getStatus() == Status.Completed) {
        if (contentId != -number) {
            // Do something
        } else {
            manager.completeWorkItem(workItemId, results);
        }
    } else {
        manager.abortWorkItem(workItemId);
    }
}
