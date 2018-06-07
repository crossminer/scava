{

    final WebSocketNetworkModule webSocketNetworkModule;
    final WebSocketSecureNetworkModule webSocketSecureNetworkModule;
    if(webSocketNetworkModule != null ) {
        return webSocketNetworkModule.getSocketOutputStream();
    }
    if(webSocketSecureNetworkModule != null) {
        return webSocketSecureNetworkModule.getSocketOutputStream();
    }
}