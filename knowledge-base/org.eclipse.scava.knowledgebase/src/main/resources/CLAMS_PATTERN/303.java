{
    String type;
    CompositeNode.NodeAndType outNode;
    Connection connection;
    if (connection.getTo().getNodeContainer() == this) {
        linkIncomingConnections(
            org.jbpm.workflow.core.Node.CONNECTION_DEFAULT_TYPE,
            connection.getTo().getId(),	connection.getToType());
    } else {
        if (outNode != null) {
            CompositeNodeEnd end = new CompositeNodeEnd(connection.getTo(), type);
            // Do something with end
        }
    }
}
