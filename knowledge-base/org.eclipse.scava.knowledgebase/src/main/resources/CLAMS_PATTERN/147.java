{
    StatefulKnowledgeSession ksession;
    KnowledgeBase kbase2;
    KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
    kbase.addKnowledgePackages(kbase2.getKnowledgePackages());
    ksession.addEventListener(new DefaultProcessEventListener() {
        // Do something
    });
    ksession.signalEvent( " a string " ,  " a string " );
}    ksession.signalEvent( "  " a string "  " ,  "  " a string "  " );    ksession.signalEvent( "  " a string "  " ,  "  " a string "  " );}    ksession.signalEvent( "  "  " a string "  "  " ,  "  "  " a string "  "  " );
