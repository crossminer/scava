{
    boolean retain;
    QoS qos;
    String topic;
    Callback<Void> cb;
    byte[] payload;
    publish(utf8(topic), new Buffer(payload), qos, retain, cb);
}