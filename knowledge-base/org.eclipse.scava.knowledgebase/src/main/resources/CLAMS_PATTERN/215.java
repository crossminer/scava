{
    Map<String, Object> params;
    final StatefulKnowledgeSession processKsession;
    final TaskSpeed taskSpeed;
    final StatefulKnowledgeSession eventsKsession;
    eventsKsession.setGlobal( " a string " , taskSpeed);

    processKsession.getWorkItemManager().registerWorkItemHandler( " a string " , new WorkItemHandler() {
        public void executeWorkItem(WorkItem wi, WorkItemManager wim) {
            System.out.println( " a string "  + wi.getName() +  " a string "  + wi.getId());
            wim.completeWorkItem(wi.getId(), null);
        }
    });
    processKsession.addEventListener(new DefaultProcessEventListener() {
        @Override
        public void beforeProcessStarted(ProcessStartedEvent event) {
            eventsKsession.insert(event);
        }
        @Override
        public void afterProcessCompleted(ProcessCompletedEvent event) {
            eventsKsession.insert(event);
        }
        @Override
        public void beforeNodeLeft(org.drools.event.process.ProcessNodeLeftEvent event) {
            eventsKsession.insert(event);
        }
    });
    for (int i = number; i < number; i++) {
        ProcessInstance processInstance = processKsession.createProcessInstance( " a string " , params);
        assertEquals(processInstance.getState(), ProcessInstance.STATE_PENDING);
        FactHandle processtHandle = processKsession.insert(processInstance);
        System.out.println( " a string " +processInstance.getId());
        processKsession.startProcessInstance(processInstance.getId());
        assertEquals(processInstance.getState(), ProcessInstance.STATE_COMPLETED);
        processKsession.retract(processtHandle);
    }




}    eventsKsession.setGlobal( "  " a string "  " , taskSpeed);    processKsession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new WorkItemHandler() {            System.out.println( "  " a string "  "  + wi.getName() +  "  " a string "  "  + wi.getId());        ProcessInstance processInstance = processKsession.createProcessInstance( "  " a string "  " , params);        System.out.println( "  " a string "  " +processInstance.getId());    eventsKsession.setGlobal( "  " a string "  " , taskSpeed);    processKsession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new WorkItemHandler() {            System.out.println( "  " a string "  "  + wi.getName() +  "  " a string "  "  + wi.getId());        ProcessInstance processInstance = processKsession.createProcessInstance( "  " a string "  " , params);        System.out.println( "  " a string "  " +processInstance.getId());}    eventsKsession.setGlobal( "  "  " a string "  "  " , taskSpeed);    processKsession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , new WorkItemHandler() {            System.out.println( "  "  " a string "  "  "  + wi.getName() +  "  "  " a string "  "  "  + wi.getId());        ProcessInstance processInstance = processKsession.createProcessInstance( "  "  " a string "  "  " , params);        System.out.println( "  "  " a string "  "  " +processInstance.getId());
