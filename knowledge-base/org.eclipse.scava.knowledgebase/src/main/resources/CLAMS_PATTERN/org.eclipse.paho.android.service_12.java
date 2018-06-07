{
    String clientHandle;
    MqttConnectOptions connectOptions;
    String activityToken;
    String invocationContext;
    MqttConnection client = getConnection(clientHandle);
    client.connect(connectOptions, invocationContext, activityToken);

}