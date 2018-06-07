{
    MqttConnack cack;
    final String CLASS_NAME;
    final Logger log;
    Object	conLock;
    final String methodName;
    int rc = cack.getReturnCode();
    synchronized (conLock) {
        if (rc == 0) {
            log.fine(CLASS_NAME,methodName,"a string");
        }
    }

    log.fine(CLASS_NAME,methodName,"a string", new Object[]{new Integer(rc)});
}