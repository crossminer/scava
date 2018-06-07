{
    ClientState	 			clientState;
    final String CLASS_NAME;
    final Logger log;
    boolean force;
    Object	conLock;
    final String methodName;
    synchronized (conLock) {
        if (!isClosed()) {
            if (!isDisconnected() || force) {
                log.fine(CLASS_NAME, methodName, "a string");
                if (isConnecting()) {
                    throw new MqttException(MqttException.REASON_CODE_CONNECT_IN_PROGRESS);
                } else if (isConnected()) {
                    throw ExceptionHelper.createMqttException(MqttException.REASON_CODE_CLIENT_CONNECTED);
                } else if (isDisconnecting()) {
                    // Do something
                }
            }
            shutdownExecutorService();
            clientState.close();
        }
    }
}