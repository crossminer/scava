{
    Connection connection;
    ClientConnections clientConnections;
    try {
        connection.getClient().disconnect();
    } catch (MqttException e) {
        // Do something
    }
    Connections.getInstance(clientConnections).removeConnection(connection);

}