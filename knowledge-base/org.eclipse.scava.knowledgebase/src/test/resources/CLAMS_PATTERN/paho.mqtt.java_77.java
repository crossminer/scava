{
    byte[] data;
    String key;
    MqttPersistable result;
    checkIsOpen();
    try {
        result = new MqttPersistentData(key, data, 0, data.length, null, 0, 0);
        // Do something with result
    } catch(IOException ex) {
        throw new MqttPersistenceException(ex);
    }
}