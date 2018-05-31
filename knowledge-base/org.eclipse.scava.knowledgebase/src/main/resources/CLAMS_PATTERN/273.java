{
    Environment _environment;
    KnowledgeSessionConfiguration _ksessionConfig;
    Integer sessionId;
    final Logger LOGGER;
    StatefulKnowledgeSession _ksession;
    KnowledgeBase _kbase;
    boolean _persistent;
    try {
        if (_ksession != null && sessionId != null) {
            if (_ksession.getId() != sessionId.intValue()) {
                LOGGER.info( " a string "  + _ksession.getId() +  " a string "  + sessionId +  " a string " );
            }
        }
        if (_ksession == null) {
            if (_persistent) {
                if (sessionId != null) {
                    _ksession = JPAKnowledgeService.loadStatefulKnowledgeSession(sessionId.intValue(), _kbase, _ksessionConfig, _environment);
                } else {
                    _ksession = JPAKnowledgeService.newStatefulKnowledgeSession(_kbase, _ksessionConfig, _environment);
                }
            } else {
                _ksession = _kbase.newStatefulKnowledgeSession(_ksessionConfig, _environment);
            }
            _ksession.addEventListener(new DefaultAgendaEventListener() {
                // Do something
            });
        }
    } finally {
        // Do something
    }
}                LOGGER.info( "  " a string "  "  + _ksession.getId() +  "  " a string "  "  + sessionId +  "  " a string "  " );                LOGGER.info( "  " a string "  "  + _ksession.getId() +  "  " a string "  "  + sessionId +  "  " a string "  " );}                LOGGER.info( "  "  " a string "  "  "  + _ksession.getId() +  "  "  " a string "  "  "  + sessionId +  "  "  " a string "  "  " );
