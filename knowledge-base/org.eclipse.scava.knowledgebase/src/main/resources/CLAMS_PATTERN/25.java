{
    Environment bamEnv;
    EntityManagerFactory jbpmBamEFactory;
    bamEnv = EnvironmentFactory.newEnvironment();
    bamEnv.set(EnvironmentName.ENTITY_MANAGER_FACTORY, jbpmBamEFactory);
}
