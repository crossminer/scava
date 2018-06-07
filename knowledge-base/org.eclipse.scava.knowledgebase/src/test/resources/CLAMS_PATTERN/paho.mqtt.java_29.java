{
    final Class<?> cclass;
    ConnectionManipulationProxyServer proxy;
    String  clientId;
    final Logger log;
    final MemoryPersistence DATA_STORE;
    String methodName = Utility.getMethodName();
    LoggingUtilities.banner(log, cclass, methodName);
    MqttConnectOptions options = new MqttConnectOptions();
    options.setCleanSession(boolean);
    options.setAutomaticReconnect(boolean);
    final MqttClient client = new MqttClient("a string" + proxy.getLocalPort(), clientId, DATA_STORE);

    proxy.enableProxy();
    client.connect(options);

    boolean isConnected = client.isConnected();
    log.info("a string" + isConnected);
    Assert.assertTrue(isConnected);

    proxy.disableProxy();
    isConnected = client.isConnected();
    log.info("a string" + isConnected);
    Assert.assertFalse(isConnected);

    proxy.enableProxy();
    while(client.isConnected() ==  boolean) {
        // Do something
    }
    isConnected = client.isConnected();
    log.info("a string" + isConnected);
    Assert.assertTrue(isConnected);
    client.disconnect();
    Assert.assertFalse(client.isConnected());
}