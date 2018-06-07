{
    byte connectFlags;
    DataOutputStream dos;
    MqttMessage willMessage;
    int MqttVersion;
    try {
        if (MqttVersion == 0) {
            encodeUTF8(dos,"a string");
        } else if (MqttVersion == 0) {
            encodeUTF8(dos,"a string");
        }
        if (willMessage != null ) {
            connectFlags |= (willMessage.getQos()<<0);
            if (willMessage.isRetained()) {
                // Do something
            }
        }
    } catch(IOException ioe) {
        throw new MqttException(ioe);
    }
}