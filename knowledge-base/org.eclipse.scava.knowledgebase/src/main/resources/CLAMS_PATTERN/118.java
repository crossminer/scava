{

    String drlFile;
    final List firedRules;
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    kbuilder.add(new ClassPathResource(drlFile, getClass()), ResourceType.DRL);
    KnowledgeBuilderErrors errors = kbuilder.getErrors();
    if (errors.size() > number) {
        // Do something
    }
    KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();

    kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
    StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
    KnowledgeRuntimeLoggerFactory.newConsoleLogger(ksession);

    if (firedRules != null) {
        ksession.addEventListener(new DefaultAgendaEventListener() {
            @Override
            public void afterActivationFired(AfterActivationFiredEvent event) {
                firedRules.add(event.getActivation().getRule().getName());
            }
        });
    }
}
