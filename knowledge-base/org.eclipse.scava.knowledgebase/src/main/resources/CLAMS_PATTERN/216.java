{
    HashMap<String, Object> context;
    EntityManagerFactory emfDomain;
    Environment domainEnv = EnvironmentFactory.newEnvironment();
    domainEnv.set(EnvironmentName.ENTITY_MANAGER_FACTORY, emfDomain);
    Environment env = PersistenceUtil.createEnvironment(context);
    env.set(EnvironmentName.OBJECT_MARSHALLING_STRATEGIES, new ObjectMarshallingStrategy[]{
        new JPAPlaceholderResolverStrategy(domainEnv),
        new SerializablePlaceholderResolverStrategy(ClassObjectMarshallingStrategyAcceptor.DEFAULT)
    });
}
