{
    Transport transport;
    final Callback<Void> cb;
    if( transport!=null ) {
        cb.onFailure(new IllegalStateException("a string"));
    }
    try {
        createTransport(new LoginHandler(cb, boolean));
    } catch (Throwable e) {
        cb.onFailure(e);
    }
}