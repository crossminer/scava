{
    TaskService taskService;
    Environment env;
    EntityManagerFactory emf;
    EntityManagerFactory emfTask;
    env = KnowledgeBaseFactory.newEnvironment();
    env.set(EnvironmentName.ENTITY_MANAGER_FACTORY, emf);
    env.set(EnvironmentName.TRANSACTION_MANAGER,
    TransactionManagerServices.getTransactionManager());

    taskService = new TaskService(emfTask,
    SystemEventListenerFactory.getSystemEventListener());
    TaskClient taskClient = new TaskClient(new HornetQTaskClientConnector( " a string " ,

    new HornetQTaskClientHandler(SystemEventListenerFactory
    .getSystemEventListener())));
    // Do something with taskService

    // Do something with taskClient
}    TaskClient taskClient = new TaskClient(new HornetQTaskClientConnector( "  " a string "  " ,    TaskClient taskClient = new TaskClient(new HornetQTaskClientConnector( "  " a string "  " ,}    TaskClient taskClient = new TaskClient(new HornetQTaskClientConnector( "  "  " a string "  "  " ,
