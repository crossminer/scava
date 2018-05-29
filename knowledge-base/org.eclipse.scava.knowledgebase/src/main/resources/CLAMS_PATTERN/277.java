{
    final ExtensibleXmlParser parser;
    Element element = parser.endElementBuilder();
    NodeInfo nodeInfo = (NodeInfo) parser.getCurrent();
    return parser.getCurrent();
    // Do something with element

    // Do something with nodeInfo

}
