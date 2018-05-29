{
    TaskData taskData;
    WorkItem workItem;
    KnowledgeRuntime session;
    String taskName = (String) workItem.getParameter( " a string " );
    String comment = (String) workItem.getParameter( " a string " );
    String priorityString = (String) workItem.getParameter( " a string " );
    taskData.setWorkItemId(workItem.getId());
    taskData.setProcessInstanceId(workItem.getProcessInstanceId());
    if (session != null && session.getProcessInstance(workItem.getProcessInstanceId()) != null) {
        taskData.setProcessId(session.getProcessInstance(workItem.getProcessInstanceId()).getProcess().getId());
    }
    if (session != null && (session instanceof StatefulKnowledgeSession)) {
        taskData.setProcessSessionId(((StatefulKnowledgeSession) session).getId());
    }
    taskData.setSkipable(! " a string " .equals(workItem.getParameter( " a string " )));
    Long parentId = (Long) workItem.getParameter( " a string " );
    String actorId = (String) workItem.getParameter( " a string " );
    String groupId = (String) workItem.getParameter( " a string " );
    // Do something with taskName

    // Do something with comment

    // Do something with priorityString

    // Do something with parentId

    // Do something with actorId

    // Do something with groupId
}    String taskName = (String) workItem.getParameter( "  " a string "  " );    String comment = (String) workItem.getParameter( "  " a string "  " );    String priorityString = (String) workItem.getParameter( "  " a string "  " );    taskData.setSkipable(! "  " a string "  " .equals(workItem.getParameter( "  " a string "  " )));    Long parentId = (Long) workItem.getParameter( "  " a string "  " );    String actorId = (String) workItem.getParameter( "  " a string "  " );    String groupId = (String) workItem.getParameter( "  " a string "  " );    String taskName = (String) workItem.getParameter( "  " a string "  " );    String comment = (String) workItem.getParameter( "  " a string "  " );    String priorityString = (String) workItem.getParameter( "  " a string "  " );    taskData.setSkipable(! "  " a string "  " .equals(workItem.getParameter( "  " a string "  " )));    Long parentId = (Long) workItem.getParameter( "  " a string "  " );    String actorId = (String) workItem.getParameter( "  " a string "  " );    String groupId = (String) workItem.getParameter( "  " a string "  " );}    String taskName = (String) workItem.getParameter( "  "  " a string "  "  " );    String comment = (String) workItem.getParameter( "  "  " a string "  "  " );    String priorityString = (String) workItem.getParameter( "  "  " a string "  "  " );    taskData.setSkipable(! "  "  " a string "  "  " .equals(workItem.getParameter( "  "  " a string "  "  " )));    Long parentId = (Long) workItem.getParameter( "  "  " a string "  "  " );    String actorId = (String) workItem.getParameter( "  "  " a string "  "  " );    String groupId = (String) workItem.getParameter( "  "  " a string "  "  " );
