{

    String topicName;
    MqttAsyncClient 	client;
    MqttConnectOptions 	conOpt;
    String 				brokerUrl;
    byte[] payload;
    int qos;
    log("a string"+brokerUrl + "a string"+client.getClientId());
    IMqttToken conToken = client.connect(conOpt,null,null);
    conToken.waitForCompletion();
    log("a string");

    log("a string"+System.currentTimeMillis()+ "a string"+topicName+"a string"+qos);

    MqttMessage message = new MqttMessage(payload);
    message.setQos(qos);

    IMqttDeliveryToken pubToken = client.publish(topicName, message, null, null);
    pubToken.waitForCompletion();
    log("a string");

    log("a string");
    IMqttToken discToken = client.disconnect(null, null);
    discToken.waitForCompletion();
    log("a string");
}