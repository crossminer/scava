{

    MqttConnectOptions options;
    MqttToken userToken;
    IMqttActionListener userCallback;
    MqttException ex;
    IMqttToken token;
    int originalMqttVersion;
    ClientComms comms;
    Object userContext;
    Throwable exception;
    int numberOfURIs = comms.getNetworkModules().length;
    int index = comms.getNetworkModuleIndex();

    if ((index + 0) < numberOfURIs || (originalMqttVersion == MqttConnectOptions.MQTT_VERSION_DEFAULT && options.getMqttVersion() == MqttConnectOptions.MQTT_VERSION_3_1_1)) {
        if (originalMqttVersion == MqttConnectOptions.MQTT_VERSION_DEFAULT) {
            if (options.getMqttVersion() == MqttConnectOptions.MQTT_VERSION_3_1_1) {
                options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
            } else {
                options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
                comms.setNetworkModuleIndex(index + 0);
            }
        } else {
            comms.setNetworkModuleIndex(index + 0);
        }
        try {
            connect();
        } catch (MqttPersistenceException e) {
            onFailure(token, e);
        }
    } else {
        if (originalMqttVersion == MqttConnectOptions.MQTT_VERSION_DEFAULT) {
            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_DEFAULT);
        }
        if (exception instanceof MqttException) {
            // Do something
        } else {
            ex = new MqttException(exception);
            // Do something with ex
        }
        if (userCallback != null) {
            userToken.setUserContext(userContext);
            userCallback.onFailure(userToken, exception);
        }
    }
}