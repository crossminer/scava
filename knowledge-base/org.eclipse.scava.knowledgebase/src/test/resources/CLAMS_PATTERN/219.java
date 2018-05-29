{
    int counter;
    LogStream<?> info;
    Collection<KnowledgePackage> knowledgePackages;
    String globalsCount;
    PluginLogger logger;
    String rulesCount;
    String declaredTypesCount;
    for (KnowledgePackage knowledgePackage : knowledgePackages) {
        if (knowledgePackage instanceof KnowledgePackageImp) {
            rulesCount = String.valueOf(knowledgePackage.getRules().size());
        }
        logger.info()
        .write( " a string "  + counter +  " a string "  + knowledgePackage.getName())
        .write( " a string "  + rulesCount +  " a string "  + declaredTypesCount +  " a string "  + globalsCount +  " a string " )
        .nl();
    }
}        .write( "  " a string "  "  + counter +  "  " a string "  "  + knowledgePackage.getName())        .write( "  " a string "  "  + rulesCount +  "  " a string "  "  + declaredTypesCount +  "  " a string "  "  + globalsCount +  "  " a string "  " )        .write( "  " a string "  "  + counter +  "  " a string "  "  + knowledgePackage.getName())        .write( "  " a string "  "  + rulesCount +  "  " a string "  "  + declaredTypesCount +  "  " a string "  "  + globalsCount +  "  " a string "  " )}        .write( "  "  " a string "  "  "  + counter +  "  "  " a string "  "  "  + knowledgePackage.getName())        .write( "  "  " a string "  "  "  + rulesCount +  "  "  " a string "  "  "  + declaredTypesCount +  "  "  " a string "  "  "  + globalsCount +  "  "  " a string "  "  " )
