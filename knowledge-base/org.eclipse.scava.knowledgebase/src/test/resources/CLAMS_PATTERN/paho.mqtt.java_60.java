{
    String[] topicNames;
    final Class<?> cclass;
    MqttClientFactoryPaho clientFactory;
    int[] topicQos;
    String serverHost;
    final Logger log;
    IMqttClient mqttClient;
    URI serverURI = new URI("a string" + serverHost + "a string" + TestProperties.getServerSSLPort());
    String methodName = Utility.getMethodName();
    LoggingUtilities.banner(log, cclass, methodName);
    try {
        mqttClient = clientFactory.createMqttClient(serverURI, methodName);
        MqttV3Receiver mqttV3Receiver = new MqttV3Receiver(mqttClient, LoggingUtilities.getPrintStream());
        mqttClient.setCallback(mqttV3Receiver);
        System.setProperty("a string", TestProperties.getClientKeyStore());
        System.setProperty("a string", TestProperties.getClientKeyStorePassword());
        System.setProperty("a string", TestProperties.getClientTrustStore());
        mqttClient.connect();
        mqttClient.subscribe(topicNames, topicQos);
        byte[] payload = ("a string" + getClass().getName() + "a string" + methodName).getBytes();
        MqttTopic mqttTopic = mqttClient.getTopic(topicNames[0]);
        mqttTopic.publish(payload, 0, boolean);
        boolean ok = mqttV3Receiver.validateReceipt(topicNames[0], 0, payload);
        // Do something with ok
    } catch (Exception exception) {
        // Do something
    } finally {
        try {
            if ((mqttClient != null) && mqttClient.isConnected()) {
                mqttClient.disconnect();
            }
            if (mqttClient != null) {
                mqttClient.close();
            }
        } catch (Exception exception) {
            // Do something
        }
    }
}