{
    MqttConnectOptions options;
    String address;
    NetworkModule[] networkModules;
    final String CLASS_NAME;
    final Logger log;
    String[] array;
    final String methodName;
    log.fine(CLASS_NAME, methodName, "a string", new Object[] { address });

    String[] serverURIs = options.getServerURIs();
    for (int i = 0; i < array.length; i++) {
        networkModules[i] = createNetworkModule(array[i], options);
    }

    log.fine(CLASS_NAME, methodName, "a string");
    // Do something with serverURIs
}