{
    MQTTFrame response;
    ExtendedListener listener;
    final MQTT mqtt;
    final Transport transport;
    final Callback<Void> cb;
    try {
        switch (response.messageType()) {
        case CONNACK.TYPE:
            CONNACK connack = new CONNACK().decode(response);
            switch (connack.code()) {
            case CONNECTION_ACCEPTED:
                onSessionEstablished(transport);
                cb.onSuccess(null);
                listener.onConnected();
                break;
            default:
                cb.onFailure(new MQTTException("a string" + connack.code(), connack));
            }
            break;
        default:
            mqtt.tracer.debug("a string", response.messageType());
            cb.onFailure(new IOException("a string" + response.messageType()));
        }
    } catch (ProtocolException e) {
        cb.onFailure(e);
    }
}