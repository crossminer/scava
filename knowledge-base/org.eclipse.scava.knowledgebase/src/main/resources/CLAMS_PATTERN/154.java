{

    File packageFile;
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    kbuilder.add(ResourceFactory.newClassPathResource( " a string " ), ResourceType.DRL);
    for (KnowledgePackage pkg : kbuilder.getKnowledgePackages()) {
        packageFile = new File(System.getProperty( " a string " ) + File.separator + pkg.getName() +  " a string " );
        break;
    }
    kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    kbuilder.add(ResourceFactory.newFileResource(packageFile), ResourceType.PKG);

    return kbuilder.newKnowledgeBase();
}    kbuilder.add(ResourceFactory.newClassPathResource( "  " a string "  " ), ResourceType.DRL);        packageFile = new File(System.getProperty( "  " a string "  " ) + File.separator + pkg.getName() +  "  " a string "  " );    kbuilder.add(ResourceFactory.newClassPathResource( "  " a string "  " ), ResourceType.DRL);        packageFile = new File(System.getProperty( "  " a string "  " ) + File.separator + pkg.getName() +  "  " a string "  " );}    kbuilder.add(ResourceFactory.newClassPathResource( "  "  " a string "  "  " ), ResourceType.DRL);        packageFile = new File(System.getProperty( "  "  " a string "  "  " ) + File.separator + pkg.getName() +  "  "  " a string "  "  " );
