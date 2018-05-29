{
    StatefulKnowledgeSession ksession;
    assertEquals(number, ksession.getProcessInstances().size());
    ksession.dispose();
}
