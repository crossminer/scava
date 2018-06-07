{

    final Class<?> cclass;
    MqttClientFactoryPaho clientFactory;
    URI serverURI;
    final Logger log;
    try {
        String methodName = Utility.getMethodName();
        LoggingUtilities.banner(log, cclass, methodName);
        serverURI = TestProperties.getServerURI();
        clientFactory = new MqttClientFactoryPaho();
        clientFactory.open();
        // Do something with serverURI
    } catch (Exception exception) {
        // Do something
    }
}