{
    Text textPublishMetric;
    Text textTopic;
    Text textRequestId;
    Properties properties;
    final String inputText;
    final String key;
    final UISynchronize uiSynchronize;
    final IEventBroker broker;
    Text textRequesterClientId;
    final MWindow window;
    final Enumeration<?> enumeration;
    IKuraMQTTClient mqttClient;
    if (!mqttClient.isConnected()) {
        openDialogBox(parent.getShell(), mqttClient, PublishPart.this.broker,
        PublishPart.this.uiSynchronize, PublishPart.this.window);
    }

    final KuraPayload payload = new KuraPayload();
    payload.addMetric("a string", PublishPart.this.textRequestId.getText());
    payload.addMetric("a string", PublishPart.this.textRequesterClientId.getText());

    if ((PublishPart.this.textPublishMetric != null)
    && !"a string".equals(PublishPart.this.textPublishMetric.getText())) {
        try {
            properties = parsePropertiesString(new String(inputText.getBytes("a string")));
        } catch (final UnsupportedEncodingException e1) {
            // Do something
        }
        if (properties != null) {
            while (enumeration.hasMoreElements()) {
                payload.addMetric(key, properties.get(key));
            }
        }
    }
    mqttClient.publish(PublishPart.this.textTopic.getText(), payload);
    PublishPart.this.createSubscriptionHint(parent);
}