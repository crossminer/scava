{
    MqttService mqttService;
    String clientHandle;
    Context myContext;
    String clientId;
    String serverURI;
    MqttClientPersistence persistence;
    if (clientHandle == null) {
        clientHandle = mqttService.getClient(serverURI, clientId, myContext.getApplicationInfo().packageName,persistence);
    }
    mqttService.close(clientHandle);
}