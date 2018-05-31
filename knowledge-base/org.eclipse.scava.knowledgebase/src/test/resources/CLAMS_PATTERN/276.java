{
    Threshold avgThreshold;
    StatefulKnowledgeSession ksession;
    ViewChangedEventListener listener;
    FactHandle thresholdFactHandle;
    ksession.insert(new Average(number));
    thresholdFactHandle = ksession.insert(avgThreshold);
    ksession.insert(new User( " a string " ));

    LiveQuery query = ksession.openLiveQuery( " a string " ,
    new Object[]{
        // Do something
    },
    listener);
    // Do something with thresholdFactHandle

    // Do something with query


}    ksession.insert(new User( "  " a string "  " ));    LiveQuery query = ksession.openLiveQuery( "  " a string "  " ,    ksession.insert(new User( "  " a string "  " ));    LiveQuery query = ksession.openLiveQuery( "  " a string "  " ,}    ksession.insert(new User( "  "  " a string "  "  " ));    LiveQuery query = ksession.openLiveQuery( "  "  " a string "  "  " ,
