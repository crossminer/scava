{
    MqttConnectOptions options;
    MqttAsyncClient aClient;
    IMqttToken tok = aClient.connect(options, null, null);
    tok.waitForCompletion(getTimeToWait());
}