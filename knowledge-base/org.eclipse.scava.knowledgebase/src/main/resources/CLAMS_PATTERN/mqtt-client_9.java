{
    MQTT mqtt = new MQTT();
    mqtt.setConnectAttemptsMax(0);
    mqtt.setReconnectAttemptsMax(0);
    mqtt.setHost("a string", 0);

    final BlockingConnection publishConnection = mqtt.blockingConnection();
    publishConnection.connect();

    final BlockingConnection subscribeConnection = mqtt.blockingConnection();
    subscribeConnection.connect();
    subscribeConnection.setReceiveBuffer(0*0);

    Topic[] topic = {new Topic(utf8("a string"), QoS.EXACTLY_ONCE)};
    byte[] qoses = subscribeConnection.subscribe(topic);

    publishConnection.disconnect();
    subscribeConnection.disconnect();
    // Do something with qoses
}