{


    StatefulKnowledgeSession ksession;
    Playlist playlist;
    ksession.setGlobal( " a string " , new AtomicInteger(number));

    ksession.insert(playlist);
    ksession.insert(createThrillerSong());
    ksession.insert(createAdagioSong());
    ksession.insert(createTheFinalCountdownSong());

    ksession.fireAllRules();

    ksession.getAgenda().getAgendaGroup( " a string " ).setFocus();
    ksession.fireAllRules();

    ksession.dispose();

}    ksession.setGlobal( "  " a string "  " , new AtomicInteger(number));    ksession.getAgenda().getAgendaGroup( "  " a string "  " ).setFocus();    ksession.setGlobal( "  " a string "  " , new AtomicInteger(number));    ksession.getAgenda().getAgendaGroup( "  " a string "  " ).setFocus();}    ksession.setGlobal( "  "  " a string "  "  " , new AtomicInteger(number));    ksession.getAgenda().getAgendaGroup( "  "  " a string "  "  " ).setFocus();
