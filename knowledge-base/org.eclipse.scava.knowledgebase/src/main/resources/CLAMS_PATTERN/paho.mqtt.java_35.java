{
    boolean retained;
    boolean connected;
    MqttClient mqtt;
    byte[] message;
    int qos;
    String topic;
    JFrame frame;
    setTitleText( "a string" );

    if ( connected ) {
        try {
            mqtt.getTopic(topic).publish( message, qos, retained );
        } catch ( MqttException ex ) {
            setTitleText( "a string" );
            JOptionPane.showMessageDialog( frame, ex.getClass().getName() + "a string" + ex.getMessage(), "a string", JOptionPane.ERROR_MESSAGE );
        }
    } else {
        setTitleText( "a string" );
    }
}