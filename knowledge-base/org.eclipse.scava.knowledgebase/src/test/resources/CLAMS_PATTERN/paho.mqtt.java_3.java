{
    final Class<?> cclass;
    MqttClientFactoryPaho clientFactory;
    final Logger log;
    String methodName = Utility.getMethodName();
    LoggingUtilities.banner(log, cclass, methodName);

    try {
        if (clientFactory != null) {
            clientFactory.close();
            clientFactory.disconnect();
        }
    } catch (Exception exception) {
        // Do something
    }
}