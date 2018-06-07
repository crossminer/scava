{
    String time;
    MqttMessage message;
    String topic;
    System.out.println("a string" +time +
    "a string" + topic +
    "a string" + new String(message.getPayload()) +
    "a string" + message.getQos());
}