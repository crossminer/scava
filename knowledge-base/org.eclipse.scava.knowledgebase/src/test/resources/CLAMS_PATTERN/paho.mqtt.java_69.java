{
    MqttConnectOptions options;
    MqttToken userToken;
    IMqttActionListener userCallback;
    boolean reconnect;
    IMqttToken token;
    int originalMqttVersion;
    ClientComms comms;
    Object userContext;
    MqttCallbackExtended mqttCallbackExtended;
    if (originalMqttVersion == MqttConnectOptions.MQTT_VERSION_DEFAULT) {
        options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_DEFAULT);
    }
    userToken.internalTok.markComplete(token.getResponse(), null);
    comms.notifyConnect();

    if (userCallback != null) {
        userToken.setUserContext(userContext);
        userCallback.onSuccess(userToken);
    }

    if(mqttCallbackExtended != null) {
        String serverURI = comms.getNetworkModules()[comms.getNetworkModuleIndex()].getServerURI();
        mqttCallbackExtended.connectComplete(reconnect, serverURI);
    }


}