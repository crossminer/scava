{
    Reader source;
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    kbuilder.add( ResourceFactory.newReaderResource( source ), ResourceType.DRF );
    KnowledgeBase kbase = kbuilder.newKnowledgeBase();
    StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
    WorkflowProcessInstance processInstance = (WorkflowProcessInstance)
    ksession.startProcess( " a string " );
    assertEquals(ProcessInstance.STATE_ACTIVE, processInstance.getState());
    Collection<NodeInstance> nodeInstances = processInstance.getNodeInstances();
    processInstance.signalEvent( " a string " ,  " a string " );
    nodeInstances = processInstance.getNodeInstances();
    processInstance.signalEvent( " a string " ,  " a string " );
    nodeInstances = processInstance.getNodeInstances();
    processInstance.signalEvent( " a string " ,  " a string " );
    nodeInstances = processInstance.getNodeInstances();
    processInstance.signalEvent( " a string " ,  " a string " );
    nodeInstances = processInstance.getNodeInstances();
    processInstance.signalEvent( " a string " ,  " a string " );
    nodeInstances = processInstance.getNodeInstances();
    assertEquals(ProcessInstance.STATE_COMPLETED, processInstance.getState());
    // Do something with nodeInstances

}    ksession.startProcess( "  " a string "  " );    processInstance.signalEvent( "  " a string "  " ,  "  " a string "  " );    processInstance.signalEvent( "  " a string "  " ,  "  " a string "  " );    processInstance.signalEvent( "  " a string "  " ,  "  " a string "  " );    processInstance.signalEvent( "  " a string "  " ,  "  " a string "  " );    processInstance.signalEvent( "  " a string "  " ,  "  " a string "  " );    ksession.startProcess( "  " a string "  " );    processInstance.signalEvent( "  " a string "  " ,  "  " a string "  " );    processInstance.signalEvent( "  " a string "  " ,  "  " a string "  " );    processInstance.signalEvent( "  " a string "  " ,  "  " a string "  " );    processInstance.signalEvent( "  " a string "  " ,  "  " a string "  " );    processInstance.signalEvent( "  " a string "  " ,  "  " a string "  " );}    ksession.startProcess( "  "  " a string "  "  " );    processInstance.signalEvent( "  "  " a string "  "  " ,  "  "  " a string "  "  " );    processInstance.signalEvent( "  "  " a string "  "  " ,  "  "  " a string "  "  " );    processInstance.signalEvent( "  "  " a string "  "  " ,  "  "  " a string "  "  " );    processInstance.signalEvent( "  "  " a string "  "  " ,  "  "  " a string "  "  " );    processInstance.signalEvent( "  "  " a string "  "  " ,  "  "  " a string "  "  " );
