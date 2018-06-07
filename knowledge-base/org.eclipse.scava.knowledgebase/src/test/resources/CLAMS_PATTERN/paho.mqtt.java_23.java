{
    MqttToken token;
    CommsCallback callback;
    MqttWireMessage ack;
    MqttException ex;
    final String CLASS_NAME;
    final Logger log;
    final String methodName;
    if (ack != null && ack instanceof MqttAck && !(ack instanceof MqttPubRec)) {
        log.fine(CLASS_NAME,methodName, "a string", new Object [] {token.internalTok.getKey(), ack, ex});
        callback.asyncOperationComplete(token);
    }
    if (ack == null ) {
        log.fine(CLASS_NAME,methodName, "a string", new Object [] { token.internalTok.getKey(), ex});
        callback.asyncOperationComplete(token);
    }
}