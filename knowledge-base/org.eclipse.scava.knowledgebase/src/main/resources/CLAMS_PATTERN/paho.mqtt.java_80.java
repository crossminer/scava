{
    SimpleList messages;
    MqttMessage message;
    System.out.println("a string" + new String(message.getPayload()) + "a string");

    synchronized (messages) {
        messages.addLast(message);
        messages.notifyAll();
    }
}