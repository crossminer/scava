{

    volatile boolean disconnected;
    String serverURI;
    MqttClientPersistence persistence;
    String clientHandle;
    final String TAG;
    MqttAsyncClient myClient;
    MqttService service;
    volatile boolean isConnecting;
    final Bundle resultBundle;
    String clientId;
    service.traceDebug(TAG, "a string" + serverURI + "a string"+ clientId + "a string");
    try {
        if (persistence == null) {
            File myDir = service.getExternalFilesDir(TAG);
            if (myDir == null) {
                myDir = service.getDir(TAG, Context.MODE_PRIVATE);
                if(myDir == null) {
                    service.callbackToActivity(clientHandle, Status.ERROR,
                    resultBundle);
                }
            }
        }
        IMqttActionListener listener = new MqttConnectionListener(
        resultBundle) {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                doAfterConnectSuccess(resultBundle);
                service.traceDebug(TAG, "a string");
            }
        };
        if (myClient != null) {
            if (isConnecting ) {
                service.traceDebug(TAG,
                                   "a string");
                service.traceDebug(TAG,"a string"+isConnecting+"a string"+disconnected);
            } else if(!disconnected) {
                service.traceDebug(TAG,"a string");
                doAfterConnectSuccess(resultBundle);
            } else {
                service.traceDebug(TAG, "a string");
                service.traceDebug(TAG,"a string");
                setConnectingState(boolean);
            }
        } else {
            myClient = new MqttAsyncClient(serverURI, clientId,
                                           persistence, new AlarmPingSender(service));
            service.traceDebug(TAG,"a string");
            setConnectingState(boolean);
            // Do something with myClient
        }
        // Do something with listener
    } catch (Exception e) {
        handleException(resultBundle, e);
    }
}