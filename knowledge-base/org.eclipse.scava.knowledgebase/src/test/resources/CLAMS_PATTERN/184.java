{
    TestWorkItemHandler handler;
    Reader source;
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    kbuilder.add(ResourceFactory.newReaderResource(source), ResourceType.DRF);
    KnowledgeBase kbase = kbuilder.newKnowledgeBase();
    StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
    KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession,  " a string " );
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " , handler);
    ProcessInstance processInstance = (ProcessInstance) ksession.startProcess( " a string " );
    DynamicNodeInstance dynamicContext = (DynamicNodeInstance)
    ((WorkflowProcessInstance) processInstance).getNodeInstances().iterator().next();
    assertEquals(number, dynamicContext.getNodeInstances().size());
    assertEquals(number, dynamicContext.getNodeInstances().size());
    logger.close();
}    KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession,  "  " a string "  " );    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , handler);    ProcessInstance processInstance = (ProcessInstance) ksession.startProcess( "  " a string "  " );    KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession,  "  " a string "  " );    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , handler);    ProcessInstance processInstance = (ProcessInstance) ksession.startProcess( "  " a string "  " );}    KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession,  "  "  " a string "  "  " );    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , handler);    ProcessInstance processInstance = (ProcessInstance) ksession.startProcess( "  "  " a string "  "  " );
