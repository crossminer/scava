{

    MqttWireMessage pingCommand;
    ClientComms clientComms;
    final String CLASS_NAME;
    final Logger log;
    log.setResourceName(clientComms.getClient().getClientId());
    log.finer(CLASS_NAME, "a string", "a string" );

    pingCommand = new MqttPingReq();
    restoreState();
    // Do something with pingCommand

}