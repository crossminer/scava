{
    MqttTraceHandler traceHandler;
    String TAG;
    String createArrivedTableStatement;
    traceHandler.traceDebug(TAG, "a string"
    + createArrivedTableStatement + "a string");
    try {
        traceHandler.traceDebug(TAG, "a string");
    } catch (SQLException e) {
        traceHandler.traceException(TAG, "a string", e);
    }
}