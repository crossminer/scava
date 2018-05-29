{
    QueryDefinition qDef;
    final KnowledgeBase kBase;
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    kbuilder.add(ResourceFactory.newByteArrayResource(qDef.getQueryStr().getBytes()), ResourceType.DRL);
    if (kbuilder.hasErrors()) {
        throw new IllegalStateException( " a string "  + kbuilder.getErrors().toString());
    }
    kBase.addKnowledgePackages(kbuilder.getKnowledgePackages());
}        throw new IllegalStateException( "  " a string "  "  + kbuilder.getErrors().toString());        throw new IllegalStateException( "  " a string "  "  + kbuilder.getErrors().toString());}        throw new IllegalStateException( "  "  " a string "  "  "  + kbuilder.getErrors().toString());
