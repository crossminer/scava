{
    String clientHandle;
    MqttService service;
    final Bundle resultBundle;
    acquireWakeLock();
    service.callbackToActivity(clientHandle, Status.OK, resultBundle);
    deliverBacklog();
    setConnectingState(boolean);
    releaseWakeLock();
}