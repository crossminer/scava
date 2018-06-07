{
    final String CLASS_NAME;
    final Logger log;
    DisconnectedMessageBuffer disconnectedMessageBuffer;
    final String methodName;
    if(disconnectedMessageBuffer != null) {
        log.fine(CLASS_NAME, methodName, "a string");
        disconnectedMessageBuffer.setPublishCallback(new ReconnectDisconnectedBufferCallback(methodName));
    }
}