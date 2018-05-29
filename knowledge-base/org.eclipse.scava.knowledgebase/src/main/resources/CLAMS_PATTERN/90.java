{
    Object ksession;
    final org.drools.event.AgendaEventListener agendaEventListener;
    ((StatefulKnowledgeSessionImpl)  ((KnowledgeCommandContext) ((CommandBasedStatefulKnowledgeSession) ksession)
    .getCommandService().getContext()).getStatefulKnowledgesession() )
    .session.addEventListener(agendaEventListener);
}
