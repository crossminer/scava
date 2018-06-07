{
    MqttService mqttService;
    String clientHandle;
    IMqttToken token = new MqttTokenAndroid(this, null,
    (IMqttActionListener) null);
    String activityToken = storeToken(token);
    mqttService.disconnect(clientHandle, null, activityToken);
}