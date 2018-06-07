{
    boolean 			clean;
    MqttClient 			client;
    String brokerUrl;
    String clientId;
    String userName;
    String password;
    String tmpDir;
    MqttConnectOptions 	conOpt;
    MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(tmpDir);

    try {
        conOpt = new MqttConnectOptions();
        conOpt.setCleanSession(clean);
        if(password != null ) {
            conOpt.setPassword(this.password.toCharArray());
        }
        if(userName != null) {
            conOpt.setUserName(this.userName);
        }
        client = new MqttClient(this.brokerUrl,clientId, dataStore);
        client.setCallback(this);
    } catch (MqttException e) {
        e.printStackTrace();
        log("a string"+e.toString());
    }
}