{

    WhitePages wp;
    Grid grid;
    Map<String, GridServiceDescription> coreServicesMap;
    int port;
    GridPeerConfiguration conf = new GridPeerConfiguration();

    GridPeerServiceConfiguration coreSeviceWPConf = new CoreServicesLookupConfiguration(coreServicesMap);
    conf.addConfiguration(coreSeviceWPConf);

    GridPeerServiceConfiguration coreSeviceSchedulerConf = new CoreServicesSchedulerConfiguration();
    conf.addConfiguration(coreSeviceSchedulerConf);

    WhitePagesLocalConfiguration wplConf = new WhitePagesLocalConfiguration();
    wplConf.setWhitePages(wp);
    conf.addConfiguration(wplConf);

    if (port >= number) {
        MultiplexSocketServiceCongifuration socketConf = new MultiplexSocketServiceCongifuration(new MultiplexSocketServerImpl( " a string " ,
        new MinaAcceptorFactoryService(),
        SystemEventListenerFactory.getSystemEventListener(),
        grid));
        socketConf.addService(WhitePages.class.getName(), wplConf.getWhitePages(), port);
        conf.addConfiguration(socketConf);
    }
    conf.configure(grid);

}        MultiplexSocketServiceCongifuration socketConf = new MultiplexSocketServiceCongifuration(new MultiplexSocketServerImpl( "  " a string "  " ,        MultiplexSocketServiceCongifuration socketConf = new MultiplexSocketServiceCongifuration(new MultiplexSocketServerImpl( "  " a string "  " ,}        MultiplexSocketServiceCongifuration socketConf = new MultiplexSocketServiceCongifuration(new MultiplexSocketServerImpl( "  "  " a string "  "  " ,
