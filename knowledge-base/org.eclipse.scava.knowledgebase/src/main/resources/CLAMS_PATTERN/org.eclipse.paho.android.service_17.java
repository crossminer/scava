{
    String clientId;
    Throwable cause;
    boolean reportConnectionLoss;
    if (reportConnectionLoss) {
        report("a string" + clientId + "a string" + cause);
    }

    synchronized (this) {
        notifyAll();
    }

}