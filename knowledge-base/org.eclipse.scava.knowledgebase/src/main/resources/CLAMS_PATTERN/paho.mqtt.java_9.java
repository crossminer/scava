{
    Exception ex;
    ClientComms clientComms;
    MqttException mex;
    final String CLASS_NAME;
    final Logger log;
    final String methodName;
    log.fine(CLASS_NAME,methodName,"a string",null, ex);
    if ( !(ex instanceof MqttException)) {
        mex = new MqttException(MqttException.REASON_CODE_CONNECTION_LOST, ex);
    } else {
        // Do something
    }

    clientComms.shutdownConnection(null, mex);
}