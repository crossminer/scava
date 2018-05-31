{
    StringBuffer logMsg;
    WorkItemManager manager;
    WorkItem workItem;
    try {
        // Do something
    } catch (Exception e) {
        if (action.equals(OnErrorAction.ABORT)) {
            manager.abortWorkItem(workItem.getId());
        } else  else if (action.equals(OnErrorAction.LOG)) {
                logMsg.append(new Date() +  " a string "  + workItem.getId());
            }
    }
}                logMsg.append(new Date() +  "  " a string "  "  + workItem.getId());                logMsg.append(new Date() +  "  " a string "  "  + workItem.getId());}                logMsg.append(new Date() +  "  "  " a string "  "  "  + workItem.getId());
