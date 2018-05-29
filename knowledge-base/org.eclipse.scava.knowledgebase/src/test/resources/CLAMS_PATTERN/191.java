{
    WorkflowProcessInstanceImpl instance;
    String nodeName;
    int num;
    for(Node node : instance.getNodeContainer().getNodes()) {
        if(node.getName().equals(nodeName)) {
            if(node.getOutgoingConnections().size() != num) {
                fail( " a string "  + num +  " a string "  + node.getOutgoingConnections().size());
            } else {
                break;
            }
        }
    }
}                fail( "  " a string "  "  + num +  "  " a string "  "  + node.getOutgoingConnections().size());                fail( "  " a string "  "  + num +  "  " a string "  "  + node.getOutgoingConnections().size());}                fail( "  "  " a string "  "  "  + num +  "  "  " a string "  "  "  + node.getOutgoingConnections().size());
