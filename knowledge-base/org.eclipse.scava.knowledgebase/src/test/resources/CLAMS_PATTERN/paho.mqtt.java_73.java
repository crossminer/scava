{
    IMqttActionListener callback;
    Object userContext;
    return this.connect(new MqttConnectOptions(), userContext, callback);
}