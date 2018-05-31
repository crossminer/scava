{
    Collection<File> drlFiles;
    KnowledgeBase knowledgeBase;
    if (isInitialized()) {
        KnowledgeBuilder knowledgeBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        for (File drl : drlFiles) {
            knowledgeBuilder.add(ResourceFactory.newFileResource(drl.getAbsolutePath()), ResourceType.DRL);
            if (knowledgeBuilder.hasErrors()) {
                log(knowledgeBuilder.getErrors().toString());
            }
        }
        Collection<KnowledgePackage> knowledgePackages = knowledgeBuilder.getKnowledgePackages();
        for (KnowledgePackage kpackage : knowledgePackages) {
            log( " a string " , kpackage.getName(), kpackage.getRules().size());
        }
        knowledgeBase.addKnowledgePackages(knowledgePackages);
    } else {
        // Do something
    }
}            log( "  " a string "  " , kpackage.getName(), kpackage.getRules().size());            log( "  " a string "  " , kpackage.getName(), kpackage.getRules().size());}            log( "  "  " a string "  "  " , kpackage.getName(), kpackage.getRules().size());
