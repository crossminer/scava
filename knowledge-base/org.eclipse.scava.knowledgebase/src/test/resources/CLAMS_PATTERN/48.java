{
    CommandBasedHornetQWSHumanTaskHandler wsHumanTaskHandler;
    StatefulKnowledgeSession session;
    List<TaskSummary> tasksUser2;
    KnowledgeBase kbase;
    List<TaskSummary> tasks;
    session = JPAKnowledgeService.newStatefulKnowledgeSession(kbase, null,
    env);

    KnowledgeRuntimeLoggerFactory.newConsoleLogger(session);

    JPAProcessInstanceDbLog processLog = new JPAProcessInstanceDbLog(
        session.getEnvironment());

    session.getWorkItemManager().registerWorkItemHandler( " a string " ,
    wsHumanTaskHandler);
    ProcessInstance process = session.createProcessInstance( " a string " ,
    null);
    session.insert(process);
    long processInstanceId = process.getId();
    session.startProcessInstance(processInstanceId);
    long taskId = tasks.get(number).getId();

    Assert.assertEquals(taskId, tasksUser2.get(number).getId());
    // Do something with processLog
}    session.getWorkItemManager().registerWorkItemHandler( "  " a string "  " ,    ProcessInstance process = session.createProcessInstance( "  " a string "  " ,    session.getWorkItemManager().registerWorkItemHandler( "  " a string "  " ,    ProcessInstance process = session.createProcessInstance( "  " a string "  " ,}    session.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " ,    ProcessInstance process = session.createProcessInstance( "  "  " a string "  "  " ,
