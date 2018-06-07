{

    MqttConnectOptions options;
    String keyStorePwd;
    IMqttToken connectToken;
    IMqttToken disconnectToken;
    String mqttSSLServerURI;
    MqttAndroidClient mqttClient;
    try {
        mqttClient = new MqttAndroidClient(mContext, mqttSSLServerURI,
        "a string");
        options.setSocketFactory(mqttClient.getSSLSocketFactory(this
        .getContext().getResources().openRawResource(R.raw.test),
        keyStorePwd));
        connectToken = mqttClient.connect( options);
        disconnectToken = mqttClient.disconnect(null, null);
        connectToken = mqttClient.connect(options);
        disconnectToken = mqttClient.disconnect(null, null);
        // Do something with connectToken
        // Do something with disconnectToken
    } catch (Exception exception) {
        // Do something
    } finally {
        if (mqttClient != null) {
            mqttClient.close();
        }
    }

}