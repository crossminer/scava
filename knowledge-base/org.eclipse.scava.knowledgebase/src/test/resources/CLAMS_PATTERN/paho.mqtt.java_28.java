{
    MqttAsyncClient 	client;
    String 				brokerUrl;
    MqttConnectOptions 	conOpt;
    log("a string"+brokerUrl + "a string"+client.getClientId());

    IMqttActionListener conListener = new IMqttActionListener() {
        public void onSuccess(IMqttToken asyncActionToken) {
            log("a string");
        }
        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
            log ("a string" +exception);
        }
    };

    try {
        client.connect(conOpt,"a string", conListener);
    } catch (MqttException e) {
        // Do something
    }
}