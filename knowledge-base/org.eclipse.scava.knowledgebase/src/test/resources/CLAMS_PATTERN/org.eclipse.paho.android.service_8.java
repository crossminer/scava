{

    TestCaseNotifier notifier;
    MqttConnectOptions options;
    String keyStorePwd;
    IMqttToken connectToken;
    IMqttToken disconnectToken;
    String mqttSSLServerURI;
    MqttAndroidClient mqttClient;
    try {
        mqttClient = new MqttAndroidClient(mContext, mqttSSLServerURI, "a string");
        options.setSocketFactory(mqttClient.getSSLSocketFactory(this.getContext().getResources().openRawResource(R.raw.test),keyStorePwd));
        connectToken = mqttClient.connect(options, this.getContext(), new ActionListener(notifier));
        disconnectToken = mqttClient.disconnect(null, new ActionListener(notifier));
        connectToken = mqttClient.connect(options, this.getContext(), new ActionListener(notifier));
        disconnectToken = mqttClient.disconnect(null, new ActionListener(notifier));
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