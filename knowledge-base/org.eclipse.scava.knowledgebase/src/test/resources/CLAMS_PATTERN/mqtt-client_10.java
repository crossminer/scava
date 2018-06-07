{
    Buffer payload;
    short messageId;
    UTF8Buffer topicName;
    QoS qos;
    return "a string" +
    "a string" + dup() +
    "a string" + qos() +
    "a string" + retain() +
    "a string" + messageId +
    "a string" + topicName +
    "a string" + payload +
    "c";
}