{
    final History history;
    final Text qosText;
    String event = history.getEvent();
    String topic = history.getTopic();
    String message = history.getMessage();
    QoS qos = history.getQos();
    qosText.setText(qos == null ? "a string" : qos.getLabel());
    Boolean retained = history.getRetained();
    Date time = history.getTime();
    // Do something with event

    // Do something with topic

    // Do something with message

    // Do something with retained

    // Do something with time
}