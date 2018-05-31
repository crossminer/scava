{
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

    if (kbuilder.hasErrors()) {
        throw new RuntimeException(kbuilder.getErrors().toString());
    }

    StatefulKnowledgeSession ksession = kbuilder.newKnowledgeBase().newStatefulKnowledgeSession();

    ksession.getAgendaEventListeners();
    ksession.getProcessEventListeners();
    ksession.getWorkingMemoryEventListeners();

    ksession.dispose();
}
