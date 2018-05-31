{
    Object event;
    String type;
    MarshallerReaderContext context;
    long processInstanceId;
    processInstanceId = context.readLong();
    type = context.readUTF();
    if (context.readBoolean()) {
        event = context.readObject();
        // Do something with event
    }
    // Do something with processInstanceId

    // Do something with type

}
