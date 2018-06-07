{

    String port;
    Lock connectionLock;
    volatile LogTracker logTracker;
    final MQTT mqtt;
    String host;
    try {
        mqtt.setHost(this.hostToURI(this.host, this.port));
    } catch (final URISyntaxException e) {
        logTracker.log("a string");
    }
    try {
        if (this.connectionLock.tryLock(0, TimeUnit.SECONDS)) {
            this.safelyConnect(mqtt);
        }
    } catch (final InterruptedException e) {
        // Do something
    } catch (final ConnectionException e) {
        // Do something
    } finally {
        // Do something
    }
}