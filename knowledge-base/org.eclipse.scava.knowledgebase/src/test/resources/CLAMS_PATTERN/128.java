{

    Map<String, Object> params;
    Person person;
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

    kbuilder.add(new ClassPathResource( " a string " ), ResourceType.BPMN2);
    if (kbuilder.hasErrors()) {
        for (KnowledgeBuilderError error : kbuilder.getErrors()) {
            System.out.println( " a string "  + error.getMessage());
        }
    }

    KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();

    kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

    StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
    ProcessInstance processInstance = ksession.createProcessInstance( " a string " , params);

    ksession.insert(processInstance);
    ksession.insert(person);

    assertEquals(processInstance.getState(), ProcessInstance.STATE_PENDING);

    ksession.startProcessInstance(processInstance.getId());

    assertEquals(processInstance.getState(), ProcessInstance.STATE_COMPLETED);
}    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.BPMN2);            System.out.println( "  " a string "  "  + error.getMessage());    ProcessInstance processInstance = ksession.createProcessInstance( "  " a string "  " , params);    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.BPMN2);            System.out.println( "  " a string "  "  + error.getMessage());    ProcessInstance processInstance = ksession.createProcessInstance( "  " a string "  " , params);}    kbuilder.add(new ClassPathResource( "  "  " a string "  "  " ), ResourceType.BPMN2);            System.out.println( "  "  " a string "  "  "  + error.getMessage());    ProcessInstance processInstance = ksession.createProcessInstance( "  "  " a string "  "  " , params);
