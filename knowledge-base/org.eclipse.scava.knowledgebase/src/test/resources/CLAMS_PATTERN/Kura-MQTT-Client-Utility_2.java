{
    final UISynchronize uiSynchronize;
    final IBundleResourceLoader bundleResourceService;
    Button subscribeButton;
    Form form;
    safelySetToolbarImage(this.form, this.uiSynchronize, this.bundleResourceService, OFFLINE_STATUS_IMAGE);
    setTootipConnectionStatus(this.uiSynchronize, this.subscribeButton, null, boolean);
}