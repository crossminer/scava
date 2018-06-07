{
    final Logger log;
    URI serverURI;
    try {
        String methodName = Utility.getMethodName();
        LoggingUtilities.banner(log, Bug443142Test.class, methodName);
        serverURI = TestProperties.getServerURI();
        // Do something with serverURI
    } catch (Exception exception) {
        // Do something
    }
}