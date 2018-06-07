{
    final Connection connection;
    final Text serverUriText;
    IEventService eventService;
    final Text clientIdText;
    final Button btnConnect;
    Widgets.enable(boolean, btnConnect, serverUriText, clientIdText);

    eventService.sendEvent(Events.of(Selector.ofConnect(), connection));
}