{

    final List<String> firedRules;
    SessionPseudoClock clock;
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    kbuilder.add(new ClassPathResource( " a string " ), ResourceType.DRL);

    KnowledgeBuilderErrors errors = kbuilder.getErrors();
    if (errors.size() > number) {
        // Do something
    }

    KnowledgeBaseConfiguration kbaseConfig = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
    kbaseConfig.setOption(EventProcessingOption.STREAM);

    KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase(kbaseConfig);
    kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());


    KnowledgeSessionConfiguration ksessionConfig = KnowledgeBaseFactory.newKnowledgeSessionConfiguration();
    ksessionConfig.setOption(ClockTypeOption.get( " a string " ));

    StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession(ksessionConfig, null);
    KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newConsoleLogger(ksession);

    ksession.addEventListener(new DefaultAgendaEventListener() {
        @Override
        public void afterActivationFired(AfterActivationFiredEvent event) {
            firedRules.add(event.getActivation().getRule().getName());
        }
    });

    clock = ksession.getSessionClock();
    // Do something with logger

    // Do something with clock
}    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.DRL);    ksessionConfig.setOption(ClockTypeOption.get( "  " a string "  " ));    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.DRL);    ksessionConfig.setOption(ClockTypeOption.get( "  " a string "  " ));}    kbuilder.add(new ClassPathResource( "  "  " a string "  "  " ), ResourceType.DRL);    ksessionConfig.setOption(ClockTypeOption.get( "  "  " a string "  "  " ));
