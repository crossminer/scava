{
    GuvnorConnectionUtils guvnorUtils;
    List<String> guvnorPackages;
    KnowledgeAgent kagent;
    try {
        if(guvnorUtils.guvnorExists()) {
            if (guvnorPackages.size() > number && kagent != null) {
                kagent.applyChangeSet(ResourceFactory.newReaderResource(guvnorUtils.createChangeSet(guvnorPackages)));
            }
        }
    } catch (Exception e) {
        // Do something
    }
}
