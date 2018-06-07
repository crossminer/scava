{
    MqttToken token;
    long quiesceTimeout;
    ExecutorService executorService;
    final String CLASS_NAME;
    CommsCallback 			callback;
    final Logger log;
    MqttDisconnect disconnect;
    Object	conLock;
    final String methodName;
    synchronized (conLock) {
        if (isClosed()) {
            log.fine(CLASS_NAME,methodName,"a string");
            throw ExceptionHelper.createMqttException(MqttException.REASON_CODE_CLIENT_CLOSED);
        } else if (isDisconnected()) {
            log.fine(CLASS_NAME,methodName,"a string");
            throw ExceptionHelper.createMqttException(MqttException.REASON_CODE_CLIENT_ALREADY_DISCONNECTED);
        } else if (isDisconnecting()) {
            log.fine(CLASS_NAME,methodName,"a string");
            throw ExceptionHelper.createMqttException(MqttException.REASON_CODE_CLIENT_DISCONNECTING);
        } else if (Thread.currentThread() == callback.getThread()) {
            log.fine(CLASS_NAME,methodName,"a string");
            throw ExceptionHelper.createMqttException(MqttException.REASON_CODE_CLIENT_DISCONNECT_PROHIBITED);
        }
        log.fine(CLASS_NAME,methodName,"a string");
        DisconnectBG discbg = new DisconnectBG(disconnect,quiesceTimeout,token, executorService);
        discbg.start();
    }
}