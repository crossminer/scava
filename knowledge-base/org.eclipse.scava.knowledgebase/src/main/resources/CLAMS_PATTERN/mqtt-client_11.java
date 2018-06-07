{
    boolean disconnected;
    Throwable value;
    final Callback<Void> cb;
    if( !disconnected && tryReconnect() ) {
        reconnect(this);
    } else {
        cb.onFailure(value);
    }
}