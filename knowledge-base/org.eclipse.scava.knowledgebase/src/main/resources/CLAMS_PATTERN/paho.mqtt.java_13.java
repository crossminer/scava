{
    SampleAsyncCallback sampleClientSub;
    if (sampleClientSub != null) {
        Disconnector disc = sampleClientSub.new Disconnector();
        disc.doDisconnect();
    }
}