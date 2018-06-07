{
    MessageStore messageStore;
    MqttServiceBinder mqttServiceBinder;
    mqttServiceBinder = new MqttServiceBinder(this);

    messageStore = new DatabaseMessageStore(this, this);
    // Do something with mqttServiceBinder

    // Do something with messageStore

}