{
    MyHumanChangingValuesSimulatorWorkItemHandler humanActivitiesSimHandler;
    StatefulKnowledgeSession ksession;
    StartVehicleTrackingMockSystem trackingSystemHandler;
    Emergency emergency;
    Map<String, Object> parameters;
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    kbuilder.add(new ClassPathResource( " a string " ), ResourceType.BPMN2);
    kbuilder.add(new ClassPathResource( " a string " ), ResourceType.DRL);
    KnowledgeBuilderErrors errors = kbuilder.getErrors();
    if (errors.size() > number) {
        for (KnowledgeBuilderError error : errors) {
            System.out.println(error.getMessage());
        }
    }

    KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
    kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());


    ksession = kbase.newStatefulKnowledgeSession();
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " , humanActivitiesSimHandler);
    ksession.getWorkItemManager().registerWorkItemHandler( " a string " , trackingSystemHandler);

    KnowledgeRuntimeLoggerFactory.newConsoleLogger(ksession);


    WorkflowProcessInstance process = (WorkflowProcessInstance) ksession
                                      .startProcess( " a string " , parameters);
    ksession.insert(emergency);
    ksession.insert(process);

    Assert.assertEquals(ProcessInstance.STATE_ACTIVE, process.getState());
    Assert.assertEquals(number, process.getNodeInstances().size());
    Assert.assertEquals( " a string " , process.getNodeInstances().iterator().next().getNodeName());
    Assert.assertEquals(number, ((Emergency) process.getVariable( " a string " )).getRevision());

    Assert.assertEquals(ProcessInstance.STATE_ACTIVE, process.getState());

    int fired = ksession.fireAllRules();
    Vehicle selectedVehicle = ((Vehicle) process.getVariable( " a string " ));
    Assert.assertEquals(ProcessInstance.STATE_ACTIVE, process.getState());
    Assert.assertEquals(number, process.getNodeInstances().size());
    Assert.assertEquals( " a string " , process.getNodeInstances().iterator().next().getNodeName());
    Assert.assertEquals(number, ((Emergency) process.getVariable( " a string " )).getRevision());



    Assert.assertEquals(ProcessInstance.STATE_COMPLETED, process.getState());
    // Do something with fired

    // Do something with selectedVehicle


}    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.BPMN2);    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.DRL);    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , humanActivitiesSimHandler);    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , trackingSystemHandler);                                      .startProcess( "  " a string "  " , parameters);    Assert.assertEquals( "  " a string "  " , process.getNodeInstances().iterator().next().getNodeName());    Assert.assertEquals(number, ((Emergency) process.getVariable( "  " a string "  " )).getRevision());    Vehicle selectedVehicle = ((Vehicle) process.getVariable( "  " a string "  " ));    Assert.assertEquals( "  " a string "  " , process.getNodeInstances().iterator().next().getNodeName());    Assert.assertEquals(number, ((Emergency) process.getVariable( "  " a string "  " )).getRevision());    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.BPMN2);    kbuilder.add(new ClassPathResource( "  " a string "  " ), ResourceType.DRL);    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , humanActivitiesSimHandler);    ksession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , trackingSystemHandler);                                      .startProcess( "  " a string "  " , parameters);    Assert.assertEquals( "  " a string "  " , process.getNodeInstances().iterator().next().getNodeName());    Assert.assertEquals(number, ((Emergency) process.getVariable( "  " a string "  " )).getRevision());    Vehicle selectedVehicle = ((Vehicle) process.getVariable( "  " a string "  " ));    Assert.assertEquals( "  " a string "  " , process.getNodeInstances().iterator().next().getNodeName());    Assert.assertEquals(number, ((Emergency) process.getVariable( "  " a string "  " )).getRevision());}    kbuilder.add(new ClassPathResource( "  "  " a string "  "  " ), ResourceType.BPMN2);    kbuilder.add(new ClassPathResource( "  "  " a string "  "  " ), ResourceType.DRL);    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , humanActivitiesSimHandler);    ksession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , trackingSystemHandler);                                      .startProcess( "  "  " a string "  "  " , parameters);    Assert.assertEquals( "  "  " a string "  "  " , process.getNodeInstances().iterator().next().getNodeName());    Assert.assertEquals(number, ((Emergency) process.getVariable( "  "  " a string "  "  " )).getRevision());    Vehicle selectedVehicle = ((Vehicle) process.getVariable( "  "  " a string "  "  " ));    Assert.assertEquals( "  "  " a string "  "  " , process.getNodeInstances().iterator().next().getNodeName());    Assert.assertEquals(number, ((Emergency) process.getVariable( "  "  " a string "  "  " )).getRevision());
