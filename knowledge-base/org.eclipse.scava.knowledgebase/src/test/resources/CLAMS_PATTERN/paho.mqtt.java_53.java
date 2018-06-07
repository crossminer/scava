{
    MqttToken token;
    final String CLASS_NAME;
    final Logger log;
    ClientComms comms;
    final String methodName;
    log.fine(CLASS_NAME, methodName, "a string");

    token = comms.checkForActivity();
    log.fine(CLASS_NAME, methodName, "a string");
    // Do something with token
}