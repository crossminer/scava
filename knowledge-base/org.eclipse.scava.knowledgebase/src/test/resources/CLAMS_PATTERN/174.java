{
    Map<String, GridServiceDescription> coreServicesMap;
    String vehicleId;
    GridServiceDescriptionImpl gsd = new GridServiceDescriptionImpl(WhitePages.class.getName());
    Address addr = gsd.addAddress( " a string " );
    addr.setObject(new InetSocketAddress[]{new InetSocketAddress( " a string " , number)});
    coreServicesMap.put(WhitePages.class.getCanonicalName(), gsd);

    GridImpl grid = new GridImpl(new ConcurrentHashMap<String, Object>());

    GridPeerConfiguration conf = new GridPeerConfiguration();
    GridPeerServiceConfiguration coreSeviceConf = new CoreServicesLookupConfiguration(coreServicesMap);
    conf.addConfiguration(coreSeviceConf);

    GridPeerServiceConfiguration wprConf = new WhitePagesRemoteConfiguration();
    conf.addConfiguration(wprConf);

    conf.configure(grid);

    GridServiceDescription<GridNode> n1Gsd = grid.get(WhitePages.class).lookup( " a string " );
    GridConnection<GridNode> conn = grid.get(ConnectionFactoryService.class).createConnection(n1Gsd);
    GridNode remoteN1 = conn.connect();


    KnowledgeBuilder kbuilder = remoteN1.get(KnowledgeBuilderFactoryService.class).newKnowledgeBuilder();

    kbuilder.add(new ByteArrayResource(IOUtils.toByteArray(new ClassPathResource( " a string " ).getInputStream())), ResourceType.DSL);
    kbuilder.add(new ByteArrayResource(IOUtils.toByteArray(new ClassPathResource( " a string " ).getInputStream())), ResourceType.DSLR);

    KnowledgeBuilderErrors errors = kbuilder.getErrors();
    if (errors != null && errors.size() > number) {
        for (KnowledgeBuilderError error : errors) {
            System.out.println( " a string "  + error.getMessage());
        }
    }
    KnowledgeBaseConfiguration kbaseConf = remoteN1.get(KnowledgeBaseFactoryService.class).newKnowledgeBaseConfiguration();
    kbaseConf.setOption(EventProcessingOption.STREAM);
    KnowledgeBase kbase = remoteN1.get(KnowledgeBaseFactoryService.class).newKnowledgeBase(kbaseConf);


    kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

    StatefulKnowledgeSession session = kbase.newStatefulKnowledgeSession();

    remoteN1.set( " a string "  + vehicleId, session);
}    Address addr = gsd.addAddress( "  " a string "  " );    addr.setObject(new InetSocketAddress[]{new InetSocketAddress( "  " a string "  " , number)});    GridServiceDescription<GridNode> n1Gsd = grid.get(WhitePages.class).lookup( "  " a string "  " );    kbuilder.add(new ByteArrayResource(IOUtils.toByteArray(new ClassPathResource( "  " a string "  " ).getInputStream())), ResourceType.DSL);    kbuilder.add(new ByteArrayResource(IOUtils.toByteArray(new ClassPathResource( "  " a string "  " ).getInputStream())), ResourceType.DSLR);            System.out.println( "  " a string "  "  + error.getMessage());    remoteN1.set( "  " a string "  "  + vehicleId, session);    Address addr = gsd.addAddress( "  " a string "  " );    addr.setObject(new InetSocketAddress[]{new InetSocketAddress( "  " a string "  " , number)});    GridServiceDescription<GridNode> n1Gsd = grid.get(WhitePages.class).lookup( "  " a string "  " );    kbuilder.add(new ByteArrayResource(IOUtils.toByteArray(new ClassPathResource( "  " a string "  " ).getInputStream())), ResourceType.DSL);    kbuilder.add(new ByteArrayResource(IOUtils.toByteArray(new ClassPathResource( "  " a string "  " ).getInputStream())), ResourceType.DSLR);            System.out.println( "  " a string "  "  + error.getMessage());    remoteN1.set( "  " a string "  "  + vehicleId, session);}    Address addr = gsd.addAddress( "  "  " a string "  "  " );    addr.setObject(new InetSocketAddress[]{new InetSocketAddress( "  "  " a string "  "  " , number)});    GridServiceDescription<GridNode> n1Gsd = grid.get(WhitePages.class).lookup( "  "  " a string "  "  " );    kbuilder.add(new ByteArrayResource(IOUtils.toByteArray(new ClassPathResource( "  "  " a string "  "  " ).getInputStream())), ResourceType.DSL);    kbuilder.add(new ByteArrayResource(IOUtils.toByteArray(new ClassPathResource( "  "  " a string "  "  " ).getInputStream())), ResourceType.DSLR);            System.out.println( "  "  " a string "  "  "  + error.getMessage());    remoteN1.set( "  "  " a string "  "  "  + vehicleId, session);
