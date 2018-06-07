{
    MqttPersistable message;
    FileOutputStream fos;
    checkIsOpen();
    try {
        fos.write(message.getHeaderBytes(), message.getHeaderOffset(), message.getHeaderLength());
        if (message.getPayloadBytes()!=null) {
            fos.write(message.getPayloadBytes(), message.getPayloadOffset(), message.getPayloadLength());
        }
    } catch (IOException ex) {
        throw new MqttPersistenceException(ex);
    } finally {
        // Do something
    }
}