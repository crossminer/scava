{
    Environment env;
    EntityManagerFactory emf;
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    kbuilder.add(new ClassPathResource( " a string " ), ResourceType.BPMN2);

    if (kbuilder.hasErrors()) {
        for (KnowledgeBuilderError error : kbuilder.getErrors()) {
            System.out.println( " a string "  + error.getMessage());
        }
    }

    KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();

    kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

    env = EnvironmentFactory.newEnvironment();
    env.set(EnvironmentName.ENTITY_MANAGER_FACTORY, emf);
    env.set(EnvironmentName.TRANSACTION_MANAGER, TransactionManagerServices.getTransactionManager());
    final StatefulKnowledgeSession ksession = JPAKnowledgeService.newStatefulKnowledgeSession(kbase, null, env);
    // Do something with ksession
}    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.BPMN2);            System.out.println( "  " a string "  "  + error.getMessage());    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.BPMN2);            System.out.println( "  " a string "  "  + error.getMessage());}    kbuilder.add(new ClassPathResource( "  "  " a string "  "  " ), ResourceType.BPMN2);            System.out.println( "  "  " a string "  "  "  + error.getMessage());
