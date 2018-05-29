{
    TestWorkItemHandler handler;
    Reader source;
    List<String> list;
    WorkItem workItem;
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    kbuilder.add(new ReaderResource(source), ResourceType.DRF);
    KnowledgeBase kbase = kbuilder.newKnowledgeBase();
    StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " , handler);
    ksession.setGlobal( " a string " , list);
    ProcessInstance processInstance =
    ksession.startProcess( " a string " );
    assertEquals(ProcessInstance.STATE_ACTIVE, processInstance.getState());
    ksession.getWorkItemManager().completeWorkItem(workItem.getId(), null);
    assertEquals(ProcessInstance.STATE_COMPLETED, processInstance.getState());
}    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , handler);    ksession.setGlobal( "  " a string "  " , list);    ksession.startProcess( "  " a string "  " );    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , handler);    ksession.setGlobal( "  " a string "  " , list);    ksession.startProcess( "  " a string "  " );}    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , handler);    ksession.setGlobal( "  "  " a string "  "  " , list);    ksession.startProcess( "  "  " a string "  "  " );
