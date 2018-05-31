{
    final StatefulKnowledgeSession processKsession;
    final TaskSpeed taskSpeed;
    final StatefulKnowledgeSession eventsKsession;
    eventsKsession.setGlobal( " a string " , taskSpeed);

    processKsession.getWorkItemManager().registerWorkItemHandler( " a string " , new WorkItemHandler() {
        // Do something
    });
    ((StatefulKnowledgeSessionImpl) processKsession).addEventListener(new DefaultProcessEventListener() {
        // Do something
    });
}    eventsKsession.setGlobal( "  " a string "  " , taskSpeed);    processKsession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new WorkItemHandler() {    eventsKsession.setGlobal( "  " a string "  " , taskSpeed);    processKsession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , new WorkItemHandler() {}    eventsKsession.setGlobal( "  "  " a string "  "  " , taskSpeed);    processKsession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , new WorkItemHandler() {
