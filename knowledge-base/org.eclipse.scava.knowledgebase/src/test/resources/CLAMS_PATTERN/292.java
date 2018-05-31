{
    PackageBuilder packageBuilder;
    PackageBuilderConfiguration conf = packageBuilder.getPackageBuilderConfiguration();
    if (conf.getSemanticModules().getSemanticModule(BPMNSemanticModule.BPMN2_URI) == null) {
        conf.addSemanticModule(new BPMNSemanticModule());
        conf.addSemanticModule(new BPMNDISemanticModule());
        conf.addSemanticModule(new BPMNExtensionsSemanticModule());
    }
}
