{
    final static String className;
    String uri;
    Logger log;
    final String methodName;
    try {
        log.fine(className,methodName, "a string", new Object[] {uri});
    } catch (IOException ex) {
        log.fine(className,methodName,"a string",null,ex);
        throw new MqttException(MqttException.REASON_CODE_SERVER_CONNECT_ERROR, ex);
    }
}