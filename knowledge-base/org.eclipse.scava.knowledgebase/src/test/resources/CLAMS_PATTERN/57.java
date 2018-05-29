{

    StatefulKnowledgeSession ksession;
    Playlist playlist;
    ksession.insert(playlist);
    ksession.fireAllRules();

    ksession.insert(createPlaylistWithOneSong());
    ksession.fireAllRules();
    ksession.dispose();
}
