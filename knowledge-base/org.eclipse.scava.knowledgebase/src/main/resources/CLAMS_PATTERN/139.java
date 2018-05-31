{
    int xOffset;
    Node[] nodes;
    int yOffset;
    for (Node node: nodes) {
        Integer x = (Integer) node.getMetaData().get( " a string " );
        Integer y = (Integer) node.getMetaData().get( " a string " );
        if (node instanceof NodeContainer) {
            postProcessNodeOffset(((NodeContainer) node).getNodes(), xOffset + (x == null ? number : x), yOffset + (y == null ? number : y));
        }
    }
}        Integer x = (Integer) node.getMetaData().get( "  " a string "  " );        Integer y = (Integer) node.getMetaData().get( "  " a string "  " );        Integer x = (Integer) node.getMetaData().get( "  " a string "  " );        Integer y = (Integer) node.getMetaData().get( "  " a string "  " );}        Integer x = (Integer) node.getMetaData().get( "  "  " a string "  "  " );        Integer y = (Integer) node.getMetaData().get( "  "  " a string "  "  " );
