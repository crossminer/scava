{
    Properties properties;
    KnowledgeBase kbase;
    SessionConfiguration config = new SessionConfiguration(properties);

    final org.drools.runtime.Environment environment = EnvironmentFactory.newEnvironment();
    environment.set(EnvironmentName.OBJECT_MARSHALLING_STRATEGIES, new ObjectMarshallingStrategy[]{
        getPlaceholderResolverStrategy(environment),new SerializablePlaceholderResolverStrategy(ClassObjectMarshallingStrategyAcceptor.DEFAULT)
    });
    return kbase.newStatefulKnowledgeSession(config, environment);
}
