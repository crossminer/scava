{
    TestWorkItemHandler workItemHandler;
    StatefulKnowledgeSession ksession;
    WorkItem workItem;
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    kbuilder.add(ResourceFactory.newClassPathResource( " a string " ), ResourceType.BPMN2);
    kbuilder.add(ResourceFactory.newClassPathResource( " a string " ), ResourceType.DRL);
    KnowledgeBase kbase = kbuilder.newKnowledgeBase();
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " , workItemHandler);
    ProcessInstance processInstance = ksession.startProcess( " a string " );
    assertTrue(processInstance.getState() == ProcessInstance.STATE_ACTIVE);
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " , workItemHandler);
    ksession.fireAllRules();
    ksession.signalEvent( " a string " , null, processInstance.getId());
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " , workItemHandler);
    ksession.getWorkItemManager().completeWorkItem(workItem.getId(), null);
    // Do something with kbase

}    kbuilder.add(ResourceFactory.newClassPathResource( "  " a string "  " ), ResourceType.BPMN2);    kbuilder.add(ResourceFactory.newClassPathResource( "  " a string "  " ), ResourceType.DRL);    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , workItemHandler);    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " );    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , workItemHandler);    ksession.signalEvent( "  " a string "  " , null, processInstance.getId());    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , workItemHandler);    kbuilder.add(ResourceFactory.newClassPathResource( "  " a string "  " ), ResourceType.BPMN2);    kbuilder.add(ResourceFactory.newClassPathResource( "  " a string "  " ), ResourceType.DRL);    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , workItemHandler);    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " );    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , workItemHandler);    ksession.signalEvent( "  " a string "  " , null, processInstance.getId());    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , workItemHandler);}    kbuilder.add(ResourceFactory.newClassPathResource( "  "  " a string "  "  " ), ResourceType.BPMN2);    kbuilder.add(ResourceFactory.newClassPathResource( "  "  " a string "  "  " ), ResourceType.DRL);    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , workItemHandler);    ProcessInstance processInstance = ksession.startProcess( "  "  " a string "  "  " );    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , workItemHandler);    ksession.signalEvent( "  "  " a string "  "  " , null, processInstance.getId());    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , workItemHandler);
