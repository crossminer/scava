{
    BundleContext context;
    ConnectionManager connectionManager;
    super.start(context);
    connectionManager = new ConnectionManager(context, new EventService(context));
    connectionManager.start();
}