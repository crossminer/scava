{
    int actualInFlight;
    final String CLASS_NAME;
    final Logger log;
    final String methodName;
    Object queueLock;
    synchronized (queueLock) {
        log.fine(CLASS_NAME,methodName,"a string",new Object[] {new Integer(actualInFlight)});
        if (!checkQuiesceLock()) {
            // Do something
        }
    }
}