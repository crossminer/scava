{
    TaskServiceEntryPoint taskService;
    TestWorkItemManager manager;
    WorkItemImpl workItem = new WorkItemImpl();
    workItem.setName( " a string " );
    workItem.setParameter( " a string " ,  " a string " );
    workItem.setParameter( " a string " ,  " a string " );
    workItem.setParameter( " a string " ,  " a string " );
    workItem.setParameter( " a string " ,  " a string " );
    getHandler().executeWorkItem(workItem, manager);

    Task task = taskService.getTaskByWorkItemId(workItem.getId());

    taskService.exit(task.getId(),  " a string " );

    task = taskService.getTaskByWorkItemId(workItem.getId());
    // Do something with task
}    workItem.setName( "  " a string "  " );    workItem.setParameter( "  " a string "  " ,  "  " a string "  " );    workItem.setParameter( "  " a string "  " ,  "  " a string "  " );    workItem.setParameter( "  " a string "  " ,  "  " a string "  " );    workItem.setParameter( "  " a string "  " ,  "  " a string "  " );    taskService.exit(task.getId(),  "  " a string "  " );    workItem.setName( "  " a string "  " );    workItem.setParameter( "  " a string "  " ,  "  " a string "  " );    workItem.setParameter( "  " a string "  " ,  "  " a string "  " );    workItem.setParameter( "  " a string "  " ,  "  " a string "  " );    workItem.setParameter( "  " a string "  " ,  "  " a string "  " );    taskService.exit(task.getId(),  "  " a string "  " );}    workItem.setName( "  "  " a string "  "  " );    workItem.setParameter( "  "  " a string "  "  " ,  "  "  " a string "  "  " );    workItem.setParameter( "  "  " a string "  "  " ,  "  "  " a string "  "  " );    workItem.setParameter( "  "  " a string "  "  " ,  "  "  " a string "  "  " );    workItem.setParameter( "  "  " a string "  "  " ,  "  "  " a string "  "  " );    taskService.exit(task.getId(),  "  "  " a string "  "  " );
