{
    ActivationCreatedEvent event;
    ((StatefulKnowledgeSession) event.getKnowledgeRuntime()).fireAllRules();
}
