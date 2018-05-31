{
    final String localName;
    final Attributes attrs;
    final ExtensibleXmlParser parser;
    parser.startElementBuilder(localName, attrs);
    ProcessInfo processInfo = (ProcessInfo) parser.getParent();
    // Do something with processInfo
}
