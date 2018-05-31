{
    StatefulKnowledgeSession ksession;
    DataStore dataStore;
    ProcessInstance processInstance = ksession.startProcess( " a string " );
    Definitions def = (Definitions) processInstance.getProcess()
    .getMetaData().get( " a string " );
    assertEquals(String.class.getCanonicalName(),
    ((ObjectDataType) dataStore.getType()).getClassName());
    // Do something with def

}    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " );    .getMetaData().get( "  " a string "  " );    ProcessInstance processInstance = ksession.startProcess( "  " a string "  " );    .getMetaData().get( "  " a string "  " );}    ProcessInstance processInstance = ksession.startProcess( "  "  " a string "  "  " );    .getMetaData().get( "  "  " a string "  "  " );
