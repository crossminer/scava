{
    long then;
    InternalKnowledgeRuntime kruntime;
    Trigger orig;
    Date date = orig.hasNextFireTime();
    long now = kruntime.getSessionClock().getCurrentTime();
    if (then < now) {
        // Do something
    } else {
        return orig.hasNextFireTime();
    }
    // Do something with date

}
