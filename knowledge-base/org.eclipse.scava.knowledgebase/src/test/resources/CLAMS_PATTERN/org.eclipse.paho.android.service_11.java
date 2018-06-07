{

    ChangeListener changeListener;
    Map<String, Connection> connections = Connections.getInstance(this).getConnections();

    for (Connection connection : connections.values()) {
        connection.registerChangeListener(changeListener);
        connection.getClient().unregisterResources();
    }
}