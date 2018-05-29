{
    final String rulesFile;
    KnowledgeBuilder kbuilder;
    final String flowFile;
    KnowledgeBase kbase;
    kbuilder.add(ResourceFactory.newClassPathResource(( " a string "  + rulesFile),
    KnowledgeEngine.class), ResourceType.DRL);
    kbuilder.add(ResourceFactory.newClassPathResource(( " a string "  + flowFile),
    KnowledgeEngine.class), ResourceType.DRF);
    if (kbuilder.hasErrors()) {
        System.out.println(kbuilder.getErrors().toString());
    }
    final Collection<KnowledgePackage> pkgs = kbuilder
    .getKnowledgePackages();
    kbase.addKnowledgePackages(pkgs);
}    kbuilder.add(ResourceFactory.newClassPathResource(( "  " a string "  "  + rulesFile),    kbuilder.add(ResourceFactory.newClassPathResource(( "  " a string "  "  + flowFile),    kbuilder.add(ResourceFactory.newClassPathResource(( "  " a string "  "  + rulesFile),    kbuilder.add(ResourceFactory.newClassPathResource(( "  " a string "  "  + flowFile),}    kbuilder.add(ResourceFactory.newClassPathResource(( "  "  " a string "  "  "  + rulesFile),    kbuilder.add(ResourceFactory.newClassPathResource(( "  "  " a string "  "  "  + flowFile),
