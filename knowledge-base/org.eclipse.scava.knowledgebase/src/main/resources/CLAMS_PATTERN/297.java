{
    String cepRuleBase;
    java.io.InputStream is;
    final Logger LOG;
    try {
        KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();

        builder.add(ResourceFactory.newInputStreamResource(is),
        ResourceType.determineResourceType(cepRuleBase));

        if (builder.hasErrors()) {
            LOG.severe(MessageFormat.format(
                java.util.PropertyResourceBundle.getBundle(
                     " a string " ).getString( " a string " ),
                builder.getErrors()));
        } else {
            KnowledgeBaseConfiguration conf = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
            conf.setOption(EventProcessingOption.STREAM);
            conf.setOption(MBeansOption.ENABLED);
            KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase(getRuleName(), conf);
            kbase.addKnowledgePackages(builder.getKnowledgePackages());
        }

    } catch (Throwable e) {
        // Do something
    }
}                     "  " a string "  " ).getString( "  " a string "  " ),                     "  " a string "  " ).getString( "  " a string "  " ),}                     "  "  " a string "  "  " ).getString( "  "  " a string "  "  " ),
