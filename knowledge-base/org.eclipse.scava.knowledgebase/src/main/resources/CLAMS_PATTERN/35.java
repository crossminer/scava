{
    FireTruck fireTruck;
    StatefulKnowledgeSession ksession;
    Emergency emergency;
    Map<String, Object> parameters;
    ksession.startProcess( " a string " ,parameters);

    ksession.signalEvent(
         " a string " ,
        new VehicleHitsEmergencyEvent(emergency.getId(),
    fireTruck.getId(),
    new Date())
    );
    ksession.signalEvent(
         " a string " ,
        new EmergencyEndsEvent(emergency.getId(),
    new Date())
    );
}    ksession.startProcess( "  " a string "  " ,parameters);         "  " a string "  " ,         "  " a string "  " ,    ksession.startProcess( "  " a string "  " ,parameters);         "  " a string "  " ,         "  " a string "  " ,}    ksession.startProcess( "  "  " a string "  "  " ,parameters);         "  "  " a string "  "  " ,         "  "  " a string "  "  " ,
