{
    String clientHandle;
    final String TAG;
    MqttAsyncClient myClient;
    MqttService service;
    final Bundle resultBundle;
    final String NOT_CONNECTED;
    service.traceDebug(TAG, "a string");
    if ((myClient != null) && (myClient.isConnected())) {
        IMqttActionListener listener = new MqttConnectionListener(
            resultBundle);
        try {
            // Do something
        } catch (Exception e) {
            handleException(resultBundle, e);
        }
        // Do something with listener
    } else {
        service.traceError(MqttServiceConstants.DISCONNECT_ACTION,
                           NOT_CONNECTED);
        service.callbackToActivity(clientHandle, Status.ERROR, resultBundle);
    }

    releaseWakeLock();
}