{

    final List<Long> list2;
    final List<Long> list;
    EntityManagerFactory emf;
    KnowledgeBase kbase;
    Environment env = EnvironmentFactory.newEnvironment();
    env.set(EnvironmentName.ENTITY_MANAGER_FACTORY, emf);

    StatefulKnowledgeSession ksession = JPAKnowledgeService.newStatefulKnowledgeSession(kbase, null, env);

    int sessionId = ksession.getId();

    ksession.addEventListener(new DefaultProcessEventListener() {
        public void afterProcessStarted(ProcessStartedEvent event) {
            list.add(event.getProcessInstance().getId());
        }
    });

    ((StatefulKnowledgeSessionImpl)  ((KnowledgeCommandContext) ((CommandBasedStatefulKnowledgeSession) ksession)
                                      .getCommandService().getContext()).getStatefulKnowledgesession() )
    .session.addEventListener(new TriggerRulesEventListener(ksession));

    ksession.fireAllRules();

    ksession.dispose();

    ksession = JPAKnowledgeService.loadStatefulKnowledgeSession(sessionId, kbase, null, env);
    ksession.addEventListener(new DefaultProcessEventListener() {
        public void beforeProcessStarted(ProcessStartedEvent event) {
            list2.add(event.getProcessInstance().getId());
        }
    });

    ((StatefulKnowledgeSessionImpl)  ((KnowledgeCommandContext) ((CommandBasedStatefulKnowledgeSession) ksession)
                                      .getCommandService().getContext()).getStatefulKnowledgesession() )
    .session.addEventListener(new TriggerRulesEventListener(ksession));

    ksession.fireAllRules();

    ksession.dispose();
}
