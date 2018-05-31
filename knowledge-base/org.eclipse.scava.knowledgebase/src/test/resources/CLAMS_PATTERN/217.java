{
    ProcessStartedEvent event;
    Logger log;
    StatefulKnowledgeSession ksession = (StatefulKnowledgeSession)event.getKnowledgeRuntime();
    ProcessInstance pInstance = event.getProcessInstance();
    log.info( " a string " +ksession.getId()+ " a string " +pInstance+ " a string " );
}    log.info( "  " a string "  " +ksession.getId()+ "  " a string "  " +pInstance+ "  " a string "  " );    log.info( "  " a string "  " +ksession.getId()+ "  " a string "  " +pInstance+ "  " a string "  " );}    log.info( "  "  " a string "  "  " +ksession.getId()+ "  "  " a string "  "  " +pInstance+ "  "  " a string "  "  " );
