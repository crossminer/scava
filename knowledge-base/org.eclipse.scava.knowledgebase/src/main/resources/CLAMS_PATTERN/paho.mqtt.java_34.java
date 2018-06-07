{

    FileLock fileLock;
    synchronized (this) {
        if (fileLock != null) {
            fileLock.release();
        }
        if (getFiles().length == 0) {
            // Do something
        }
    }
}