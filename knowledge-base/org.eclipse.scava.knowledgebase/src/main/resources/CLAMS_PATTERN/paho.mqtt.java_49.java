{
    PropertyResourceBundle					logMessageCatalog;
    String									catalogID;
    com.oracle.util.logging.Logger	julLogger;
    if (julLogger == null) {
        setLoggerProperties();
    }

    this.catalogID = logMessageCatalog.getString("a string");
}