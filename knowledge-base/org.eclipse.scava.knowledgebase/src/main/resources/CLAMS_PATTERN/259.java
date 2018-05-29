{

    GenericCommand<Void> setProcInstVariablesCommand;
    ((CommandBasedStatefulKnowledgeSession) getSession())
    .getCommandService()
    .execute(setProcInstVariablesCommand);
}
