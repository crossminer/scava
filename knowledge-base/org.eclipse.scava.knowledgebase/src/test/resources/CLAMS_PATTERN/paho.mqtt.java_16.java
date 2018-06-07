{
    String mytopic;
    final Class<?> cclass;
    MqttClientFactoryPaho clientFactory;
    URI serverURI;
    final Logger log;
    final String methodName = Utility.getMethodName();
    LoggingUtilities.banner(log, cclass, methodName);
    listener mylistener = new listener();
    IMqttAsyncClient mqttClient = clientFactory.createMqttAsyncClient(serverURI, methodName);
    IMqttToken token = mqttClient.connect(null, null);
    token.waitForCompletion();

    token = mqttClient.subscribe(mytopic, 0, mylistener);
    token.waitForCompletion();

    MqttMessage message = new MqttMessage();
    message.setPayload("a string".getBytes());
    token = mqttClient.publish(mytopic, message);
    token.waitForCompletion();

    MqttMessage msg = mylistener.getNextMessage();
    Assert.assertEquals("a string", msg.toString());

    token = mqttClient.disconnect();
    token.waitForCompletion();

    mqttClient.close();

}