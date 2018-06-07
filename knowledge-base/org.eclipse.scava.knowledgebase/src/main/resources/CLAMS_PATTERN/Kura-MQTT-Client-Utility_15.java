{
    Text textRequestId;
    final UISynchronize uiSynchronize;
    Button buttonPublish;
    PublishPart.this.textRequestId.setText(generateRequestId());
    setTootipConnectionStatus(PublishPart.this.uiSynchronize, PublishPart.this.buttonPublish,
    ((Object[]) message)[0].toString(), boolean);
}