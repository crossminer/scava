{
    final Class<?> cclass;
    final Logger log;
    IMqttClient mqttClient;
    String methodName = Utility.getMethodName();
    LoggingUtilities.banner(log, cclass, methodName);
    connectAndSub();
    try {
        setState(FirstClientState.READY);
        waitForState(FirstClientState.RUNNING);
        repeatedlyPub();
        mqttClient.close();
    } catch (InterruptedException exception) {
        setState(FirstClientState.ERROR);
    } catch (MqttException exception) {
        setState(FirstClientState.ERROR);
    }
}