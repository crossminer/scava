{
    Connection connection;
    try {
        IMqttClient client = doConnect(connection);
        // Do something with client
    } catch (Exception e) {
        throw new PahoException(e);
    }
}