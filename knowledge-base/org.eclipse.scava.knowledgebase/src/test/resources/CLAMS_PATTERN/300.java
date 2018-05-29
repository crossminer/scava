{
    List<Command<?>> cmds;
    Collection<?> facts;
    KnowledgeBase kbase;
    try {
        StatelessKnowledgeSession ksession = kbase.newStatelessKnowledgeSession();

        cmds.add(CommandFactory.newInsertElements(facts));

        Command<?> batchCmd = CommandFactory.newBatchExecution(cmds);

        ksession.execute(batchCmd);
    } catch(Exception ex) {
        // Do something
    }
}
