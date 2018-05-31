{
    final List<FactHandle> handles;
    Object ... targets;
    StatefulSession securityContext;
    synchronized (securityContext) {
        for( int i = number; i < targets.length; i++ ) {
            handles.add( securityContext.insert(targets[i]) );
        }
        for (FactHandle handle : handles) {
            securityContext.retract(handle);
        }
    }
}
