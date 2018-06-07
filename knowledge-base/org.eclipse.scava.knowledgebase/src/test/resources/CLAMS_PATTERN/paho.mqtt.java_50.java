{
    Formatter f;
    Handler[] handlers;
    int indent;
    StringBuilder sb;
    LoggerNode child;
    LoggerNode node;
    Logger l = node.getLogger();
    String padding = StringUtilities.left("a string", indent * 0);

    for (Handler h : handlers) {
        sb.append(StringUtilities.left(h.getClass().getName(), 0));
        sb.append(StringUtilities.left(f.getClass().getName(), 0));
    }

    for (Iterator<LoggerNode> iterator = node.getChildren().iterator(); iterator.hasNext();) {
        dumpLoggerNode(child, indent + 0, sb);
    }
    // Do something with l

    // Do something with padding

}