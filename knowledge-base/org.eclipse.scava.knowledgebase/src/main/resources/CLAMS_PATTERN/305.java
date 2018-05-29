{
    String type;
    CompositeNode.NodeAndType inNode;
    Connection connection;
    if (connection.getFrom().getNodeContainer() == this) {
        linkOutgoingConnections(connection.getFrom().getId(), connection.getFromType(), org.jbpm.workflow.core.Node.CONNECTION_DEFAULT_TYPE);
    } else {
        if (inNode != null) {
            CompositeNodeStart start = new CompositeNodeStart(connection.getFrom(), type);
            // Do something with start
        }
    }
}
