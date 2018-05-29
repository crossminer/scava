{
    final String localName;
    final Attributes attrs;
    final ExtensibleXmlParser parser;
    parser.startElementBuilder(localName, attrs);

    ProcessBuildData buildData = (ProcessBuildData) parser.getData();
    // Do something with buildData
}
