{
    IEditorInput input;
    IRegistration registration;
    IEventService eventService;
    Connection connection;
    IEditorSite site;
    final Pair<String, String> tuple;
    if (!(input instanceof ConnectionEditorInput)) {
        throw new PahoException("a string");
    }

    setSite(site);
    setInput(input);
    setPartName(input.getName());
    setTitleImage(Images.get(ImageKeys.IMG_CONNECTION));

    eventService = Paho.getEventService();
    registration = eventService.registerHandler(Selector.ofRenameConnection(connection),
    new IEventHandler<Pair<String, String>>() {
        @Override
        public void handleEvent(Event<Pair<String, String>> e) {
            setPartName(tuple.getRight());
        }
    });
    // Do something with registration

}