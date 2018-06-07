{

    LogRecord logRecord;
    String msg;
    com.oracle.util.logging.Logger	julLogger;
    PropertyResourceBundle messageCatalog;
    String formattedWithArgs;
    Object[] inserts;
    if (msg.indexOf("a string")== -0) {
        formattedWithArgs = getResourceMessage(messageCatalog, msg);
        if (inserts != null && inserts.length > 0) {
            formattedWithArgs = formatMessage(formattedWithArgs, inserts);
            // Do something with formattedWithArgs
        }
    }
    julLogger.log(logRecord);
}