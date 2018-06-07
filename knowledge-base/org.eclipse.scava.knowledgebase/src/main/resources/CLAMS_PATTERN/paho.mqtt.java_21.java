{
    String clientId;
    int reconnectDelay;
    Timer reconnectTimer;
    final String CLASS_NAME;
    final Logger log;
    String methodName;
    log.fine(CLASS_NAME, methodName, "a string", new Object[] { this.clientId, new Long(reconnectDelay) });
    reconnectTimer.schedule(new ReconnectTask(), reconnectDelay);
}