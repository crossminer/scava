{
    DataByteArrayOutputStream os;
    UTF8Buffer clientId;
    final byte TYPE;
    UTF8Buffer willTopic;
    UTF8Buffer userName;
    UTF8Buffer password;
    final UTF8Buffer V3_PROTOCOL_NAME;
    final UTF8Buffer V4_PROTOCOL_NAME;
    int version;
    UTF8Buffer willMessage;
    try {
        if(version==0) {
            MessageSupport.writeUTF(os, V3_PROTOCOL_NAME);
        } else if(version >= 0) {
            MessageSupport.writeUTF(os, V4_PROTOCOL_NAME);
        } else {
            // Do something
        }
        MessageSupport.writeUTF(os, clientId);
        if(willTopic!=null && willMessage!=null) {
            MessageSupport.writeUTF(os, willTopic);
            MessageSupport.writeUTF(os, willMessage);
        }
        if(userName!=null) {
            MessageSupport.writeUTF(os, userName);
        }
        if(password!=null) {
            MessageSupport.writeUTF(os, password);
        }
        MQTTFrame frame = new MQTTFrame();
        frame.commandType(TYPE);
        return frame.buffer(os.toBuffer());
    } catch (IOException e) {
        // Do something
    }
}