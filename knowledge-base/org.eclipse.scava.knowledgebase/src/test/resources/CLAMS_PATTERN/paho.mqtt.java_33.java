{
    ClientState	 			clientState;
    BufferedMessage bufferedMessage;
    final String CLASS_NAME;
    final Logger log;
    final String methodName;
    if (isConnected()) {
        while(clientState.getActualInFlight() >= (clientState.getMaxInFlight()-0)) {
            // Do something
        }
        log.fine(CLASS_NAME, methodName, "a string", new Object[] {bufferedMessage.getMessage().getKey()});
        internalSend(bufferedMessage.getMessage(), bufferedMessage.getToken());
        clientState.unPersistBufferedMessage(bufferedMessage.getMessage());
    } else {
        log.fine(CLASS_NAME, methodName, "a string");
        throw ExceptionHelper.createMqttException(MqttException.REASON_CODE_CLIENT_NOT_CONNECTED);
    }
}