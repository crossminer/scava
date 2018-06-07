{
    final Class<?> cclass;
    final Logger log;
    String methodName = Utility.getMethodName();
    LoggingUtilities.banner(log, cclass, methodName);
    MqttTopic.validate("a string", boolean);
}