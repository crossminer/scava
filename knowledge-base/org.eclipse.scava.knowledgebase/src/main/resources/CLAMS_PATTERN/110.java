{
    String type;
    for (Node node: getNodes()) {
        if (type.equals(node.getName()) && node.getIncomingConnections().isEmpty()) {
            // Do something
        }
    }
}
