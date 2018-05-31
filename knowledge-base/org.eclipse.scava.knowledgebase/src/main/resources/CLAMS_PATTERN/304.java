{
    Logger log;
    IKnowledgeSessionPool sessionPool;
    ProcessCompletedEvent event;
    StatefulKnowledgeSession ksession = (StatefulKnowledgeSession)event.getKnowledgeRuntime();
    ProcessInstance pInstance = event.getProcessInstance();
    if(sessionPool.isBorrowed(ksession.getId(), pInstance.getProcessId())) {
        log.info( " a string " +ksession.getId()+ " a string " +pInstance+ " a string " );
        sessionPool.markAsReturned(ksession.getId());
    } else {
        log.info( " a string " +ksession.getId()+ " a string " +pInstance);
    }
}        log.info( "  " a string "  " +ksession.getId()+ "  " a string "  " +pInstance+ "  " a string "  " );        log.info( "  " a string "  " +ksession.getId()+ "  " a string "  " +pInstance);        log.info( "  " a string "  " +ksession.getId()+ "  " a string "  " +pInstance+ "  " a string "  " );        log.info( "  " a string "  " +ksession.getId()+ "  " a string "  " +pInstance);}        log.info( "  "  " a string "  "  " +ksession.getId()+ "  "  " a string "  "  " +pInstance+ "  "  " a string "  "  " );        log.info( "  "  " a string "  "  " +ksession.getId()+ "  "  " a string "  "  " +pInstance);
