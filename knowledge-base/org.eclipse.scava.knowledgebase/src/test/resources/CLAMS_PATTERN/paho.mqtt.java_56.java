{
    String clientId;
    String url;
    String pubTopic;
    MqttClient pubClinet;
    String payload;
    int qos;
    pubClinet = new MqttClient(url, clientId);
    pubClinet.setCallback(this);
    pubClinet.connect();

    MqttMessage message = new MqttMessage(payload.getBytes());
    message.setQos(qos);

    pubClinet.publish(pubTopic, message);

    pubClinet.disconnect();
}