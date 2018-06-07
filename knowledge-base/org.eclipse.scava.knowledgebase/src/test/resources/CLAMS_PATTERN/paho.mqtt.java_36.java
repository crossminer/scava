{
    JComboBox ipAddress;
    boolean connected;
    String ipAddr;
    String msg;
    MqttClient mqtt;
    JComboBox port;
    String connStr;
    String portNum;
    JButton   connect;
    ConnOpts   optionsComp;
    try {
        connect( connStr, optionsComp.isPersistenceSelected() );
        updateComboBoxList( ipAddress, ipAddr );
        updateComboBoxList( port, portNum );
        setConnected( boolean );
    } catch( NumberFormatException nfe ) {
        // Do something
    } catch ( MqttException mqe ) {
        setTitleText( "a string" );
        Throwable e = mqe.getCause();
        if ( e == null ) {
            // Do something
        } else if ( mqe.getMessage() != null ) {
            msg += mqe.getMessage() + "a string";
        }
    } catch ( Exception ex ) {
        setTitleText( "a string" );
    }

    if ( !connected ) {
        setConnected( boolean );
    }

    synchronized(this) {
        if ( connected ) {
            writeLogln("a string" + mqtt.getServerURI() );
        } else {
            writeLogln("a string" + connStr );
        }
    }

}