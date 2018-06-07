{
    ClientState	 			clientState;
    MqttToken token;
    final String CLASS_NAME;
    final Logger log;
    MqttWireMessage message;
    final String methodName;
    log.fine(CLASS_NAME, methodName, "a string", new Object[]{message.getKey(), message, token});

    if (token.getClient() == null ) {
        token.internalTok.setClient(getClient());
    } else {
        log.fine(CLASS_NAME, methodName, "a string", new Object[] {message.getKey(), message, token});
        throw new MqttException(MqttException.REASON_CODE_TOKEN_INUSE);
    }

    try {
        this.clientState.send(message, token);
    } catch(MqttException e) {
        if (message instanceof MqttPublish) {
            this.clientState.undo((MqttPublish)message);
        }
    }
}