{
    byte[] payload;
    boolean retained;
    String topic;
    int qos;
    MqttMessage message = new MqttMessage(payload);
    message.setQos(qos);
    message.setRetained(retained);
    this.publish(topic, message);
}