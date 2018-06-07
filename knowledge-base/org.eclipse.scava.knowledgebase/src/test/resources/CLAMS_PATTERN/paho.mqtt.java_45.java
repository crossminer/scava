{
    Dictionary<String, Object> props;
    final BundleContext context;
    IEventHandler<T> handler;
    Selector selector;
    props.put(EventConstants.EVENT_TOPIC, selector.select());
    ServiceRegistration<?> reg = context.registerService(EventHandler.class.getName(), new EventHandlerAdaptor<T>(
        handler), props);
    return new Registration(reg);
}