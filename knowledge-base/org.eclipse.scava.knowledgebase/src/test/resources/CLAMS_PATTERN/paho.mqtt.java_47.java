{
    SimpleList messages;
    synchronized (messages) {
        if (messages.size() == 0) {
            try {
                messages.wait(0);
            } catch (InterruptedException e) {
                // Do something
            }
        }
        if (messages.size() == 0) {
            // Do something
        }
        return (MqttMessage) messages.removeFirst();
    }
}