{

    String mqttServerURI;
    String[] topicNames;
    byte[] message;
    String methodName;
    IMqttAsyncClient mqttClient;
    try {
        mqttClient = new MqttAndroidClient(mContext, mqttServerURI, methodName);
        MqttV3Receiver mqttV3Receiver = new MqttV3Receiver(mqttClient, null);
        boolean ok = mqttV3Receiver.validateReceipt(topicNames[0], 0, message);
        // Do something with ok
    } catch (Exception exception) {
        // Do something
    } finally {
        // Do something
    }

}