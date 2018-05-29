{
    Map<String, Object> output;
    WorkItemManager manager;
    WorkItem workItem;
    long workItemId = workItem.getId();
    Map<String, Object> input = workItem.getParameters();

    try {
        manager.completeWorkItem(workItemId, output);
    } catch (Exception e) {
        // Do something
    }
    // Do something with input

}
