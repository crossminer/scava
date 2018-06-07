{

    TestCaseNotifier notifier;
    IMqttToken connectToken;
    int waitForCompletionTime;
    IMqttToken disconnectToken;
    String serverURI;
    IMqttAsyncClient mqttClient;
    mqttClient = new MqttAndroidClient(mContext, serverURI, "a string");

    connectToken = mqttClient.connect(null, new ActionListener(notifier));
    notifier.waitForCompletion(waitForCompletionTime);

    disconnectToken = mqttClient.disconnect(null, new ActionListener(notifier));
    notifier.waitForCompletion(waitForCompletionTime);

    connectToken = mqttClient.connect(null, new ActionListener(notifier));
    notifier.waitForCompletion(waitForCompletionTime);

    disconnectToken = mqttClient.disconnect(null, new ActionListener(notifier));
    notifier.waitForCompletion(waitForCompletionTime);
    // Do something with connectToken

    // Do something with disconnectToken


}