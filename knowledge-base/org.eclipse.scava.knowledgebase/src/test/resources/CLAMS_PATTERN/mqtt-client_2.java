{
    final FutureConnection next;
    final Buffer payload;
    final QoS qos;
    final boolean retain;
    final UTF8Buffer topic;
    this.next.publish(topic, payload, qos, retain).await();
}