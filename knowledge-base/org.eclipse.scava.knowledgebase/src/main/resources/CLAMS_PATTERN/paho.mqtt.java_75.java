{
    ClientState	 			clientState;
    MqttPingSender pingSender;
    MqttClientPersistence persistence;
    CommsTokenStore 		tokenStore;
    CommsCallback 			callback;
    final Logger log;
    this.pingSender.init(this);
    this.tokenStore = new CommsTokenStore(getClient().getClientId());
    this.callback 	= new CommsCallback(this);
    this.clientState = new ClientState(persistence, tokenStore, this.callback, this, pingSender);

    callback.setClientState(clientState);
    log.setResourceName(getClient().getClientId());
}