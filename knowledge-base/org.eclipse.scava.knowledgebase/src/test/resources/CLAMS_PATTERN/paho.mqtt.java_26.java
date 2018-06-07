{
    Boolean connected;
    MqttClient mqttClient;
    try {
        if(connected) {
            mqttClient.disconnect();
            out(getDate() + Messages.MqttClientView_24);
        } else {
            out(getDate() + Messages.MqttClientView_25);
        }
    } catch (MqttException e) {
        out(getDate() + Messages.MqttClientView_26 + e.getMessage());
    }
}