{
    IMqttActionListener callback;
    final long QUIESCE_TIMEOUT;
    Object userContext;
    return this.disconnect(QUIESCE_TIMEOUT, userContext, callback);
}