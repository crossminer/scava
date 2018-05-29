{
    Properties properties;
    KnowledgeBase kbase;
    KnowledgeSessionConfiguration config = KnowledgeBaseFactory.newKnowledgeSessionConfiguration(properties);
    return kbase.newStatefulKnowledgeSession(config, EnvironmentFactory.newEnvironment());
}
