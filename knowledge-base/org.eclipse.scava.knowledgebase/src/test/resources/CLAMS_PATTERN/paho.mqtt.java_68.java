{
    DisconnectedMessageBuffer disconnectedMessageBuffer;
    int bufferIndex;
    MqttPublish send = (MqttPublish) this.disconnectedMessageBuffer.getMessage(bufferIndex).getMessage();
    return send.getMessage();
}