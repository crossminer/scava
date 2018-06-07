{
    String event;
    History history;
    String message;
    Boolean retained;
    Date time;
    String topic;
    QoS qos;
    if (history != null) {
        this.event = history.getEvent();
        this.topic = history.getTopic();
        this.message = history.getMessage();
        this.qos = history.getQos();
        this.retained = history.getRetained();
        this.time = history.getTime();
    }
}