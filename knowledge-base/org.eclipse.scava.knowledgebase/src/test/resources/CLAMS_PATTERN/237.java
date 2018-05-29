{



    LocalHTWorkItemHandler htHandler;
    StatefulKnowledgeSession ksession;
    TasksAdmin admin;
    List<TaskSummary> doctorTasks;
    List<TaskSummary> managerTasks;
    LocalTaskService localTaskService;
    List<TaskSummary> frontDeskTasks;
    Map<String, Object> parameters;
    KnowledgeRuntimeLoggerFactory.newConsoleLogger(ksession);
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " , htHandler);
    ksession.addEventListener(new TaskCleanUpProcessEventListener(admin));

    ProcessInstance process = ksession.startProcess( " a string " , parameters);
    long processInstanceId = process.getId();

    Assert.assertEquals(ProcessInstance.STATE_ACTIVE, process.getState());

    this.localTaskService.start(frontDeskTasks.get(number).getId(),  " a string " );

    this.localTaskService.complete(frontDeskTasks.get(number).getId(),  " a string " , null);

    this.localTaskService.start(doctorTasks.get(number).getId(),  " a string " );

    this.localTaskService.complete(doctorTasks.get(number).getId(),  " a string " , null);

    this.localTaskService.start(managerTasks.get(number).getId(),  " a string " );

    this.localTaskService.complete(managerTasks.get(number).getId(),  " a string " , null);

    process = ksession.getProcessInstance(process.getId());
    // Do something with processInstanceId

    // Do something with process
}    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , htHandler);    ProcessInstance process = ksession.startProcess( "  " a string "  " , parameters);    this.localTaskService.start(frontDeskTasks.get(number).getId(),  "  " a string "  " );    this.localTaskService.complete(frontDeskTasks.get(number).getId(),  "  " a string "  " , null);    this.localTaskService.start(doctorTasks.get(number).getId(),  "  " a string "  " );    this.localTaskService.complete(doctorTasks.get(number).getId(),  "  " a string "  " , null);    this.localTaskService.start(managerTasks.get(number).getId(),  "  " a string "  " );    this.localTaskService.complete(managerTasks.get(number).getId(),  "  " a string "  " , null);    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , htHandler);    ProcessInstance process = ksession.startProcess( "  " a string "  " , parameters);    this.localTaskService.start(frontDeskTasks.get(number).getId(),  "  " a string "  " );    this.localTaskService.complete(frontDeskTasks.get(number).getId(),  "  " a string "  " , null);    this.localTaskService.start(doctorTasks.get(number).getId(),  "  " a string "  " );    this.localTaskService.complete(doctorTasks.get(number).getId(),  "  " a string "  " , null);    this.localTaskService.start(managerTasks.get(number).getId(),  "  " a string "  " );    this.localTaskService.complete(managerTasks.get(number).getId(),  "  " a string "  " , null);}    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , htHandler);    ProcessInstance process = ksession.startProcess( "  "  " a string "  "  " , parameters);    this.localTaskService.start(frontDeskTasks.get(number).getId(),  "  "  " a string "  "  " );    this.localTaskService.complete(frontDeskTasks.get(number).getId(),  "  "  " a string "  "  " , null);    this.localTaskService.start(doctorTasks.get(number).getId(),  "  "  " a string "  "  " );    this.localTaskService.complete(doctorTasks.get(number).getId(),  "  "  " a string "  "  " , null);    this.localTaskService.start(managerTasks.get(number).getId(),  "  "  " a string "  "  " );    this.localTaskService.complete(managerTasks.get(number).getId(),  "  "  " a string "  "  " , null);
