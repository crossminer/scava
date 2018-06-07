{
    final Class<?> cclass;
    ConnectionManipulationProxyServer proxy;
    URI serverURI;
    final Logger log;
    try {
        String methodName = Utility.getMethodName();
        LoggingUtilities.banner(log, cclass, methodName);
        serverURI = TestProperties.getServerURI();
        proxy = new ConnectionManipulationProxyServer(serverURI.getHost(), serverURI.getPort(), 0);
        proxy.startProxy();
        while(!proxy.isPortSet()) {
            // Do something
        }
        log.log(Level.INFO, "a string" + proxy.getLocalPort());
    } catch (Exception exception) {
        // Do something
    }

}