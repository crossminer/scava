{
    Context context;
    Properties clientProperties;
    ChainedProperties props = new ChainedProperties( " a string " , ClassLoaderUtil.getClassLoader(null, getClass(), boolean ));
    setEmailHost(props.getProperty( " a string " ,  " a string " ));
    setEmailPort(props.getProperty( " a string " ,  " a string " ));

    client = new SyncTaskServiceWrapper(new TaskClient(new JMSTaskClientConnector( " a string " ,
    new JMSTaskClientHandler(SystemEventListenerFactory.getSystemEventListener()),
    clientProperties, context)));
    // Do something with client
}    ChainedProperties props = new ChainedProperties( "  " a string "  " , ClassLoaderUtil.getClassLoader(null, getClass(), boolean ));    setEmailHost(props.getProperty( "  " a string "  " ,  "  " a string "  " ));    setEmailPort(props.getProperty( "  " a string "  " ,  "  " a string "  " ));    client = new SyncTaskServiceWrapper(new TaskClient(new JMSTaskClientConnector( "  " a string "  " ,    ChainedProperties props = new ChainedProperties( "  " a string "  " , ClassLoaderUtil.getClassLoader(null, getClass(), boolean ));    setEmailHost(props.getProperty( "  " a string "  " ,  "  " a string "  " ));    setEmailPort(props.getProperty( "  " a string "  " ,  "  " a string "  " ));    client = new SyncTaskServiceWrapper(new TaskClient(new JMSTaskClientConnector( "  " a string "  " ,}    ChainedProperties props = new ChainedProperties( "  "  " a string "  "  " , ClassLoaderUtil.getClassLoader(null, getClass(), boolean ));    setEmailHost(props.getProperty( "  "  " a string "  "  " ,  "  "  " a string "  "  " ));    setEmailPort(props.getProperty( "  "  " a string "  "  " ,  "  "  " a string "  "  " ));    client = new SyncTaskServiceWrapper(new TaskClient(new JMSTaskClientConnector( "  "  " a string "  "  " ,
