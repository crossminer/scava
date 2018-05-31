{
    ServiceRegistration processBuilderReg;
    BundleContext bc;
    this.processBuilderReg = bc.registerService( new String[]{ ProcessBuilderFactoryService.class.getName(), Service.class.getName()},
    new ProcessBuilderFactoryServiceImpl(),
    new Hashtable() );
}
