{

    IMqttClient client;
    String clientId;
    String serverURI;
    try {
        client = new MqttClient(serverURI, clientId);
        client.connect();
        String clientId2 = client.getClientId();
        boolean isConnected = client.isConnected();
        System.out.println("a string" + isConnected);
        String id = client.getServerURI();
        client.disconnect();
        client.connect();
        client.disconnect();
        // Do something with clientId2
        // Do something with id
    } catch (MqttException exception) {
        // Do something
    } finally {
        if (client != null) {
            client.close();
        }
    }
}