{
    final ExtensibleXmlParser parser;
    final Element element = parser.endElementBuilder();
    Node node = (Node) parser.getCurrent();
    NodeContainer nodeContainer = (NodeContainer) parser.getParent();
    // Do something with element

    // Do something with node

    // Do something with nodeContainer
}
